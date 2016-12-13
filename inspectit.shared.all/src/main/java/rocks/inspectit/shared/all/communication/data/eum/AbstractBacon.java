package rocks.inspectit.shared.all.communication.data.eum;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

public class AbstractBacon {
	/**
	 * The contents of this beacon.
	 */
	@JsonSerialize(include = Inclusion.NON_EMPTY)
	@JsonProperty
	private List<AbstractEUMElement> data;

	/**
	 * Default constructor
	 */
	public AbstractBacon() {
		data = new ArrayList<AbstractEUMElement>();
	}

	/**
	 * Gets {@link #data}.
	 *
	 * @return {@link #data}
	 */
	public List<AbstractEUMElement> getData() {
		return this.data;
	}
}
