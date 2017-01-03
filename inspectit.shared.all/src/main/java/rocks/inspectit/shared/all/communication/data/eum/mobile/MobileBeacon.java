package rocks.inspectit.shared.all.communication.data.eum.mobile;

import org.codehaus.jackson.annotate.JsonProperty;

import rocks.inspectit.shared.all.communication.data.eum.AbstractBeacon;

/**
 * 
 * @author Tobias Angerstein, Alper Hidiroglu, Manuel Palenga
 *
 */
public class MobileBeacon extends AbstractBeacon {
	
	/**
	 * ID of the mobile device
	 */
	@JsonProperty(value = "deviceID")
	private String deviceID;

	public String getDeviceID() {
		return deviceID;
	}

}
