package rocks.inspectit.shared.all.communication.data;

import rocks.inspectit.shared.all.cmr.cache.IObjectSizes;

/**
 * Data class for mobile data capturing on the client side.
 *
 * @author Manuel Palenga
 */
public class MobileClientData extends MobileData {

	/** Serial version id. */
	private static final long serialVersionUID = 3195785893704992213L;

	/** The description of the use case. */
	private String useCaseDescription;
	
	/** The connection of the network that was measured. */
	private String networkConnection;
	
	/** The longitude that was measured. */
	private double longitude;

	/** The latitude that was measured. */
	private double latitude;

	/** The battery power that was measured. */
	private double batteryPower;
	
	/** The cpu usage that was measured. */
	private double cpuUsage;
	
	/** The memory usage that was measured. */
	private double memoryUsage;
	
	/**
	 * Default constructor.
	 */
	public MobileClientData() {
		super();
	}	
	
	/**
	 * Constructor.
	 * 
	 * @param useCaseID Value for {@link #useCaseID}
	 */
	public MobileClientData(String useCaseID) {
		super(useCaseID);
	}

	/**
	 * Gets {@link #longitude}.
	 *
	 * @return {@link #longitude}
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Sets {@link #longitude}.
	 *
	 * @param longitude
	 *            New value for {@link #longitude}
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * Gets {@link #latitude}.
	 *
	 * @return {@link #latitude}
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Sets {@link #latitude}.
	 *
	 * @param latitude
	 *            New value for {@link #latitude}
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets {@link #batteryPower}.
	 *
	 * @return {@link #batteryPower}
	 */
	public double getBatteryPower() {
		return batteryPower;
	}

	/**
	 * Sets {@link #batteryPower}.
	 *
	 * @param batteryPower
	 *            New value for {@link #batteryPower}
	 */
	public void setBatteryPower(double batteryPower) {
		this.batteryPower = batteryPower;
	}

	/**
	 * Gets {@link #networkConnection}.
	 *
	 * @return {@link #networkConnection}
	 */
	public String getNetworkConnection() {
		return networkConnection;
	}

	/**
	 * Sets {@link #networkConnection}.
	 *
	 * @param networkConnection
	 *            New value for {@link #networkConnection}
	 */
	public void setNetworkConnection(String networkConnection) {
		this.networkConnection = networkConnection;
	}

	/**
	 * Gets {@link #cpuUsage}.
	 *
	 * @return {@link #cpuUsage}
	 */
	public double getCpuUsage() {
		return cpuUsage;
	}

	/**
	 * Sets {@link #cpuUsage}.
	 *
	 * @param cpuUsage
	 *            New value for {@link #cpuUsage}
	 */
	public void setCpuUsage(double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	/**
	 * Gets {@link #memoryUsage}.
	 *
	 * @return {@link #memoryUsage}
	 */
	public double getMemoryUsage() {
		return memoryUsage;
	}

	/**
	 * Sets {@link #memoryUsage}.
	 *
	 * @param memoryUsage
	 *            New value for {@link #memoryUsage}
	 */
	public void setMemoryUsage(double memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	
	/**
	 * Gets {@link #useCaseDescription}.
	 *
	 * @return {@link #useCaseDescription}
	 */
	public String getUseCaseDescription() {
		return useCaseDescription;
	}

	/**
	 * Sets {@link #useCaseDescription}.
	 *
	 * @param useCaseDescription
	 *            New value for {@link #useCaseDescription}
	 */
	public void setUseCaseDescription(String useCaseDescription) {
		this.useCaseDescription = useCaseDescription;
	}
	
	/**
	 * Get if {@link #networkConnection} is wlan.
	 *
	 * @return If {@link #networkConnection} is wlan
	 */
	public boolean hasWlan() {
		return (networkConnection != null && networkConnection.equalsIgnoreCase("WLAN"));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getInvocationAffiliationPercentage() {
		return 1.0d;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		result = (prime * result) + ((networkConnection == null) ? 0 : networkConnection.hashCode());
		result = (prime * result) + ((useCaseDescription == null) ? 0 : useCaseDescription.hashCode());
		temp = Double.doubleToLongBits(batteryPower);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(latitude);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(cpuUsage);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(memoryUsage);
		result = (prime * result) + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MobileClientData other = (MobileClientData) obj;
		if (networkConnection == null) {
			if (other.networkConnection != null) {
				return false;
			}
		} else if (!networkConnection.equals(other.networkConnection)) {
			return false;
		}
		if (useCaseDescription == null) {
			if (other.useCaseDescription != null) {
				return false;
			}
		} else if (!useCaseDescription.equals(other.useCaseDescription)) {
			return false;
		}
		if (longitude != other.longitude) {
			return false;
		}
		if (latitude != other.latitude) {
			return false;
		}
		if (batteryPower != other.batteryPower) {
			return false;
		}
		if (cpuUsage != other.cpuUsage) {
			return false;
		}
		if (memoryUsage != other.memoryUsage) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "MobileClientData [useCaseID=" + getUseCaseID() + ", useCaseDescription=" + useCaseDescription + ", longitude=" + longitude + ", latitude=" + latitude + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getObjectSize(IObjectSizes objectSizes, boolean doAlign) {
		long size = super.getObjectSize(objectSizes, doAlign);
		size += objectSizes.getPrimitiveTypesSize(0, 0, 0, 0, 0, 5);
		size += objectSizes.getSizeOf(networkConnection);
		size += objectSizes.getSizeOf(useCaseDescription);
		
		if (doAlign) {
			return objectSizes.alignTo8Bytes(size);
		} else {
			return size;
		}
	}
	
}
