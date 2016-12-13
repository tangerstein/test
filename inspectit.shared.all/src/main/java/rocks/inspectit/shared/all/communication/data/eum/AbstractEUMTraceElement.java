package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * Base class for all EUM elements which can participate in a EUM trace.
 *
 * Traces are stored by child elements referencing their parents based on their
 * {@link EUMElementID}.
 *
 * Asynchronous (= non-blocking) calls are marked by a flag and elements
 *
 * @author Jonas Kunz
 *
 */
public class AbstractEUMTraceElement extends AbstractEUMElement {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1814842777577166266L;

	/**
	 * The local ID of the parent of this element within the trace. The sessionID and the tabID are
	 * equal to the sessionID and tabID of this element, as elements in a trace are always local to
	 * a tab. If this element is a root element, emaning that is has no parent, the value of this
	 * variable is zero.
	 */
	@JsonProperty(value = "parentLocalID")
	@JsonSerialize(include = Inclusion.NON_DEFAULT)
	private long parentLocalID;

	/**
	 * A flag indicating whether this call was a non-blocking call, e.g. the issue of an AJAX
	 * request.
	 */
	@JsonProperty
	@JsonSerialize(include = Inclusion.NON_DEFAULT)
	private boolean isAsyncCall; // NOPMD

	/**
	 * The timestamp storing the enter time of this operation, if available. If the timestamp is not
	 * available, the value of this variable is zero.
	 */
	@JsonProperty
	@JsonSerialize(include = Inclusion.NON_DEFAULT)
	private long enterTimestamp;

	/**
	 * The timestamp storing the exit time of this operation, if available. If the timestamp is not
	 * available, the value of this variable is zero.
	 */
	@JsonProperty
	@JsonSerialize(include = Inclusion.NON_DEFAULT)
	private long exitTimestamp;

	/**
	 * Default Constructor.
	 */
	public AbstractEUMTraceElement() {
		parentLocalID = 0;
		isAsyncCall = false;
		enterTimestamp = 0;
		exitTimestamp = 0;
	}

	/**
	 * Gets {@link #parentLocalID}.
	 *
	 * @return {@link #parentLocalID}
	 */
	public long getParentLocalID() {
		return this.parentLocalID;
	}

	/**
	 * Gets {@link #isAsyncCall}.
	 *
	 * @return {@link #isAsyncCall}
	 */
	public boolean isAsyncCall() {
		return this.isAsyncCall;
	}

	/**
	 * Gets {@link #enterTimestamp}.
	 *
	 * @return {@link #enterTimestamp}
	 */
	public long getEnterTimestamp() {
		return this.enterTimestamp;
	}

	/**
	 * Gets {@link #exitTimestamp}.
	 *
	 * @return {@link #exitTimestamp}
	 */
	public long getExitTimestamp() {
		return this.exitTimestamp;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + (int) (this.enterTimestamp ^ (this.enterTimestamp >>> 32));
		result = (prime * result) + (int) (this.exitTimestamp ^ (this.exitTimestamp >>> 32));
		result = (prime * result) + (this.isAsyncCall ? 1231 : 1237);
		result = (prime * result) + (int) (this.parentLocalID ^ (this.parentLocalID >>> 32));
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
		AbstractEUMTraceElement other = (AbstractEUMTraceElement) obj;
		if (this.enterTimestamp != other.enterTimestamp) {
			return false;
		}
		if (this.exitTimestamp != other.exitTimestamp) {
			return false;
		}
		if (this.isAsyncCall != other.isAsyncCall) {
			return false;
		}
		if (this.parentLocalID != other.parentLocalID) { // NOPMD
			return false;
		}
		return true;
	}

}
