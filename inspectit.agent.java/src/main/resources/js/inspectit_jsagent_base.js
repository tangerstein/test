
//closure used to hide the data from the rest of the application
(function(){

	var SETTINGS = window.inspectIT_settings;
	
	//this cosntant is ued to mark attributes of eum elements which wil lbe filled by a plugin at some point of time
	var REQUIRED_BEFORE_SEND = function(){};
	
	//stores all registered plugins
	var plugins = {};
	
	var isInitialized = false;
	
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
		
		beaconService.init();
		
		// init plugins
		for (pluginName in plugins) {
			if(init in plugins[pluginName]) {
				plugins[pluginName].init();				
			}
		}
	}
	
	
	function EUMElement(typeName) {
		this.type = typeName;
		
		this.trySend = function(){
			for(var key in this) {
				if(this[key] === REQUIRED_BEFORE_SEND) {
					return false;
				}
			}
			beaconService.send(this);
			return true;
		}
	}
	
	function ReferenceableEUMElement(typeName) {
		//super constructor
		EUMElement.call(this,typeName);
		
		//asign a unique id
		this.id = idCounter;
		idCounter++;
	}
	
	var beaconService = (function() {
		
		var SESSION_COOKIE_NAME = "inspectIT_cookieId";
		
		var BEACON_URL = SETTINGS["eumManagementServer"]
		
		var dataToSend = [];
		
		var lastDataCollectionTimestamp = null;
		var firstDataCollectionTimestamp = null;
		
		var sessionID = -1;
		var tabID = -1;
		
		
		var awaitingResponse = false;

		var timeoutTask = null;
		
		var TIME_WINDOW = 2500;
		var MAX_TIME_WINDOW = 15000;
		
		function init() {
			var sessionCookie = inspectIT.util.getCookie(SESSION_COOKIE_NAME);
			if(sessionCookie === null) {
				//send a cookie immediately to request a new session ID - it seems like this page has been cached
				forceBeaconSend();
			} else {
				//session cookie available- read it
				sessionID = parseInt(sessionCookie);			
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
				
				if(sessionID === -1) {
					//we have to request a new session ID, as this page was probably cached
					//we therefore will send an empty beacon instead
					beaconObj.data = [];
				} else {
					beaconObj.data = dataToSend;
					dataToSend = [];
					lastDataCollectionTimestamp = null;
					firstDataCollectionTimestamp = null;
				}
				
				var idsRequested = (sessionID === -1) || (tabID === -1) ;
				
				//use the beacon API if we do not care about the response
				if ( sessionID !== -1 && tabID !== -1) {
					navigator.sendBeacon(BEACON_URL, JSON.stringify(dataObject));
				} else {
					var xhrPost = new XMLHttpRequest();
					xhrPost.open("POST", BEACON_URL, true);
					xhrPost.addEventListener("load", function() {
						inspectIT.instrumentation.runWithout(function() {
							if (xhrPost.status === 200) {
								var responseObj = JSON.parse(xhrPost.responseText);
								
								if(tabID === -1) {
									tabID = responseObj.tabID;
								}
								if(sessionID === -1) {
									var sessionCookie = inspectIT.util.getCookie(SESSION_COOKIE_NAME);
									if(sessionCookie !== null){
										//ignore the received id and instead use the stored one
										sessionID = parseInt(sessionCookie);
									} else {
										//possible race condition between multiple tabs here
										//we just wait a moment and then take the winner of this race condition
										document.cookie = SESSION_COOKIE_NAME+"="+responseObj.sessionID+"; path=/"
										setTimeout(function() {
											inspectIT.instrumentation.runWithout(function() {
												sessionID = parseInt(inspectIT.util.getCookie(SESSION_COOKIE_NAME));
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
			disable();
			var retVal = func();
			reenable();
			return retVal;
		}
		
		return {
			isEnabled : isEnabled,
			disable : disable,
			reenable : reenable,
			runWithout : runWithout
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
			if (func.hasOwnProperty("name")) return func.name; // ES6
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
			REQUIRED_BEFORE_SEND : REQUIRED_BEFORE_SEND,
			util : util
		};
	}
	
})();