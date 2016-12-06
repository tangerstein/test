package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Jonas Kunz
 *
 */
public abstract class JSFunctionExecution extends AbstractEUMTraceElement {

	/**
	 *
	 */
	private static final long serialVersionUID = 2033672104732276882L;

	@JsonProperty
	private String functionName;

}
