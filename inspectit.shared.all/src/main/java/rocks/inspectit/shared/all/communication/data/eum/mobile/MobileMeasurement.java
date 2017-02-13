package rocks.inspectit.shared.all.communication.data.eum.mobile;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ 
	@Type(name = "MetaMeasurement", value = MobileIOSMeasurement.class),
	@Type(name = "RemoteMeasurement", value = RemoteCallMeasurementContainer.class)
})
/**
 * 
 * @author Tobias Angerstein, Alper Hidiroglu, Manuel Palenga
 *
 */
public class MobileMeasurement implements Serializable,
		Comparable<MobileMeasurement> {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -5092391272010428974L;
	
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
