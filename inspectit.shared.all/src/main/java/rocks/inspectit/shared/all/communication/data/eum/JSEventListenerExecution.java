package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Jonas Kunz
 *
 */
public class JSEventListenerExecution extends JSFunctionExecution {

	/**
	 *
	 */
	private static final long serialVersionUID = -3061447548235961509L;

	@JsonProperty
	private String eventType;

}
