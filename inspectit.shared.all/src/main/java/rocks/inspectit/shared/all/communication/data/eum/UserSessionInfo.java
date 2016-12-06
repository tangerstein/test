package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Containing informations about an user session. A session should be unique for every user.
 *
 * @author David Monschein
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSessionInfo extends AbstractEUMElement {

	/**
	 * serial Version UID.
	 */
	private static final long serialVersionUID = 2607499843063635013L;

	/**
	 * The browser name.
	 */
	private String browser;

	/**
	 * The device name.
	 */
	private String device;

	/**
	 * The browser language.
	 */
	private String language;

	/**
	 * Creates a new user session containing no information about the user.
	 */
	public UserSessionInfo() {
	}

	/**
	 * Gets {@link #browser}.
	 *
	 * @return {@link #browser}
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * Sets {@link #browser}.
	 *
	 * @param browser
	 *            New value for {@link #browser}
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * Gets {@link #device}.
	 *
	 * @return {@link #device}
	 */
	public String getDevice() {
		return device;
	}

	/**
	 * Sets {@link #device}.
	 *
	 * @param device
	 *            New value for {@link #device}
	 */
	public void setDevice(String device) {
		this.device = device;
	}

	/**
	 * Gets {@link #language}.
	 *
	 * @return {@link #language}
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets {@link #language}.
	 *
	 * @param language
	 *            New value for {@link #language}
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	@JsonIgnore
	public void setLocalID(long localId) {

	}

	@Override
	@JsonIgnore
	public void setTabID(long sessionId) {
	}

}
