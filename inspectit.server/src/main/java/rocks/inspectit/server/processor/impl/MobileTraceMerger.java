package rocks.inspectit.server.processor.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rocks.inspectit.server.dao.DefaultDataDao;
import rocks.inspectit.shared.all.communication.data.InvocationSequenceData;
import rocks.inspectit.shared.all.communication.data.MobileData;
import rocks.inspectit.shared.cs.cmr.service.IInvocationDataAccessService;

@Component
public class MobileTraceMerger {

	/**
	 * The used data access service to access the data on the CMR.
	 */
	@Autowired
	private IInvocationDataAccessService dataAccessService;


	/**
	 * The default data DAO.
	 */
	@Autowired
	private DefaultDataDao defaultDataDao;
	
	/**
	 * 
	 * @param trace
	 */
	public void storeTraceOnTree(InvocationSequenceData trace){
		defaultDataDao.saveAll(Arrays.asList(trace));
	}
	
	/**
	 * 
	 * @param clientTrace
	 * @return
	 */
	public InvocationSequenceData mergeMobileTraceWithServerTraces(InvocationSequenceData clientTrace){

		// Pre-condition, allow only a mobile client trace
		if(clientTrace.getMobileData() == null && clientTrace.getMobileData().hasMobileClientData()){
			return clientTrace;
		}
		
		String useCaseID = clientTrace.getMobileData().getUseCaseID();
		
		List<InvocationSequenceData> listSequences = dataAccessService.getInvocationSequenceOverview(0, -1, null);
		for (InvocationSequenceData invocationSequenceData : listSequences) {
			
			MobileData mobileData = invocationSequenceData.getMobileData();
			
			// Get all mobile server traces from the use case of the client trace
			if(mobileData != null && !mobileData.hasMobileClientData() && mobileData.getUseCaseID() != null && mobileData.getUseCaseID().equals(useCaseID)){
				
				InvocationSequenceData serverTrace = dataAccessService.getInvocationSequenceDetail(invocationSequenceData);
				
				// is currently nested sequence?
				if(serverTrace == null){
					continue;
				}
				
				// Get all measurement points
				for (int i = 0; i < clientTrace.getNestedSequences().size(); i++) {
					InvocationSequenceData nestedSequences = clientTrace.getNestedSequences().get(i);
							
					if(nestedSequences.getMobileData().getTimeStamp().after(serverTrace.getMobileData().getTimeStamp())){
						// Note: remote sequence from getInvocationSequenceDetail, not from getInvocationSequenceOverview
						clientTrace.getNestedSequences().add(i, serverTrace);
						break;
					}				
				}		
			}
		}
		
		return clientTrace;
	}
}
