package rocks.inspectit.shared.all.communication.data.eum.mobile;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author Tobias Angerstein, Alper Hidiroglu, Manuel Palenga
 *
 */
public class MobileIOSMeasurement extends MobileMeasurement {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8195087403345499757L;

	@JsonProperty(value = "longitude")
	private float longitude;

	@JsonProperty(value = "latitude")
	private float latitude;

	@JsonProperty(value = "power")
	private int power;

	@JsonProperty(value = "networkConnection")
	private String networkConnection;
	
	@JsonProperty(value = "cpu")
	private float cpu;
	
	@JsonProperty(value = "memory")
	private float memory;

	public MobileIOSMeasurement(float longitude, float latitude,
			long timestamp, int power, String networkConnection, float cpu, float memory) {
		super(timestamp);
		this.longitude = longitude;
		this.latitude = latitude;
		this.power = power;
		this.networkConnection = networkConnection;
		this.cpu = cpu;
		this.memory = memory;
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
	
	/**
	 * @return the power
	 */
	public int getPower() {
		return power;
	}
	
	/**
	 * @return the network connection
	 */
	public String getNetworkConnection() {
		return networkConnection;
	}
	
	/**
	 * @return the latitude
	 */
	public float getCPU() {
		return cpu;
	}
	
	/**
	 * @return the latitude
	 */
	public float getMemory() {
		return memory;
	}
}