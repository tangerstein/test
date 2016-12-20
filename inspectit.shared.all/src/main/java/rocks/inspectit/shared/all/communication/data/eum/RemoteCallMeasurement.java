package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonProperty;

public class RemoteCallMeasurement extends MobileMeasurement {

	@JsonProperty(value = "remoteCallID")
	private String remoteCallID;

	public RemoteCallMeasurement(String remoteCallID, long timestamp) {
		super(timestamp);
		this.remoteCallID = remoteCallID;
	}

}
