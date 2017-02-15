package rocks.inspectit.shared.all.communication.data.eum.mobile;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author Tobias Angerstein, Alper Hidiroglu, Manuel Palenga
 *
 */
public class MobilePeriodicMeasurement extends MobileMeasurement {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -8195087403345499757L;

	@JsonProperty(value = "power")
	private double batteryPower;
	
	@JsonProperty(value = "cpu")
	private double cpuUsage;
	
	@JsonProperty(value = "memory")
	private double memoryUsage;

	/**
	 * Constructor.
	 * 
	 * @param timestamp
	 * @param battery power
	 * @param cpu usage
	 * @param memory usage
	 */
	public MobilePeriodicMeasurement(long timestamp, double batteryPower, double cpuUsage, double memoryUsage) {
		super(timestamp);
		this.batteryPower = batteryPower;
		this.cpuUsage = cpuUsage;
		this.memoryUsage = memoryUsage;
	}
	
	/**
	 * @return the battery power
	 */
	public double getBatteryPower() {
		return batteryPower;
	}
	
	/**
	 * @return the cpu usage
	 */
	public double getCpuUsage() {
		return cpuUsage;
	}
	
	/**
	 * @return the memory usage
	 */
	public double getMemoryUsage() {
		return memoryUsage;
	}
}