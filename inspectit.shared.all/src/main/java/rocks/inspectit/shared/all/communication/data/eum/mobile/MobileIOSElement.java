package rocks.inspectit.shared.all.communication.data.eum.mobile;

import java.sql.Timestamp;
import java.util.ArrayList;
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

	@JsonProperty(value = "remoteCalls")
	private List<RemoteCallMeasurementContainer> remoteCalls;
	
	@JsonProperty(value = "startMeasurement")
	private MobilePeriodicMeasurement startMeasurement;
	
	@JsonProperty(value = "stopMeasurement")
	private MobilePeriodicMeasurement stopMeasurement;

	/**
	 * Default constructor.
	 */
	public MobileIOSElement() {
		remoteCalls = new ArrayList<RemoteCallMeasurementContainer>();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param useCaseDescription
	 * @param useCaseID
	 * @param measurements
	 * @param timeStamp
	 */
	public MobileIOSElement(String useCaseDescription, String useCaseID, long timeStamp, 
			List<RemoteCallMeasurementContainer> remoteCalls, MobilePeriodicMeasurement startMeasurement, MobilePeriodicMeasurement stopMeasurement) {
		super();
		super.setTimeStamp(new Timestamp(timeStamp));
		this.useCaseDescription = useCaseDescription;
		this.useCaseID = useCaseID;
		this.remoteCalls = remoteCalls;
		this.startMeasurement = startMeasurement;
		this.stopMeasurement = stopMeasurement;
	}	

	/**
	 * @return the useCaseDescription
	 */
	public String getUseCaseDescription() {
		return useCaseDescription;
	}

	/**
	 * @param useCaseDescription the useCaseDescription to set
	 */
	public void setUseCaseDescription(String useCaseDescription) {
		this.useCaseDescription = useCaseDescription;
	}

	/**
	 * @return the useCaseID
	 */
	public String getUseCaseID() {
		return useCaseID;
	}

	/**
	 * @param useCaseID the useCaseID to set
	 */
	public void setUseCaseID(String useCaseID) {
		this.useCaseID = useCaseID;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @param remoteCalls the remoteCalls to set
	 */
	public void setRemoteCalls(List<RemoteCallMeasurementContainer> remoteCalls) {
		this.remoteCalls = remoteCalls;
	}

	/**
	 * @param startMeasurement the startMeasurement to set
	 */
	public void setStartMeasurement(MobilePeriodicMeasurement startMeasurement) {
		this.startMeasurement = startMeasurement;
	}

	/**
	 * @param stopMeasurement the stopMeasurement to set
	 */
	public void setStopMeasurement(MobilePeriodicMeasurement stopMeasurement) {
		this.stopMeasurement = stopMeasurement;
	}
	
	public String getUsecaseDescription() {
		return useCaseDescription;
	}

	public String getUsecaseID() {
		return useCaseID;
	}
	
	public MobilePeriodicMeasurement getStartMeasurement() {
		return startMeasurement;
	}
	
	public MobilePeriodicMeasurement getStopMeasurement() {
		return stopMeasurement;
	}

	public List<RemoteCallMeasurementContainer> getRemoteCalls() {
		return remoteCalls;
	}

	public long getDuration() {
		if(stopMeasurement == null || startMeasurement == null){
			return 0;
		}
		return stopMeasurement.getTimestamp() - startMeasurement.getTimestamp();
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
		result = (prime * result) + ((this.remoteCalls == null) ? 0 : this.remoteCalls.hashCode());
		result = (prime * result) + ((this.startMeasurement == null) ? 0 : this.startMeasurement.hashCode());
		result = (prime * result) + ((this.stopMeasurement == null) ? 0 : this.stopMeasurement.hashCode());
		return result;
	}

}
