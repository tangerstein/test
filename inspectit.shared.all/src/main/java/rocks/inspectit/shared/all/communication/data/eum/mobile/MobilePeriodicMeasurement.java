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
	private double power;
	
	@JsonProperty(value = "cpu")
	private double cpu;
	
	@JsonProperty(value = "memory")
	private double memory;

	/**
	 * Constructor.
	 * 
	 * @param timestamp
	 * @param power
	 * @param cpu
	 * @param memory
	 */
	public MobilePeriodicMeasurement(long timestamp, double power, double cpu, double memory) {
		super(timestamp);
		this.power = power;
		this.cpu = cpu;
		this.memory = memory;
	}
	
	/**
	 * @return the power
	 */
	public double getPower() {
		return power;
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