package rocks.inspectit.shared.all.communication.data.eum.mobile;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import rocks.inspectit.shared.all.indexing.IIndexQuery;
import rocks.inspectit.shared.all.indexing.IUsecaseAwareStorageIndexQuery;

public class MobileUsecaseElement extends MobileIOSElement {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -117892522529551603L;

	/**
	 * List of measurement points.
	 */
	@JsonProperty(value = "measurements")
	private List<MobilePeriodicMeasurement> measurements;

	/**
	 * ID of the mobile device
	 */
	@JsonProperty(value = "deviceID")
	private String deviceID;

	public MobileUsecaseElement() {
		super();
	}

	public MobileUsecaseElement(String useCaseDescription, long useCaseID,
			List<RemoteCallMeasurementContainer> remoteCalls, long timeStamp,
			MobilePeriodicMeasurement startMeasurement,
			MobilePeriodicMeasurement stopMeasurement,
			List<MobilePeriodicMeasurement> measurements, String deviceID) {
		super(useCaseDescription, useCaseID, timeStamp, remoteCalls,
				startMeasurement, stopMeasurement);
		this.measurements = measurements;
		this.deviceID = deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public void setMeasurements(List<MobilePeriodicMeasurement> measurements) {
		this.measurements = measurements;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public List<MobilePeriodicMeasurement> getMeasurements() {
		return measurements;
	}

	public boolean isQueryComplied(IIndexQuery query) {
		if (!super.isQueryComplied(query)) {
			return false;
		}
		if (query instanceof IUsecaseAwareStorageIndexQuery) {
			IUsecaseAwareStorageIndexQuery useCaseQuery = (IUsecaseAwareStorageIndexQuery) query;
			if (((useCaseQuery.getUsecaseId() != 0) && (useCaseQuery
					.getUsecaseId() != super.getUsecaseID()))) {
				return false;
			}
			if (((useCaseQuery.getUsecaseDescription() != null) && (!useCaseQuery
					.getUsecaseDescription().equals(super.getUsecaseID())))) {
				return false;
			}
		}
		return true;
	}
}
