package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonProperty;

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
