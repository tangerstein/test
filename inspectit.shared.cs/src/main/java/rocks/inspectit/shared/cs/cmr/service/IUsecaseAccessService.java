package rocks.inspectit.shared.cs.cmr.service;

import java.util.concurrent.ConcurrentHashMap;

import rocks.inspectit.shared.all.cmr.service.ServiceExporterType;
import rocks.inspectit.shared.all.cmr.service.ServiceInterface;
import rocks.inspectit.shared.all.communication.data.eum.mobile.MobileUsecaseElement;

@ServiceInterface(exporter = ServiceExporterType.HTTP)
public interface IUsecaseAccessService {
	/**
	 * Returns all available usecases
	 * 
	 * @return
	 */
	public ConcurrentHashMap<Long, MobileUsecaseElement> getAllUseCaseInstances();

	/**
	 * Returns a specific use case
	 * 
	 * @return
	 */
	public MobileUsecaseElement getSingleUseCaseInstance(long usecaseId);

	/**
	 * Get all usecase instances which belong to the same usecase 
	 * @param usecaseDescription
	 */
	public ConcurrentHashMap<Long, MobileUsecaseElement> getAllUsecaseInstances(String usecaseDescription);

}
