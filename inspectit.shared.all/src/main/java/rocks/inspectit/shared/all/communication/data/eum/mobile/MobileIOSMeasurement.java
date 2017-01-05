package rocks.inspectit.shared.all.communication.data.eum.mobile;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author Tobias Angerstein, Alper Hidiroglu, Manuel Palenga
 *
 */
public class MobileIOSMeasurement extends MobileMeasurement {

	private static final long serialVersionUID = -8195087403345499757L;

	@JsonProperty(value = "networkConnection")
	private String networkConnection;
	
	@JsonProperty(value = "longitude")
	private double longitude;

	@JsonProperty(value = "latitude")
	private double latitude;

	@JsonProperty(value = "power")
	private double power;
	
	@JsonProperty(value = "cpu")
	private double cpu;
	
	@JsonProperty(value = "memory")
	private double memory;

	/**
	 * Constructor.
	 * 
	 * @param longitude
	 * @param latitude
	 * @param timestamp
	 * @param power
	 * @param networkConnection
	 * @param cpu
	 * @param memory
	 */
	public MobileIOSMeasurement(double longitude, double latitude,
			long timestamp, double power, String networkConnection, double cpu, double memory) {
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
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * @return the power
	 */
	public double getPower() {
		return power;
	}
	
	/**
	 * @return the network connection
	 */
	public String getNetworkConnection() {
		return networkConnection;
	}
	
	/**
	 * @return the cpu
	 */
	public double getCpu() {
		return cpu;
	}
	
	/**
	 * @return the memory
	 */
	public double getMemory() {
		return memory;
	}
}