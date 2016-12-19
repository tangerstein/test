package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonProperty;

public class MobileIOSMeasurement extends MobileMeasurement {

	@JsonProperty(value = "longitude")
	private float longitude;

	@JsonProperty(value = "latitude")
	private float latitude;


	// ...

	/**
	 * 
	 */
	private static final long serialVersionUID = -8423161562303980627L;

	public MobileIOSMeasurement(float longitude, float latitude, long timestamp) {
		super(timestamp);
		this.longitude = longitude;
		this.latitude = latitude;
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
}