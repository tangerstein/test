
/**
 * Module for dealing with the Resoure Timings API.
 * Collects the loaded Resources and hands the data over the Action module.
 */
window.inspectIT.registerPlugin("resTimings", function() {

	var inspectIT = window.inspectIT;
	
	var resTimingsSupported = ("performance" in window) && ("getEntriesByType" in window.performance);
	
	/**
	 * Starts the child action for gathering the loaded Resources.
	 */
	function collectResourceTimings() {
		
		if (resTimingsSupported &&  (window.performance.getEntriesByType("resource") instanceof Array)) {

			inspectIT.pageLoadRequest.require("resTimings");
			
			inspectIT.instrumentation.runWithout(function(){
				var onLoadCallback = inspectIT.instrumentation.disableFor(function(){
					setTimeout(inspectIT.instrumentation.disableFor(function(){
						
						var timingsList = [];
						var resourceList = window.performance.getEntriesByType("resource");
						inspectIT.pageLoadRequest.resourceCount = resourceList.length;
						inspectIT.pageLoadRequest.markComplete("resTimings");
						
						for ( i = 0; i < resourceList.length; i++) {
							var resourceRequest = inspectIT.createEUMElement("resourceLoadRequest")
							resourceRequest.require("resTimings");
							resourceRequest.markRelevant();
							
							resourceRequest.setParent(inspectIT.pageLoadAction);
							
							resourceRequest.url = resourceList[i].name;
							resourceRequest.setEnterTimestamp(Math.round(resourceList[i].startTime));
							resourceRequest.setExitTimestamp(Math.round(resourceList[i].responseEnd));
							resourceRequest.initiatorType = resourceList[i].initiatorType;
							resourceRequest.transferSize = resourceList[i].decodedBodySize;
							
							resourceRequest.markComplete("resTimings");
							
						}
						
						
						//clear the timings to make space for new ones
						window.performance.clearResourceTimings();
						
					}), 100);
				});
				window.addEventListener("load", onLoadCallback);
				
			});
		}
	}
	
	return collectResourceTimings;
});