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
	
	/** The longitude that was measured. */
	private float longitude;

	/** The latitude that was measured. */
	private float latitude;

	/** The power that was measured. */
	private int power;

	/** The connection of the network that was measured. */
	private String networkConnection;
	
	/** The cpu that was measured. */
	private float cpu;
	
	/** The memory that was measured. */
	private float memory;

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
	public float getLongitude() {
		return longitude;
	}

	/**
	 * Sets {@link #longitude}.
	 *
	 * @param longitude
	 *            New value for {@link #longitude}
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	/**
	 * Gets {@link #latitude}.
	 *
	 * @return {@link #latitude}
	 */
	public float getLatitude() {
		return latitude;
	}

	/**
	 * Sets {@link #latitude}.
	 *
	 * @param latitude
	 *            New value for {@link #latitude}
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	/**
	 * Gets {@link #power}.
	 *
	 * @return {@link #power}
	 */
	public int getPower() {
		return power;
	}

	/**
	 * Sets {@link #power}.
	 *
	 * @param power
	 *            New value for {@link #power}
	 */
	public void setPower(int power) {
		this.power = power;
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
	 * Gets {@link #cpu}.
	 *
	 * @return {@link #cpu}
	 */
	public float getCpu() {
		return cpu;
	}

	/**
	 * Sets {@link #cpu}.
	 *
	 * @param cpu
	 *            New value for {@link #cpu}
	 */
	public void setCpu(float cpu) {
		this.cpu = cpu;
	}

	/**
	 * Gets {@link #memory}.
	 *
	 * @return {@link #memory}
	 */
	public float getMemory() {
		return memory;
	}

	/**
	 * Sets {@link #memory}.
	 *
	 * @param memory
	 *            New value for {@link #memory}
	 */
	public void setMemory(float memory) {
		this.memory = memory;
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
		result = (prime * result) + ((networkConnection == null) ? 0 : networkConnection.hashCode());
		result = (prime * result) + ((useCaseDescription == null) ? 0 : useCaseDescription.hashCode());
		result = (prime * result) + (int) (power ^ (power >>> 32));
		result = (prime * result) + Float.floatToIntBits(longitude);
		result = (prime * result) + Float.floatToIntBits(latitude);
		result = (prime * result) + Float.floatToIntBits(cpu);
		result = (prime * result) + Float.floatToIntBits(memory);
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
		if (power != other.power) {
			return false;
		}
		if (cpu != other.cpu) {
			return false;
		}
		if (memory != other.memory) {
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
		size += objectSizes.getPrimitiveTypesSize(0, 0, 1, 4, 0, 0);
		size += objectSizes.getSizeOf(networkConnection);
		size += objectSizes.getSizeOf(useCaseDescription);
		
		if (doAlign) {
			return objectSizes.alignTo8Bytes(size);
		} else {
			return size;
		}
	}
	
}
