package rocks.inspectit.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rocks.inspectit.shared.all.communication.data.eum.mobile.MobilePeriodicMeasurement;
import rocks.inspectit.shared.cs.cmr.service.IMobilePeriodicMeasurementAccessService;
import rocks.inspectit.shared.cs.cmr.service.ISpanService;

/**
 * Implementation of the {@link ISpanService} that reads data from the buffer.
 *
 * @author Ivan Senic
 *
 */
@Service
public class MobilePeriodicMeasurementAccessService implements IMobilePeriodicMeasurementAccessService {

	//TODO: Edit MobilePeriodicMeasurement
	
	/**
	 * The invocation DAO.
	 */
	//@Autowired
	//private MobileTraceStorage mobileTraceStorage;
	
	@Override
	public List<MobilePeriodicMeasurement> getMobilePeriodicMeasurementInstances() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MobilePeriodicMeasurement> getMobilePeriodicMeasurementInstances(
			long deviceID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MobilePeriodicMeasurement> getMobilePeriodicMeasurementInstances(
			long deviceID, long fromTimestamp, long toTimestamp) {
		// TODO Auto-generated method stub
		return null;
	}

//
//	@Override
//	public ConcurrentHashMap<Long, MobilePeriodicMeasurement> getAllUseCaseInstances() {
//		return mobileTraceStorage.getMapCopy();
//	}
//
//	@Override
//	public MobilePeriodicMeasurement getSingleUseCaseInstance(long usecaseId) {
//		return mobileTraceStorage.get(usecaseId);
//	}
//
//	@Override
//	public ConcurrentHashMap<Long, MobilePeriodicMeasurement> getAllUsecaseInstances(String usecaseDescription) {
//		ConcurrentHashMap<Long, MobilePeriodicMeasurement> map = mobileTraceStorage.getMapCopy();
//		for (MobilePeriodicMeasurement usecaseInstance : map.values()) {
//			if (!(usecaseInstance.getUseCaseDescription().equals(usecaseDescription))) {
//				map.remove(usecaseInstance.getUseCaseID());
//			}
//		}
//		return map;
//	}

}
