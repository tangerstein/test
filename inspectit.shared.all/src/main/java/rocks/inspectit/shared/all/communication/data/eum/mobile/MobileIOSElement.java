package rocks.inspectit.shared.all.communication.data.eum.mobile;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import rocks.inspectit.shared.all.communication.DefaultData;

/**
 * 
 * @author Tobias Angerstein, Alper Hidiroglu, Manuel Palenga
 *
 */
public class MobileIOSElement extends DefaultData {
	
	/** Serial version id. */
	private static final long serialVersionUID = -8835803943354776498L;

	@JsonProperty(value = "useCaseDescription")
	private String useCaseDescription;

	@JsonProperty(value = "useCaseID")
	private String useCaseID;

	@JsonProperty(value = "measurements")
	private List<MobileMeasurement> measurements;

	/**
	 * Constructor.
	 * 
	 * @param useCaseDescription
	 * @param useCaseID
	 * @param measurements
	 * @param timeStamp
	 */
	public MobileIOSElement(String useCaseDescription, String useCaseID, List<MobileMeasurement> measurements,
			long timeStamp) {
		super();
		super.setTimeStamp(new Timestamp(timeStamp));
		this.useCaseDescription = useCaseDescription;
		this.useCaseID = useCaseID;
		this.measurements = measurements;
	}
	
	public String getUsecaseDescription() {
		return useCaseDescription;
	}

	public String getUsecaseID() {
		return useCaseID;
	}

	public List<MobileMeasurement> getMeasurements() {
		return measurements;
	}

	public long getDuration() {
		Collections.sort(measurements);
		return measurements.get(measurements.size() - 1).getTimestamp() - measurements.get(0).getTimestamp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((this.useCaseDescription == null) ? 0 : this.useCaseDescription.hashCode());
		result = (prime * result) + ((this.useCaseID == null) ? 0 : this.useCaseID.hashCode());
		result = (prime * result) + ((this.measurements == null) ? 0 : this.measurements.hashCode());
		return result;
	}

}
