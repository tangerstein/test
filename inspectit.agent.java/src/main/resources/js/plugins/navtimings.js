
/**
 * Module for dealing with the Navigation Timings API (https://developer.mozilla.org/en-US/docs/Web/API/Navigation_timing_API).
 * Collects the Navigation Timings, adds the Speedindex (if available) and hands it over to the Action module.
 */
window.inspectIT.registerPlugin("navTimings", function() {
	
	
	var inspectIT = window.inspectIT;
	
	var navTimingsSupported = ("performance" in window) && ("timing" in window.performance);
	
	/**
	 * Collects the navigation timings and stores them in the pageloadrequest.
	 */
	function collectNavigationTimings() {
		if (navTimingsSupported) {
			inspectIT.instrumentation.runWithout(function(){

				//force the beacon service to wait until we have collected the data
				inspectIT.pageLoadRequest.require("navigationTimings");
				
				
				var onLoadCallback = inspectIT.instrumentation.disableFor(function(){

					setTimeout(inspectIT.instrumentation.disableFor(function(){
						inspectIT.pageLoadRequest.navigationTimings = inspectIT.pageLoadRequest.navigationTimings || {};
						buildNavigationTimingsInfo(inspectIT.pageLoadRequest.navigationTimings);
						
						inspectIT.pageLoadRequest.markComplete("navigationTimings");
					}), 100);
				});
				
				window.addEventListener("load", onLoadCallback);
				
			});
		}
	}
	
	/**
	 * Collects the Navigation Timings, adds the Speedindex (if available)
	 * and ends the Action child after handing the data over to the Action Module.
	 */
	function buildNavigationTimingsInfo(navigationTimingsInfo) {
				
		for (var key in window.performance.timing) {
			// this is really sad but otherwise toJSON doesn't work in all browsers
			navigationTimingsInfo[String(key) + "W"] = window.performance.timing[key];
		}		
		
		return navigationTimingsInfo;
	}

	//init call returned
	return collectNavigationTimings;
});