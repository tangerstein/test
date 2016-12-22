package rocks.inspectit.shared.all.communication.data.eum.mobile;

import org.codehaus.jackson.annotate.JsonProperty;

import rocks.inspectit.shared.all.communication.data.eum.AbstractBacon;

public class MobileBacon extends AbstractBacon {
	/**
	 * ID of the mobile device
	 */
	@JsonProperty(value = "deviceID")
	private String deviceID;

	public String getDeviceID() {
		return deviceID;
	}

}
