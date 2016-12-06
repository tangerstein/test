package rocks.inspectit.shared.all.communication.data.eum;

/**
 * Representing an AJAX request.
 *
 * @author David Monschein
 */
public class AjaxRequest extends Request {

	/**
	 * serial Version UID.
	 */
	private static final long serialVersionUID = -2318566427302336923L;

	/**
	 * Status with which the Ajax request was completed. (e.g. 200 for successful)
	 */
	private int status;

	/**
	 * Method which was used to send the Ajax request (e.g. GET or POST).
	 */
	private String method;

	/**
	 * Gets {@link #status}.
	 * 
	 * @return {@link #status}
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * Sets {@link #status}.
	 * 
	 * @param status
	 *            New value for {@link #status}
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Gets {@link #method}.
	 * 
	 * @return {@link #method}
	 */
	public String getMethod() {
		return this.method;
	}

	/**
	 * Sets {@link #method}.
	 * 
	 * @param method
	 *            New value for {@link #method}
	 */
	public void setMethod(String method) {
		this.method = method;
	}


}
