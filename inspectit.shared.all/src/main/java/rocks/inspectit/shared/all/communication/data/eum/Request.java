package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Jonas Kunz
 *
 */
public abstract class Request extends AbstractEUMTraceElement {

	@JsonProperty
	private String url;

	/**
	 * Gets {@link #url}.
	 *
	 * @return {@link #url}
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Sets {@link #url}.
	 *
	 * @param url
	 *            New value for {@link #url}
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
