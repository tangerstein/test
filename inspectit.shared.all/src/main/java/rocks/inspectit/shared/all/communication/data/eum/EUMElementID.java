package rocks.inspectit.shared.all.communication.data.eum;

import java.io.Serializable;

/**
 * @author Jonas Kunz
 *
 */
public class EUMElementID implements Serializable {

	/**
	 * Serialization UUID.
	 */
	private static final long serialVersionUID = -7396170537157406276L;

	private long sessionID;
	private long tabID;
	private long localID;

	/**
	 * @param sessionID
	 * @param tabID
	 * @param elementID
	 */
	public EUMElementID(long sessionID, long tabID, long localID) {
		super();
		this.sessionID = sessionID;
		this.tabID = tabID;
		this.localID = localID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (int) (this.localID ^ (this.localID >>> 32));
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
		EUMElementID other = (EUMElementID) obj;
		if (this.localID != other.localID) {
			return false;
		}
		if (this.sessionID != other.sessionID) {
			return false;
		}
		if (this.tabID != other.tabID) {
			return false;
		}
		return true;
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

	/**
	 * Gets {@link #elementID}.
	 *
	 * @return {@link #elementID}
	 */
	public long getLocalID() {
		return this.localID;
	}

	/**
	 * Sets {@link #elementID}.
	 *
	 * @param localID
	 *            New value for {@link #elementID}
	 */
	public void setLocalID(long localID) {
		this.localID = localID;
	}

}
