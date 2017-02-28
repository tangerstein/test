package rocks.inspectit.shared.cs.cmr.service;

import java.util.concurrent.ConcurrentHashMap;

import rocks.inspectit.shared.all.cmr.service.ServiceExporterType;
import rocks.inspectit.shared.all.cmr.service.ServiceInterface;
import rocks.inspectit.shared.all.communication.data.eum.mobile.MobilePeriodicMeasurement;

// TODO: EDIT ME
// TODO: EDIT ME
// TODO: EDIT ME

@ServiceInterface(exporter = ServiceExporterType.HTTP)
public interface IMobilePeriodicMeasurementAccessService {
	/**
	 * Returns all available usecases
	 * 
	 * @return
	 */
	public ConcurrentHashMap<Long, MobilePeriodicMeasurement> getAllUseCaseInstances();

	/**
	 * Returns a specific use case
	 * 
	 * @return
	 */
	public MobilePeriodicMeasurement getSingleUseCaseInstance(long usecaseId);

	/**
	 * Get all usecase instances which belong to the same usecase 
	 * @param usecaseDescription
	 */
	public ConcurrentHashMap<Long, MobilePeriodicMeasurement> getAllUsecaseInstances(String usecaseDescription);

}
