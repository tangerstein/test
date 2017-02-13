package rocks.inspectit.shared.all.communication.data.eum.mobile;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import rocks.inspectit.shared.all.communication.data.eum.AbstractBeacon;

/**
 * 
 * @author Tobias Angerstein, Alper Hidiroglu, Manuel Palenga
 *
 */
public class MobileBeacon extends AbstractBeacon {
	
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

	public MobileBeacon() {
		measurements = new ArrayList<MobileIOSMeasurement>();
	}
	
	public String getDeviceID() {
		return deviceID;
	}
	
	public List<MobileIOSMeasurement> getMeasurements() {
		return measurements;
	}

}
