package rocks.inspectit.server.service;


public class MobilePeriodicMeasurementAccessService { 
	//implements IMobilePeriodicMeasurementAccessService {
//	/** The logger of this class. */
//	@Log
//	Logger log;
//
//	/**
//	 * The invocation DAO.
//	 */
//	@Autowired
//	private MobileTraceStorage mobileTraceStorage;
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
