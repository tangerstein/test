package rocks.inspectit.shared.all.communication.data.eum.mobile;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author Tobias Angerstein, Alper Hidiroglu, Manuel Palenga
 *
 */
public class RemoteCallMeasurement extends MobileMeasurement {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = 8411848459847980488L;

	@JsonProperty(value = "remoteCallID")
	private String remoteCallID;
	
	@JsonProperty(value = "ssid")
	private String ssid;
	
	@JsonProperty(value = "networkConnection")
	private String networkConnection;
	
	@JsonProperty(value = "networkProvider")
	private String networkProvider;
	
	@JsonProperty(value = "responseCode")
	private long responseCode;
	
	@JsonProperty(value = "timeout")
	private boolean timeout;
	
	@JsonProperty(value = "longitude")
	private double longitude;

	@JsonProperty(value = "latitude")
	private double latitude;
		
	
	/**
	 * Constructor.
	 * 
	 * @param timestamp
	 * @param remoteCallID
	 * @param ssid
	 * @param networkConnection
	 * @param networkProvider
	 * @param responseCode
	 * @param timeout
	 * @param longitude
	 * @param latitude
	 */
	public RemoteCallMeasurement(long timestamp, String remoteCallID,
			String ssid, String networkConnection, String networkProvider,
			long responseCode, boolean timeout, double longitude,
			double latitude) {
		super(timestamp);
		this.remoteCallID = remoteCallID;
		this.ssid = ssid;
		this.networkConnection = networkConnection;
		this.networkProvider = networkProvider;
		this.responseCode = responseCode;
		this.timeout = timeout;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}	
	
	/**
	 * @return the remoteCallID
	 */
	public String getRemoteCallID() {
		return remoteCallID;
	}

	/**
	 * @param remoteCallID the remoteCallID to set
	 */
	public void setRemoteCallID(String remoteCallID) {
		this.remoteCallID = remoteCallID;
	}

	/**
	 * @return the ssid
	 */
	public String getSsid() {
		return ssid;
	}

	/**
	 * @param ssid the ssid to set
	 */
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	/**
	 * @return the networkProvider
	 */
	public String getNetworkProvider() {
		return networkProvider;
	}

	/**
	 * @param networkProvider the networkProvider to set
	 */
	public void setNetworkProvider(String networkProvider) {
		this.networkProvider = networkProvider;
	}

	/**
	 * @return the responseCode
	 */
	public long getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(long responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the timeout
	 */
	public boolean isTimeout() {
		return timeout;
	}

	/**
	 * @param timeout the timeout to set
	 */
	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}

	/**
	 * @param networkConnection the networkConnection to set
	 */
	public void setNetworkConnection(String networkConnection) {
		this.networkConnection = networkConnection;
	}

	/**
	 * @return the network connection
	 */
	public String getNetworkConnection() {
		return networkConnection;
	}
}
