package rocks.inspectit.shared.all.communication.data.eum;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import rocks.inspectit.shared.all.communication.DefaultData;

/**
 * 
 * @author Tobias Angerstein, Alper Hidiroglu, Manuel Palenga
 *
 */
public class AbstractBeacon {
	/**
	 * The contents of this beacon.
	 */
	@JsonSerialize(include = Inclusion.NON_EMPTY)
	@JsonProperty
	private List<DefaultData> data;

	/**
	 * Default constructor
	 */
	public AbstractBeacon() {
		data = new ArrayList<DefaultData>();
	}

	/**
	 * Gets {@link #data}.
	 *
	 * @return {@link #data}f
	 */
	public List<DefaultData> getData() {
		return this.data;
	}
}
