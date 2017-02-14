package rocks.inspectit.server.service;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import rocks.inspectit.server.util.MobileTraceStorage;
import rocks.inspectit.shared.all.communication.data.eum.mobile.MobileUsecaseElement;
import rocks.inspectit.shared.all.spring.logger.Log;
import rocks.inspectit.shared.cs.cmr.service.IUsecaseAccessService;

public class UseCaseAccessService implements IUsecaseAccessService {
	/** The logger of this class. */
	@Log
	Logger log;

	/**
	 * The invocation DAO.
	 */
	@Autowired
	private MobileTraceStorage mobileTraceStorage;

	@Override
	public ConcurrentHashMap<Long,MobileUsecaseElement> getAllUseCaseInstances() {
		return mobileTraceStorage.getMapCopy();
	}

	@Override
	public MobileUsecaseElement getSingleUseCaseInstance(long usecaseId) {
		return mobileTraceStorage.get(usecaseId);
	}

	@Override
	public ConcurrentHashMap<Long, MobileUsecaseElement> getAllUsecaseInstances(String usecaseDescription) {
		ConcurrentHashMap<Long, MobileUsecaseElement> map = mobileTraceStorage.getMapCopy();
		for (MobileUsecaseElement usecaseInstance : map.values()) {
			if (!(usecaseInstance.getUseCaseDescription().equals(usecaseDescription))) {
				map.remove(usecaseInstance.getUseCaseID());
			}
		}
		return map;
	}

}
