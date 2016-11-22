package rocks.inspectit.agent.java.eum.data;

/**
 * @author Jonas Kunz
 *
 */
public class Beacon {

	private long sessionID;

	private long tabID;

	public String[] data;

	/**
	 * Gets {@link #sessionID}.
	 *
	 * @return {@link #sessionID}
	 */
	public long getSessionID() {
		return this.sessionID;
	}

	/**
	 * Sets {@link #sessionID}.
	 *
	 * @param sessionID
	 *            New value for {@link #sessionID}
	 */
	public void setSessionID(long sessionID) {
		this.sessionID = sessionID;
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
	 * Sets {@link #tabID}.
	 *
	 * @param tabID
	 *            New value for {@link #tabID}
	 */
	public void setTabID(long tabID) {
		this.tabID = tabID;
	}

}
