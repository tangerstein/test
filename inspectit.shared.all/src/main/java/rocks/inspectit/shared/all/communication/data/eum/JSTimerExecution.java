package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Jonas Kunz
 *
 */
public class JSTimerExecution extends JSFunctionExecution {

	/**
	 *
	 */
	private static final long serialVersionUID = 1971211250584269261L;

	/**
	 * Defines the point of time when setTimeout / setInterval was called
	 */
	@JsonProperty
	private long initiatorCallTimestamp;

	@JsonProperty
	private long configuredTimeout;

	/**
	 * Holds the number of the iteration if the timer was an Intervall-Timer For timeout-tiemrs,
	 * this value is zero.
	 */
	@JsonProperty
	private int iterationNumber;
}
