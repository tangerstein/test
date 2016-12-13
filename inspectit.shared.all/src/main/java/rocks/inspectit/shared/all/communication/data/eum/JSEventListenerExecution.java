package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Represents the execution of JS Event listener function.
 *
 * @author Jonas Kunz
 *
 */
public class JSEventListenerExecution extends JSFunctionExecution {

	/**
	 * ther serial version UID.
	 */
	private static final long serialVersionUID = -3061447548235961509L;

	/**
	 * The type of the event which occured.
	 */
	@JsonProperty
	private String eventType;

	/**
	 * Gets {@link #eventType}.
	 * 
	 * @return {@link #eventType}
	 */
	public String getEventType() {
		return this.eventType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((this.eventType == null) ? 0 : this.eventType.hashCode());
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
		JSEventListenerExecution other = (JSEventListenerExecution) obj;
		if (this.eventType == null) {
			if (other.eventType != null) {
				return false;
			}
		} else if (!this.eventType.equals(other.eventType)) {
			return false;
		}
		return true;
	}

}
