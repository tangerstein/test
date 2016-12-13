package rocks.inspectit.shared.all.communication.data.eum;

import java.util.Collections;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class MobileIOSElement extends AbstractEUMElement {
	/**
	 * S
	 */
	private static final long serialVersionUID = -8835803943354776498L;

	public MobileIOSElement(String usecaseDescription, String usecaseID, List<MobileIOSMeasurement> measurements) {
		super();
		this.usecaseDescription = usecaseDescription;
		this.usecaseID = usecaseID;
		this.measurements = measurements;
	}

	@JsonProperty(value = "useCaseDescription")
	private String usecaseDescription;

	@JsonProperty(value = "useCaseID")
	private String usecaseID;

	@JsonProperty(value = "measurements")
	private List<MobileIOSMeasurement> measurements;

	public String getUsecaseDescription() {
		return usecaseDescription;
	}

	public String getUsecaseID() {
		return usecaseID;
	}

	public List<MobileIOSMeasurement> getMeasurements() {
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
		result = (prime * result) + ((this.usecaseDescription == null) ? 0 : this.usecaseDescription.hashCode());
		result = (prime * result) + ((this.usecaseID == null) ? 0 : this.usecaseID.hashCode());
		result = (prime * result) + ((this.measurements == null) ? 0 : this.measurements.hashCode());
		return result;
	}

}
