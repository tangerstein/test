package rocks.inspectit.shared.all.communication.data.eum;

public class RemoteCallMeasurement extends MobileMeasurement {
	private String remoteCallID;

	public RemoteCallMeasurement(String remoteCallID, long timestamp) {
		super(timestamp);
		this.remoteCallID = remoteCallID;
	}

}
