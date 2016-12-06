package rocks.inspectit.shared.all.communication.data.eum;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;


/**
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
	@Type(name = "domListenerExecution", value = JSDomEventListenerExecution.class)
})
public class AbstractEUMElement implements Serializable {

	/**
	 * Serial version UUID.
	 */
	private static final long serialVersionUID = -9028143779219068642L;

	@JsonIgnore
	private EUMElementID id;

	public AbstractEUMElement() {
		id = new EUMElementID(0, 0, 0);
	}

	@JsonProperty(value = "id")
	public void setLocalID(long localId) {
		this.id.setLocalID(localId);
	}

	@JsonIgnore
	public void setTabID(long sessionId) {
		this.id.setTabID(sessionId);
	}

	@JsonIgnore
	public void setSessionID(long sessionId) {
		this.id.setSessionID(sessionId);
	}

	@JsonIgnore
	public EUMElementID getID() {
		return this.id;
	}

}
