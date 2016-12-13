package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * EUM element representing an AJAX request.
 *
 * @author David Monschein, Jonas Kunz
 */
public class AjaxRequest extends AbstractRequest {

	/**
	 * serial Version UID.
	 */
	private static final long serialVersionUID = -2318566427302336923L;

	/**
	 * Status with which the Ajax request was completed. (e.g. 200 for successful)
	 */
	@JsonProperty
	private int status;

	/**
	 * Method which was used to send the Ajax request (e.g. GET or POST).
	 */
	@JsonProperty
	private String method;

	/**
	 * Gets {@link #status}.
	 *
	 * @return {@link #status}
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * Gets {@link #method}.
	 *
	 * @return {@link #method}
	 */
	public String getMethod() {
		return this.method;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((this.method == null) ? 0 : this.method.hashCode());
		result = (prime * result) + this.status;
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
		AjaxRequest other = (AjaxRequest) obj;
		if (this.method == null) {
			if (other.method != null) {
				return false;
			}
		} else if (!this.method.equals(other.method)) {
			return false;
		}
		if (this.status != other.status) { // NOPMD
			return false;
		}
		return true;
	}

}
