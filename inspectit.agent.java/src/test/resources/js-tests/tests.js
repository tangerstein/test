
QUnit.config.autostart = false;
QUnit.config.reorder = false;

var supportMap = {};


QUnit.begin(function( details ) {
    // check for Navigation Timing API
    supportMap["navtimings"] = typeof window.performance !== "undefined";
    // check for Resource Timings API
    if ( !('performance' in window) ||
        !('getEntriesByType' in window.performance) ||
        !(window.performance.getEntriesByType('resource') instanceof Array)
    ) {
        supportMap["restimings"] = false;
    } else {
        supportMap["restimings"] = true;
    }
    
    function test(){}
    supportMap["functionNames"] = inspectIT.util.getFunctionName(test) == "test";
    
    
});


function getSentSimilarCount(assert, referenceElement, message) {
	var similarCount = 0;
	for(var i=0; i<mocking.sentElements.length; i++) {
		var isEqual = true;
		for(var prop in referenceElement) {
			if(mocking.sentElements[i][prop] != referenceElement[prop]) {
				isEqual = false;
			}
		}
		if(isEqual) {
			similarCount++;
		}
	}	
	return similarCount;
}

function assertSent(assert, expectedElement, message) {
	for(var i=0; i<mocking.sentElements.length; i++) {
		var elem = mocking.sentElements[i];
		if(elem.id == expectedElement.id) {
			assert.deepEqual(elem,expectedElement, message);
			return;
		}
	}	
	assert.ok(false, message);
}

function assertNotSent(assert, expectedElement, message) {
	for(var i=0; i<mocking.sentElements.length; i++) {
		var elem = mocking.sentElements[i];
		if(elem.id == expectedElement.id) {
			assert.ok(false, message);
			return;
		}
	}	
	assert.ok(true, message);
}

QUnit.module("Core functionality.", {
    beforeEach : function() {
		while(mocking.sentElements.length > 0) {
			mocking.sentElements.pop();
		}
    },
    afterEach : function() {
    }
});


QUnit.test("Element sending policies", function( assert ) {
	var elementA = inspectIT.createEUMElement("testRec");
	elementA.require("data");
	elementA.markComplete("data");
	
	assertNotSent(assert, elementA, "Do not send elements until marked as relevant.");
	elementA.markRelevant();
	assertSent(assert, elementA, "Send element after marked as relevant.");
	
	
	var elementB = inspectIT.createEUMElement("testRec");
	elementB.require("data");
	elementB.markRelevant();
	
	assertNotSent(assert, elementB, "Do not send elements until they have completed.");
	elementB.markComplete("data");
	assertSent(assert, elementB, "Send element after the last data was completed.");
	
});


QUnit.test("Trace building and Relevancy Inheritance", function( assert ) {
	
	var grandParent = inspectIT.createEUMElement("testRec");
	grandParent.require("data");
	
	var parent = inspectIT.createEUMElement("testRec");
	parent.require("data");

	var asyncChild = inspectIT.createEUMElement("testRec");
	asyncChild.require("data");
	
	//test synchronous call
	grandParent.buildTrace(true,function() {
		parent.buildTrace(true,function() {
		});
	});
	//test asynchronous call
	asyncChild.setParent(parent,true);
	
	//complete all
	grandParent.markComplete("data");
	parent.markComplete("data");
	asyncChild.markComplete("data");
	
	//test for relevancy inheritance: only the asyncChild is marked as relevant, it should stil ltrigger the sending of all parents
	asyncChild.markRelevant();
	

	assert.ok("enterTimestamp" in parent && "exitTimestamp" in parent && "enterTimestamp" in grandParent && "exitTimestamp" in grandParent, "Enter / Exit timestamp capturing");
	assert.ok(asyncChild.parentLocalID == parent.id && parent.parentLocalID == grandParent.id, "Parent IDs correctly captured");
	
	assertSent(assert, asyncChild, "Asynchronous child sent.");
	assertSent(assert, parent, "Parent sent.");
	assertSent(assert, grandParent, "GrandParent sent.");
	
	
});


QUnit.test("Listener Instrumentation Mechanism", function( assert ) {
	
	var done = assert.async();
	
	var target = document.createDocumentFragment();
	
	var testEvent = new Event("test");
	
	var listenerACount = 0;
	var listenerBCount = 0;
	
	var instrumentationACount = 0;
	var instrumentationBCount = 0;
	
	function listenerA(event) {
		if(listenerACount == 0) {
			assert.strictEqual(event, testEvent, "Correct Event passed to listenerA");
		}
		listenerACount++;
	}
	
	function listenerB(event) {
		//check the event variable only once
		if(listenerBCount == 0) {
			assert.strictEqual(event, testEvent, "Correct Event passed to listenerB");			
		}
		listenerBCount++;
	}
	
	//these should count as different listeners
	target.addEventListener("test",listenerA,true);
	target.addEventListener("test",listenerA,false);

	//these should count as the same listeners (-> only one instrumentation)
	target.addEventListener("test",listenerB);
	target.addEventListener("test",listenerB);
	
	//add instrumentation
	function instrumentation(executeOriginalListener, attachmentTarget, originalCallback, event) {
		if(event === testEvent) {
			if(originalCallback === listenerA) {
				instrumentationACount++;
			} else if(originalCallback === listenerB) {
				instrumentationBCount++;
			}
		}
		executeOriginalListener();
	}
	inspectIT.instrumentation.instrumentEventListener(instrumentation);
	
	//fire two events
	target.dispatchEvent(testEvent);
	target.dispatchEvent(testEvent);
	
	setTimeout(function() {
		//listenerA should have been called four times, listenerB
		assert.equal(listenerACount,4,"ListenerA was executed the correct number of times");
		assert.equal(listenerBCount,2,"ListenerB was executed the correct number of times");
		
		assert.equal(listenerACount,instrumentationACount,"Instrumentation of listenerA was executed correctly");
		assert.equal(listenerBCount,instrumentationBCount,"Instrumentation of listenerB was executed correctly");
		
		//remove listenerA and check againg
		target.removeEventListener("test",listenerA,true);
		target.removeEventListener("test",listenerA,false);
		target.dispatchEvent(testEvent);
		
		setTimeout(function() {
			assert.equal(listenerACount,4,"ListenerA was not executed anymore after removal");
			assert.equal(listenerBCount,3,"ListenerB was executed the correct number of times");
			
			assert.equal(listenerACount,instrumentationACount,"Instrumentation of listenerA not executed anymore");
			assert.equal(listenerBCount,instrumentationBCount,"Instrumentation of listenerB was executed correctly");
			
			//remove the instrumentation but keep listener B
			inspectIT.instrumentation.uninstrumentEventListener(instrumentation);
			target.dispatchEvent(testEvent);
			setTimeout(function() {
				assert.equal(listenerBCount,4,"ListenerB was executed the correct number of times");
				assert.equal(instrumentationBCount,3,"Instrumentation of listenerB was not executed after removal");
				done();
			}, 500);
			
		}, 500);		
		
	}, 500);
		
	
});


//START TESTS
window.addEventListener("load", function() {
 QUnit.start();
});