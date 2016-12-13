package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Represents the execution of a timer callback, either set using the JS function setTimeout or
 * using setInterval.
 *
 * @author Jonas Kunz
 *
 */
public class JSTimerExecution extends JSFunctionExecution {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 1971211250584269261L;

	/**
	 * The timestamp when setTimeout / setInterval was called. Therefore, the actual timeout can be
	 * computed using this value and {@link AbstractEUMTraceElement#getEnterTimestamp()}.
	 */
	@JsonProperty
	private long initiatorCallTimestamp;

	/**
	 * The timeout or interval in milliseconds passed to the setTimeout / setInterval call.
	 */
	@JsonProperty
	private long configuredTimeout;

	/**
	 * Stores the number of the iteration in case of an interval timer, starting with one for the
	 * first iteration. if the tiemr is a timer set using setTimeout instead, the value of this
	 * variable is zero.
	 */
	@JsonProperty
	private int iterationNumber;

	/**
	 * Gets {@link #initiatorCallTimestamp}.
	 *
	 * @return {@link #initiatorCallTimestamp}
	 */
	public long getInitiatorCallTimestamp() {
		return this.initiatorCallTimestamp;
	}

	/**
	 * Gets {@link #configuredTimeout}.
	 *
	 * @return {@link #configuredTimeout}
	 */
	public long getConfiguredTimeout() {
		return this.configuredTimeout;
	}

	/**
	 * Gets {@link #iterationNumber}.
	 *
	 * @return {@link #iterationNumber}
	 */
	public int getIterationNumber() {
		return this.iterationNumber;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + (int) (this.configuredTimeout ^ (this.configuredTimeout >>> 32));
		result = (prime * result) + (int) (this.initiatorCallTimestamp ^ (this.initiatorCallTimestamp >>> 32));
		result = (prime * result) + this.iterationNumber;
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
		JSTimerExecution other = (JSTimerExecution) obj;
		if (this.configuredTimeout != other.configuredTimeout) {
			return false;
		}
		if (this.initiatorCallTimestamp != other.initiatorCallTimestamp) {
			return false;
		}
		if (this.iterationNumber != other.iterationNumber) { // NOPMD
			return false;
		}
		return true;
	}

}
