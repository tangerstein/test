package rocks.inspectit.shared.all.communication.data.eum;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.ser.std.ToStringSerializer;

/**
 * Definition of the format of beacons sent by the EUM JS Agent.
 *
 * The beacon header stores the sessionID and tabID, which is not stored for each element explictly.
 * Therefore, after receiving a beacon, these IDs have to be assigned based on the beacon header to
 * the elements contained within the beacon.
 *
 * The JS Agent can signalize the server that it does not have an ID yet by setting the value of the
 * corresponding ID to {@link Beacon#REQUEST_NEW_TAB_ID_MARKER} or
 * {@link Beacon#REQUEST_NEW_SESSION_ID_MARKER}. The server is then responsible for assigning a new
 * ID and sending it as a response to the beacon.
 *
 * E.g. when the JS Agent sends a beacon with the tab ID of "-1", the server generates a new tab ID
 * which is assigned to all data sent with the beacon and responds with "{ tabID : [newID] }". This
 * id must then be reused by the JS agent for the other beacons.
 *
 * @author Jonas Kunz
 *
 */
public class Beacon {

	/**
	 * Special ID for marking that the JS Agent requires a new session ID.
	 */
	public static final long REQUEST_NEW_SESSION_ID_MARKER = -1;

	/**
	 * Special ID for marking that the JS Agent requires a new tab ID.
	 */
	public static final long REQUEST_NEW_TAB_ID_MARKER = -1;

	/**
	 * The sessionID of the JS Agent which sent this beacon.
	 */
	@JsonSerialize(include = Inclusion.NON_DEFAULT, using = ToStringSerializer.class)
	@JsonProperty
	private long sessionID;

	/**
	 * The tabID of the JS Agent which sent this beacon.
	 */
	@JsonSerialize(include = Inclusion.NON_DEFAULT, using = ToStringSerializer.class)
	@JsonProperty
	private long tabID;

	/**
	 * The contents of this beacon.
	 */
	@JsonSerialize(include = Inclusion.NON_EMPTY)
	@JsonProperty
	private List<AbstractEUMElement> data;

	/**
	 * Default cosntructor.
	 */
	public Beacon() {
		sessionID = REQUEST_NEW_SESSION_ID_MARKER;
		tabID = REQUEST_NEW_TAB_ID_MARKER;
		data = new ArrayList<AbstractEUMElement>();
	}

	/**
	 * Gets {@link #sessionID}.
	 *
	 * @return {@link #sessionID}
	 */
	public long getSessionID() {
		return this.sessionID;
	}

	/**
	 * Gets {@link #tabID}.
	 *
	 * @return {@link #tabID}
	 */
	public long getTabID() {
		return this.tabID;
	}

	/**
	 * Gets {@link #data}.
	 *
	 * @return {@link #data}
	 */
	public List<AbstractEUMElement> getData() {
		return this.data;
	}

	/**
	 * Assigns an ID to this beacon and all contained elements.
	 *
	 * @param sessionID
	 *            the sessionID to assign
	 * @param tabID
	 *            the tabID to assign
	 */
	public void assignIDs(long sessionID, long tabID) {
		this.sessionID = sessionID;
		this.tabID = tabID;
		for (AbstractEUMElement element : data) {
			element.setSessionID(sessionID);
			element.setTabID(tabID);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.data == null) ? 0 : this.data.hashCode());
		result = (prime * result) + (int) (this.sessionID ^ (this.sessionID >>> 32));
		result = (prime * result) + (int) (this.tabID ^ (this.tabID >>> 32));
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
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Beacon other = (Beacon) obj;
		if (this.data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!this.data.equals(other.data)) {
			return false;
		}
		if (this.sessionID != other.sessionID) {
			return false;
		}
		if (this.tabID != other.tabID) { // NOPMD
			return false;
		}
		return true;
	}


}
