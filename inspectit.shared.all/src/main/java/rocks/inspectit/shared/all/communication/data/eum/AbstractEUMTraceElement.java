package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author Jonas Kunz
 *
 */
public class AbstractEUMTraceElement extends AbstractEUMElement {

	@JsonProperty(value = "parentLocalID")
	@JsonSerialize(include = Inclusion.NON_DEFAULT)
	private long parentLocalID;

	@JsonProperty
	@JsonSerialize(include = Inclusion.NON_DEFAULT)
	private boolean isAsyncCall;

	@JsonProperty
	@JsonSerialize(include = Inclusion.NON_DEFAULT)
	private long enterTimestamp;

	@JsonProperty
	@JsonSerialize(include = Inclusion.NON_DEFAULT)
	private long exitTimestamp;

	public AbstractEUMTraceElement() {
		parentLocalID = 0;
		isAsyncCall = false;
		enterTimestamp = 0;
		exitTimestamp = 0;
	}

}
