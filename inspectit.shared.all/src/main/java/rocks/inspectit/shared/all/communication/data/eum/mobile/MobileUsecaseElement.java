package rocks.inspectit.shared.all.communication.data.eum.mobile;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class MobileUsecaseElement extends MobileIOSElement{

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -117892522529551603L;

	/**
	 * List of measurement points.
	 */
	@JsonProperty(value = "measurements")
	private List<MobileIOSMeasurement> measurements;
	
	/**
	 * ID of the mobile device
	 */
	@JsonProperty(value = "deviceID")
	private String deviceID;

	public MobileUsecaseElement() {
		super();
	}
	
	public MobileUsecaseElement(String useCaseDescription, String useCaseID,
			List<RemoteCallMeasurementContainer> remoteCalls, long timeStamp,
			MobileIOSMeasurement startMeasurement, MobileIOSMeasurement stopMeasurement, 
			List<MobileIOSMeasurement> measurements, String deviceID) {
		super(useCaseDescription, useCaseID, timeStamp, remoteCalls, startMeasurement,
				stopMeasurement);
		this.measurements = measurements;
		this.deviceID = deviceID;
	}
	
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	
	public void setMeasurements(List<MobileIOSMeasurement> measurements) {
		this.measurements = measurements;
	}
	
	public String getDeviceID() {
		return deviceID;
	}
	
	public List<MobileIOSMeasurement> getMeasurements() {
		return measurements;
	}
}
