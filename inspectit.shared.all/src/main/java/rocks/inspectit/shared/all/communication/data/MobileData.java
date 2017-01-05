package rocks.inspectit.shared.all.communication.data;

import rocks.inspectit.shared.all.cmr.cache.IObjectSizes;

/**
 * Data class for mobile data capturing.
 *
 * @author Manuel Palenga
 */
public class MobileData extends InvocationAwareData {

	/** Serial version id. */
	private static final long serialVersionUID = 8538357552938963246L;
	
	/** The use case ID of the invocation. */
	private String useCaseID = null;
		
	/**
	 * Default constructor.
	 */
	public MobileData() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param useCaseID Value for {@link #useCaseID}
	 */
	public MobileData(String useCaseID) {
		super();
		this.useCaseID = useCaseID;
	}
	
	/**
	 * Sets {@link #useCaseID}.
	 *
	 * @param useCaseID
	 *            New value for {@link #useCaseID}
	 */
	public void setUseCaseID(String useCaseID) {
		this.useCaseID = useCaseID;
	}
	
	/**
	 * Gets {@link #useCaseID}.
	 *
	 * @return {@link #useCaseID}
	 */
	public String getUseCaseID() {
		return useCaseID;
	}
	
	/**
	 * Get if {@link #mobileTrace} is not empty.
	 *
	 * @return If {@link #mobileTrace} is not empty
	 */
	public boolean hasMobileClientData(){
		return (this instanceof MobileClientData);
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
		result = (prime * result) + ((useCaseID == null) ? 0 : useCaseID.hashCode());
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
		MobileData other = (MobileData) obj;
		if (useCaseID == null) {
			if (other.useCaseID != null) {
				return false;
			}
		} else if (!useCaseID.equals(other.useCaseID)) {
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "MobileData [useCaseID=" + useCaseID + "]";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getObjectSize(IObjectSizes objectSizes, boolean doAlign) {
		long size = super.getObjectSize(objectSizes, doAlign);
		size += objectSizes.getSizeOf(useCaseID);
		
		if (doAlign) {
			return objectSizes.alignTo8Bytes(size);
		} else {
			return size;
		}
	}
	
}
