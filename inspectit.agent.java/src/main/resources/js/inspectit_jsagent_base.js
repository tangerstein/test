
//closure used to hide the data from the rest of the application
(function(){

	var SETTINGS = window.inspectIT_settings;
	
	//this cosntant is used to mark attributes of eum elements which wil lbe filled by a plugin at some point of time
	
	var MINIMUM_DURATION_FOR_RELEVANCY = 0;
	
	//stores the init-function of all registered plugins
	var plugins = {};
	
	var isInitialized = false;
	
	var pageLoadAction;
	var pageLoadRequest;
	
	var idCounter = 1;
	
	function registerPlugin(name, creatorFunction) {
		//prevent double and too late registration
		if( ! (name in plugins) && !isInitialized) {
			plugins[name] = creatorFunction();
		}
	}
	
	function init() {
		
		if(isInitialized === true) {
			return;
		} else {
			isInitialized = true;
		}
		
		//TODO: maybe optionally disable this call, as it possibly can have a performance impact
		// (not the call itself, but the listener instrumentation)
		instrumentation.initListenerInstrumentation();
		
		beaconService.init();
		
		pageLoadAction = createEUMElement("pageLoadAction");
		pageLoadRequest = createEUMElement("pageLoadRequest");

		pageLoadAction.require("agentBase");
		
		function startInstrumentation(executeOriginalListener, attachmentTarget, originalCallback, event) {	
			if( (event.target === document && (event.type == "load" || event.type == "DOMContentLoaded"))
					|| (event.target === window && event.type == "load" )) {
				
				var listenerRecord = inspectIT.createEUMElement("listenerExecution");
				listenerRecord.require("listenerData");
				listenerRecord.setParent(pageLoadAction);

				var funcName = inspectIT.util.getFunctionName(originalCallback);
				if(funcName != "") {
					listenerRecord.functionName = funcName;
				}
				listenerRecord.eventType = event.type;
				
				//execute the lsitener while build the trace
				listenerRecord.buildTrace(true, executeOriginalListener);
			
				listenerRecord.markComplete("listenerData");
			} else {
				//not a dom element
				executeOriginalListener();
			}
		}
		inspectIT.instrumentation.instrumentEventListener(startInstrumentation);		
		
		pageLoadRequest.require("agentBase");
		pageLoadRequest.url = window.location.href;
		pageLoadRequest.setParent(pageLoadAction);
		pageLoadRequest.markRelevant(); //automatically marks the action as relevant
				

		window.inspectIT.pageLoadAction = pageLoadAction;
		window.inspectIT.pageLoadRequest = pageLoadRequest;
		
		// init plugins
		for (pluginName in plugins) {
			plugins[pluginName]();		
		}
		
		pageLoadRequest.markComplete("agentBase");
		pageLoadAction.markComplete("agentBase");

	}
	
	
	function createEUMElement(typeName) {
		var eumElement = {
			type : typeName,
			id : idCounter,
		}
		idCounter++;
		
		var incompletePlugins = {};
		
		var parentElem = null;
		
		var wasSent = false;
		
		var isRelevant = false;
		
		
		eumElement.setParent = function(newParent, isAsnychronous){
			parentElem = newParent;
			if(newParent !== null) {
				eumElement.parentLocalID = newParent.id;
				if(isRelevant) {
					newParent.markRelevant();
				}
			}
			
			if(!isAsnychronous) {
				delete eumElement.isAsyncCall; //default value is false, no need to include it
			} else {
				eumElement.isAsyncCall = true;
			}
		};
		
		eumElement.getParent = function(){
			return parentElem;
		};
		
		
		eumElement.require = function(key){
			incompletePlugins[key] = true;
		};
		
		eumElement.markComplete = function(key){
			delete incompletePlugins[key];
			trySend();
		};
		
		eumElement.markRelevant = function(){
			if(isRelevant) {
				return; //already relevant, nothing todo
			}
			isRelevant = true;
			if(parentElem !== null) {
				parentElem.markRelevant(); //if an element is relevant, all parents of it are also relevant
			}
			trySend();
		};
		
		eumElement.buildTrace = function(storeTimingsFlag, executionCode){
			
			
			var returnValue = undefined;
			var caughtException = null;
			
			traceBuilder.enterChild(this);
			if(storeTimingsFlag) {
				this.require("traceTimings"); //do not send the element before the timing has been completed
				this.setEnterTimestamp(util.timestampMS());
			}
			if(typeof executionCode !== "undefined") {
				try {
					returnValue = executionCode();
				} catch(error) {
					caughtException = error;
				}
			}
			if(storeTimingsFlag) {
				this.setExitTimestamp(util.timestampMS());
				this.markComplete("traceTimings");
			}
			traceBuilder.finishChild();
			
			if(caughtException !== null) {
				throw caughtException;
			} else {
				return returnValue;
			}
		};	
		
		eumElement.setEnterTimestamp = function(timestamp) {
			this.enterTimestamp = timestamp;	
			if(((typeof eumElement.enterTimestamp === "number") && (typeof eumElement.exitTimestamp === "number") 
					&& (eumElement.exitTimestamp - eumElement.enterTimestamp) >= MINIMUM_DURATION_FOR_RELEVANCY)) {
				this.markRelevant();
			}
		};

		eumElement.setExitTimestamp = function(timestamp) {
			this.exitTimestamp = timestamp;	
			if(((typeof eumElement.enterTimestamp === "number") && (typeof eumElement.exitTimestamp === "number") 
					&& (eumElement.exitTimestamp - eumElement.enterTimestamp) >= MINIMUM_DURATION_FOR_RELEVANCY)) {
				this.markRelevant();
			}
		};
		
		function trySend() {
			//check for data completeness
			if(Object.keys(incompletePlugins).length > 0) {
				return false;
			}
			//check for relevancy
			if(!isRelevant) {
				return false;
			}
			//prevent double sending
			if(wasSent) { 
				return false;
			}
			wasSent = true;
			beaconService.send(eumElement);
			return true;
		}
		
		return eumElement;
	}
	
	var beaconService = (function() {
		
		var SESSION_COOKIE_NAME = "inspectIT_cookieId";
		
		var BEACON_URL = SETTINGS["eumManagementServer"]
		
		var BEACON_API_SUPPORTED = (typeof navigator.sendBeacon !== "undefined") ;
		
		var dataToSend = [];
		
		var lastDataCollectionTimestamp = null;
		var firstDataCollectionTimestamp = null;
		
		var sessionID = "-1";
		var tabID = "-1";
		
		
		var awaitingResponse = false;

		var timeoutTask = null;
		
		var TIME_WINDOW = 2500;
		var MAX_TIME_WINDOW = 15000;
		
		function init() {
			var sessionCookie = inspectIT.util.getCookie(SESSION_COOKIE_NAME);
			if(sessionCookie === null) {
				//send a beacon immediately to request a new session ID - it seems like this page has been cached
				forceBeaconSend();
			} else {
				//session cookie available- read it
				sessionID = sessionCookie;			
			}
		}
		
		function send(element) {
			dataToSend.push(element);
			var time = inspectIT.util.timestampMS();
			lastDataCollectionTimestamp = time;
			if(firstDataCollectionTimestamp === null) {
				firstDataCollectionTimestamp = time;
			}
			checkBeaconSendTimeout();
		}
		
		function checkBeaconSendTimeout() {
			if ( dataToSend.length > 0) {

				//disable instrumenation as we make use of setTimoeut / clearTimeout
				inspectIT.instrumentation.runWithout(function() {
					//cancel timeout
					if(timeoutTask !== null) {
						clearTimeout(timeoutTask);
						timeoutTask = null;
					}
					//check if maximum wait time has been exceeded
					var timestamp = inspectIT.util.timestampMS();
					var totalElapsedMS = (timestamp - firstDataCollectionTimestamp);
					var elapsedSinceLastSendRequestMS =  (timestamp - lastDataCollectionTimestamp);
					if( totalElapsedMS >= MAX_TIME_WINDOW || elapsedSinceLastSendRequestMS >= TIME_WINDOW) {
						//force immediate sending of beacon
						forceBeaconSend();
					} else {
						
						timeoutTask = setTimeout(function() {
							timeoutTask = null;
							checkBeaconSendTimeout();
						}, Math.min(TIME_WINDOW - elapsedSinceLastSendRequestMS, MAX_TIME_WINDOW - totalElapsedMS));
					}
				});
			}
			
		}
		
		function forceBeaconSend() {
			//disable instrumentation as we interact with APIs
			inspectIT.instrumentation.runWithout(function() {
				
				if(awaitingResponse) {
					return; //only send one beacon at a time - as soon as the last beacon is finished it will attempt to call checkBeaconSendTimeout()
				}
				
				var beaconObj = {
					tabID : tabID,
					sessionID : sessionID,
				}
				
				if(sessionID == "-1") {
					//we have to request a new session ID, as this page was probably cached
					//we therefore will send an empty beacon instead
					beaconObj.data = [];
				} else {
					beaconObj.data = dataToSend;
					dataToSend = [];
					lastDataCollectionTimestamp = null;
					firstDataCollectionTimestamp = null;
				}
				
				//use the beacon API if we do not care about the response
				if ( BEACON_API_SUPPORTED && sessionID != "-1" && tabID != "-1") {
					navigator.sendBeacon(BEACON_URL, JSON.stringify(beaconObj));
				} else {
					var xhrPost = new XMLHttpRequest();
					xhrPost.open("POST", BEACON_URL, true);
					xhrPost.addEventListener("loadend", function() {
						inspectIT.instrumentation.runWithout(function() {
							if (xhrPost.status === 200) {
								var responseObj = JSON.parse(xhrPost.responseText);
								
								if(tabID == "-1") {
									tabID = responseObj.tabID;
								}
								if(sessionID == "-1") {
									var sessionCookie = inspectIT.util.getCookie(SESSION_COOKIE_NAME);
									if(sessionCookie !== null){
										//ignore the received id and instead use the stored one
										sessionID = sessionCookie;
									} else {
										//possible race condition between multiple tabs here
										//we just wait a moment and then take the winner of this race condition
										document.cookie = SESSION_COOKIE_NAME+"="+responseObj.sessionID+"; path=/"
										setTimeout(function() {
											inspectIT.instrumentation.runWithout(function() {
												sessionID = inspectIT.util.getCookie(SESSION_COOKIE_NAME);
												awaitingResponse = false;
												checkBeaconSendTimeout();
											});
										},200);
											
										return; //do not mark the request as complete, this is done after the timeout
									}
								}
							} else {
								//error handling: add the failed data back to the send queue
								for (var i = 0; i < beaconObj.data.length; i++) {
									send(beaconObj.data[i]);
								}
							}
							awaitingResponse = false;
							checkBeaconSendTimeout();
						});
					});
					xhrPost.setRequestHeader("Content-Type", "application/json");
					xhrPost.send(JSON.stringify(beaconObj));
					awaitingResponse = true;
				}
			});
		}
		
		return {
			init : init,
			send : send
		}
	})();
	
	var instrumentation = (function(){
		
		var instrumentationCounter = 0;
		
		function isEnabled() {
			return (instrumentationCounter == 0);
		}
		
		function disable() {
			instrumentationCounter++;
		}
		
		function reenable() {
			instrumentationCounter--;
		}
		
		function runWithout(func) {
			return (disableFor(func))();
		}
		
		function disableFor(func) {
						
			return function() {
				disable();
				var retVal = func.apply(this, arguments);
				reenable();
				return retVal;
			};
		}
		
		var listenerInstrumentations = [];
		
		
		function instrumentEventListener(instrumentationFunc) {
			listenerInstrumentations.push(instrumentationFunc);
		}
		
		function uninstrumentEventListener(instrumentationFunc) {
			var index = listenerInstrumentations.indexOf(instrumentationFunc);
			if (index != -1) {
				listenerInstrumentations.splice(index,1);
			}
		}
		
		function initListenerInstrumentation() {
			
			
			if( (typeof EventTarget !== "undefined") && EventTarget.prototype.hasOwnProperty("addEventListener")
					&& EventTarget.prototype.hasOwnProperty("removeEventListener")) {
				
				//Chrome & Firefox & Edge
				
				instrumentForPrototype(EventTarget.prototype);
			} else {
				//IE solution
				if ("addEventListener" in Element.prototype) {
					instrumentForPrototype(Element.prototype);
				}
				
			}
			
			
			
			function instrumentForPrototype(prototypeToInstrument) {
				
				var uninstrumentedAddEventListener = prototypeToInstrument.addEventListener;
				prototypeToInstrument.addEventListener = function(type, callback, optionsOrCapture) {
					
					//check instrumentation disabled flag
					if(!isEnabled()) {
						return uninstrumentedAddEventListener.apply(this, arguments);
					}
					
					var target = this;
					//done according to https://dom.spec.whatwg.org/#concept-flatten-options
					var useCapture = optionsOrCapture === true 
									|| ((typeof optionsOrCapture == "object") && optionsOrCapture.capture);
					
					
					//inititalize the isntrumentation storage for this callback object
					if( !("__inspectIT_instr_callback_mapping" in callback)) {
						//closure to hide the data
						var mapping = function() {
							
							var storedMappings= [];
							
							function put(target,type, capture, instrumentedCallback) {
								storedMappings.push({
									target : target,
									type : type,
									capture : capture,
									instrumentedCallback : instrumentedCallback
								});
							}
							
							function getAndRemove(target, type, capture) {
								for(var i=0; i<storedMappings.length; i++) {
									var mapping = storedMappings[i];
									if(mapping.target === target && mapping.type == type && mapping.capture == capture) {
										storedMappings.splice(i,1);
										return mapping.instrumentedCallback;
									}
								}
								return null;
							}
							

							function contains(target,type, capture) {
								for(var i=0; i<storedMappings.length; i++) {
									var mapping = storedMappings[i];
									if(mapping.target === target && mapping.type == type && mapping.capture == capture) {
										return true;
									}
								}
								return false;
							}
							
							return {
								put : put,
								getAndRemove : getAndRemove,
								contains : contains
							}					
						}();
						
						callback.__inspectIT_instr_callback_mapping = function() { return mapping; }
					}
					var mapping = callback.__inspectIT_instr_callback_mapping();
					
					var instrumentedCallback = function() {
						var originalArgs = arguments;
						var originalThis = this;
						var allArgs;
						
						var currentListenerIndex = -1;
						
						var returnValue;
						
						//recursive iterator
						//when this function is invoked, it wither calls the next isntrumentation or the actual callback if all instrumentations where executed
						function continueFunc() {
							currentListenerIndex++;
							if(currentListenerIndex < listenerInstrumentations.length) {
								listenerInstrumentations[currentListenerIndex].apply(window, extendedArgs);
							} else {
								returnValue = callback.apply(originalThis, originalArgs);
							}
						}
						
						extendedArgs = [continueFunc,target,callback].concat(Array.prototype.slice.call(originalArgs));
						
						//start the call chain
						continueFunc();
						
						return returnValue;
					}
					
					//According to the spec, attaching the same lsitener on the same object with the same type and capture args ahs no effect
					//therefore, we need to prevent storing
					if(mapping.contains(target,type,useCapture)) {
						return ;			
					} else {
						//otherwise store the new mapping
						mapping.put(target,type,useCapture,instrumentedCallback)
					}
					
						
					
					
					var modifiedArgs = Array.prototype.slice.call(arguments);
					modifiedArgs[1] = instrumentedCallback;
					uninstrumentedAddEventListener.apply(target, modifiedArgs);
					
				}
				


				var uninstrumentedRemoveEventListener = prototypeToInstrument.removeEventListener;
				
				prototypeToInstrument.removeEventListener = function(type, callback, optionsOrCapture) {

					//check instrumentation disabled flag
					if(!isEnabled()) {
						return uninstrumentedRemoveEventListener.apply(this, arguments);
					}
					
					var target = this;
					
					//done according to https://dom.spec.whatwg.org/#concept-flatten-options
					var useCapture = optionsOrCapture === true 
									|| ((typeof optionsOrCapture == "object") && optionsOrCapture.capture);
					
					if("__inspectIT_instr_callback_mapping" in callback) {
						var mapping = callback.__inspectIT_instr_callback_mapping();
						var instrCallback = mapping.getAndRemove(target,type,useCapture);
						if(instrCallback) {
							//isntrumentation detected. remove the isntrumented lsitener instead
							var modifiedArgs = Array.prototype.slice.call(arguments);
							modifiedArgs[1] = instrCallback;
							return uninstrumentedRemoveEventListener.apply(this, modifiedArgs);
						}				
					}
					//default case
					return uninstrumentedRemoveEventListener.apply(this, arguments);
					
				}
			}
			
			
		}
		
		
		
		
		return {
			initListenerInstrumentation : initListenerInstrumentation,
			isEnabled : isEnabled,
			disable : disable,
			reenable : reenable,
			disableFor : disableFor,
			runWithout : runWithout,
			instrumentEventListener : instrumentEventListener,
			uninstrumentEventListener : uninstrumentEventListener
		}
		
	})();
	
	
	var traceBuilder = (function() {
		
		var callStack = [];
		
		function getCurrentParent() {
			if(callStack.length == 0) {
				return null;
			} else {
				return callStack[callStack.length - 1];
			}
		}
		
		function enterChild(eumElement) {
			var parent = getCurrentParent();
			if(parent !== null && eumElement.getParent() === null) {
				eumElement.setParent(parent);		
			}
			callStack.push(eumElement);
		}

		function finishChild() {
			callStack.pop();			
		}
		
		return {
			getCurrentParent : getCurrentParent,
			enterChild : enterChild,
			finishChild : finishChild
		}
		
	})();
	/**
	 * Utility module providing some often needed functionality.
	 * @return utility module with needed functionality.
	 */
	var util = (function () {
	
		/**
		 * Gets the current timestamp with the Performance API, if supported.
		 * Otherwise returns an approximate tiemstamp using Date.now();
		 * @return current timestamp
		 */
		function timestampMS() {
			if (window.performance) {
				if (performance.timing.navigationStart != 0) {
					return Math.round(performance.now() + performance.timing.navigationStart);
				}
			}
			return Date.now();
		}
	
		/**
		 * Gets a cookie with a specified key.
		 * @param key The key of the cookie
		 * @return The value of the cookie which is specified by the key - null if the cookie doesn't exist
		 */
		function getCookie(key) {
			var name = key + "=";
		    var ca = document.cookie.split(';');
		    for(var i = 0; i <ca.length; i++) {
		        var c = ca[i];
		        while (c.charAt(0)==' ') {
		            c = c.substring(1);
		        }
		        if (c.indexOf(name) == 0) {
		            return c.substring(name.length,c.length);
		        }
		    }
		    return null;
		}
	
		/**
		 * Gets the Javascript function name. (Only works >=ES6)
		 */
		function getFunctionName(func) {
			if (!(typeof func === "function")) return null;
			if (func.hasOwnProperty("name")) { //ES 6
				if(func.name == "") {
					return "<anonymous>";
				} else {
					return func.name;					
				}
			}
			return "";
		}

		
		return {
			timestampMS : timestampMS,
			getFunctionName : getFunctionName,
			getCookie : getCookie
		}
		
	})();
	
	
	
	//prevent double injection
	if(typeof window.inspectIT === "undefined") {
		window.inspectIT = {
			init : init,
			registerPlugin : registerPlugin,
			beaconService : beaconService,
			instrumentation : instrumentation,
			traceBuilder : traceBuilder,
			createEUMElement : createEUMElement,
			util : util
		};
	}
	
})();