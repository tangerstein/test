package rocks.inspectit.shared.all.communication.data.eum;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author Jonas Kunz
 *
 */
public class Beacon {

	public static final long REQUEST_NEW_SESSION_ID_MARKER = -1;
	public static final long REQUEST_NEW_TAB_ID_MARKER = -1;

	@JsonSerialize(include = Inclusion.NON_DEFAULT)
	private long sessionID;

	@JsonSerialize(include = Inclusion.NON_DEFAULT)
	private long tabID;

	@JsonSerialize(include = Inclusion.NON_EMPTY)
	public List<AbstractEUMElement> data;

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

	public void assignIDs(long sessionID, long tabID) {
		this.sessionID = sessionID;
		this.tabID = tabID;
		for (AbstractEUMElement element : data) {
			element.setSessionID(sessionID);
			element.setTabID(tabID);
		}
	}

}
