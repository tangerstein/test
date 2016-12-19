package rocks.inspectit.shared.all.communication.data.eum;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class MobileMeasurement implements Serializable, Comparable<MobileMeasurement> {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 43L;

	@JsonProperty(value = "timestamp")
	private long timestamp;

	public MobileMeasurement(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int compareTo(MobileMeasurement o) {
		if (o.timestamp > this.timestamp) {
			return -1;
		} else if (o.timestamp < this.timestamp) {
			return 1;
		}
		return 0;
	}

}
