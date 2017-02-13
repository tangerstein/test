package rocks.inspectit.shared.all.communication.data.eum.mobile;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author Tobias Angerstein, Alper Hidiroglu, Manuel Palenga
 *
 */
public class RemoteCallMeasurementContainer implements Serializable{

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 174346732219191871L;

	@JsonProperty
	private RemoteCallMeasurement requestMeasurement;
	
	@JsonProperty
	private RemoteCallMeasurement responseMeasurement;

	/**
	 * Constructor.
	 * 
	 * @param requestMeasurement
	 * @param responseMeasurement
	 */
	public RemoteCallMeasurementContainer(
			RemoteCallMeasurement requestMeasurement,
			RemoteCallMeasurement responseMeasurement) {
		super();
		this.requestMeasurement = requestMeasurement;
		this.responseMeasurement = responseMeasurement;
	}
	
	/**
	 * 
	 * @return requestMeasurement
	 */
	public RemoteCallMeasurement getRequestMeasurement() {
		return requestMeasurement;
	}
	
	/**
	 * 
	 * @return responseMeasurement
	 */
	public RemoteCallMeasurement getResponseMeasurement() {
		return responseMeasurement;
	}
	
}
