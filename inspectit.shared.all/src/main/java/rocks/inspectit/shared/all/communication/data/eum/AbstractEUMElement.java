package rocks.inspectit.shared.all.communication.data.eum;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import rocks.inspectit.shared.all.communication.DefaultData;
import rocks.inspectit.shared.all.communication.data.eum.mobile.MobileIOSElement;


/**
 * Base class for all types of EUM Data which can be contained in an EUM beacon. Elements are
 * identifiable by a globally unique {@link EUMElementID}.
 *
 * @author Jonas Kunz
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
	@Type(name = "pageLoadAction", value = PageLoadAction.class),
	@Type(name = "pageLoadRequest", value = PageLoadRequest.class),
	@Type(name = "resourceLoadRequest", value = ResourceLoadRequest.class),
	@Type(name = "ajaxRequest", value = AjaxRequest.class),
	@Type(name = "metaInfo", value = UserSessionInfo.class),
	@Type(name = "timerExecution", value = JSTimerExecution.class),
	@Type(name = "listenerExecution", value = JSEventListenerExecution.class),
		@Type(name = "domListenerExecution", value = JSDomEventListenerExecution.class),
		@Type(name = "IOSMeasuredUseCase", value = MobileIOSElement.class)

})
public class AbstractEUMElement extends DefaultData implements Serializable {

	/**
	 * Serial version UUID.
	 */
	private static final long serialVersionUID = -9028143779219068642L;

	/**
	 * The ID globally unique ID of this element.
	 */
	@JsonIgnore
	private EUMElementID euElementId;

	/**
	 * Default constructs, assigns a null-id to this object.
	 */
	public AbstractEUMElement() {
		euElementId = new EUMElementID();
	}

	/**
	 * Sets the local ID part of the ID of this element. The local ID is uniquely identifies this
	 * elment within a tab.
	 *
	 * @param localId
	 *            the localID to set
	 */
	@JsonIgnore
	public void setLocalID(long localId) {
		this.euElementId.setLocalID(localId);
	}

	/**
	 * Sets the identifier of the tab within which this element was generated.
	 *
	 * @param tabId
	 *            the tabID of the tab within which this element was generated.
	 */
	@JsonIgnore
	public void setTabID(long tabId) {
		this.euElementId.setTabID(tabId);
	}

	/**
	 * Sets the identifier of the session within which this element was generated.
	 *
	 * @param sessionId
	 *            the id of the session
	 */
	@JsonIgnore
	public void setSessionID(long sessionId) {
		this.euElementId.setSessionID(sessionId);
	}

	/**
	 * Returns the complete ID of this element.
	 *
	 * @return the {@link EUMElementID} of this element.
	 */
	@JsonIgnore
	public EUMElementID getID() {
		return this.euElementId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((this.euElementId == null) ? 0 : this.euElementId.hashCode());
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
		AbstractEUMElement other = (AbstractEUMElement) obj;
		if (this.euElementId == null) {
			if (other.euElementId != null) {
				return false;
			}
		} else if (!this.euElementId.equals(other.euElementId)) {
			return false;
		}
		return true;
	}

}
