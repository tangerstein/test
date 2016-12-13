package rocks.inspectit.shared.all.communication.data.eum;

/**
 * Request which contains informations about a single resource load. (e.g. CSS file)
 *
 * @author David Monschein, Jonas Kunz
 */
public class ResourceLoadRequest extends AbstractRequest {

	/**
	 * serial Version UID.
	 */
	private static final long serialVersionUID = 583794863578163599L;

	/**
	 * Determines from what the resource loading got triggered.
	 */
	private String initiatorType;

	/**
	 * The size in octets of the resource.
	 */
	private long transferSize;

	/**
	 * Gets {@link #initiatorType}.
	 *
	 * @return {@link #initiatorType}
	 */
	public String getInitiatorType() {
		return this.initiatorType;
	}

	/**
	 * Gets {@link #transferSize}.
	 *
	 * @return {@link #transferSize}
	 */
	public long getTransferSize() {
		return this.transferSize;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((this.initiatorType == null) ? 0 : this.initiatorType.hashCode());
		result = (prime * result) + (int) (this.transferSize ^ (this.transferSize >>> 32));
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
		ResourceLoadRequest other = (ResourceLoadRequest) obj;
		if (this.initiatorType == null) {
			if (other.initiatorType != null) {
				return false;
			}
		} else if (!this.initiatorType.equals(other.initiatorType)) {
			return false;
		}
		if (this.transferSize != other.transferSize) { // NOPMD
			return false;
		}
		return true;
	}



}
