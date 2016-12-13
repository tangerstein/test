package rocks.inspectit.shared.all.communication.data.eum;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

public class MobileIOSMeasurement implements Serializable, Comparable<MobileIOSMeasurement> {

	@JsonProperty(value = "longitude")
	private float longitude;

	@JsonProperty(value = "latitude")
	private float latitude;

	@JsonProperty(value = "timestamp")
	private long timestamp;

	// ...

	/**
	 * 
	 */
	private static final long serialVersionUID = -8423161562303980627L;

	public MobileIOSMeasurement(float longitude, float latitude, long timestamp) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.timestamp = timestamp;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the longitude
	 */
	public float getLongitude() {
		return longitude;
	}

	/**
	 * @return the latitude
	 */
	public float getLatitude() {
		return latitude;
	}

	@Override
	public int compareTo(MobileIOSMeasurement o) {
		if(o.timestamp > this.timestamp){
			return -1;
		} else if(o.timestamp < this.timestamp){
			return 1;
		}
		return 0;
	}
}