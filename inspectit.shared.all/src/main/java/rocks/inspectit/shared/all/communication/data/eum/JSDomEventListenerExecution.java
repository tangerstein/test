package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author Jonas Kunz
 *
 */
public class JSDomEventListenerExecution extends JSEventListenerExecution{

	/**
	 *
	 */
	private static final long serialVersionUID = -3340240065540045237L;

	@JsonProperty
	private String elementType;

	@JsonProperty
	@JsonSerialize(include = Inclusion.NON_NULL)
	private String elementID;

}
