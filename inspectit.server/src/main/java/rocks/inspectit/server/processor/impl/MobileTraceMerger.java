package rocks.inspectit.server.processor.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rocks.inspectit.server.dao.DefaultDataDao;
import rocks.inspectit.shared.all.communication.data.InvocationSequenceData;
import rocks.inspectit.shared.all.communication.data.MobileClientData;
import rocks.inspectit.shared.all.communication.data.MobileData;
import rocks.inspectit.shared.cs.cmr.service.IInvocationDataAccessService;

/**
 * Added remote server traces {@link InvocationSequenceData} with {@link MobileData}, not {@link MobileClientData},
 * to client traces {@link InvocationSequenceData} with {@link MobileClientData}.
 *
 * @author Tobias Angerstein, Manuel Palenga
 *
 */
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
	 * Stores the provided {@link InvocationSequenceData} on the server.
	 * 
	 * @param trace {@link InvocationSequenceData} to store
	 */
	public void storeTraceOnTree(InvocationSequenceData trace){
		defaultDataDao.saveAll(Arrays.asList(trace));
	}
	
	/**
	 * Merged the provided clientTrace with serverTraces, stored on the server, when the use case id is equal.
	 * Return the clientTrace with serverTraces as nestedSequences.
	 * 
	 * @param clientTrace {@link InvocationSequenceData} to merge
	 * @return Return the provided {@link InvocationSequenceData} with serverTraces as nestedSequences.
	 */
	public InvocationSequenceData mergeMobileTraceWithServerTraces(InvocationSequenceData clientTrace){

		// Pre-condition, allow only a mobile client trace
		if(clientTrace.getMobileData() == null && clientTrace.getMobileData().hasMobileClientData()){
			return clientTrace;
		}
		
		String useCaseID = clientTrace.getMobileData().getUseCaseID();
		
		// Get all sequences which are stored on the server
		List<InvocationSequenceData> listSequences = dataAccessService.getInvocationSequenceOverview(0, -1, null);
		for (InvocationSequenceData invocationSequenceData : listSequences) {
			
			MobileData mobileData = invocationSequenceData.getMobileData();
			
			// Get all mobile server traces from the use case of the client trace
			if(checkValidServerMobileData(mobileData) && mobileData.getUseCaseID().equals(useCaseID)){
				
				InvocationSequenceData serverTrace = dataAccessService.getInvocationSequenceDetail(invocationSequenceData);
				
				// is already nested sequence?
				if(serverTrace == null){
					continue;
				}
				
				// Get all measurement points
				for (int i = 0; i < clientTrace.getNestedSequences().size(); i++) {
					InvocationSequenceData nestedSequences = clientTrace.getNestedSequences().get(i);
							
					if(nestedSequences.getMobileData().getTimeStamp().after(serverTrace.getMobileData().getTimeStamp())){
						// Note: remove sequence from getInvocationSequenceDetail, not from getInvocationSequenceOverview
						clientTrace.getNestedSequences().add(i, serverTrace);
						break;
					}				
				}		
			}
		}
		
		return clientTrace;
	}
	
	/**
	 * Check whether 1.) the provided mobileData is set, 2.) the mobileData is from the server and 3.) the useCaseID is not null.
	 * 
	 * @param mobileData Check this {@link MobileData}
	 * @return true for a valid mobileData
	 */
	private boolean checkValidServerMobileData(MobileData mobileData){
		return (mobileData != null && !mobileData.hasMobileClientData() && mobileData.getUseCaseID() != null);
	}
}
