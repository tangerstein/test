package rocks.inspectit.shared.all.communication.data.eum;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * @author Jonas Kunz
 *
 */
public class PageLoadRequest extends Request {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -2379341294938690998L;

	@JsonSerialize(include = Inclusion.NON_NULL)
	@JsonProperty
	private NavigationTimings navigationTimings;

	/**
	 * All timestamps are in MS relative to the epoche.
	 *
	 *
	 * @author Jonas Kunz
	 *
	 */
	public static class NavigationTimings {
		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("navigationStartW")
		private long navigationStart = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("unloadEventStartW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long unloadEventStart = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("unloadEventEndW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long unloadEventEnd = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("redirectStartW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long redirectStart = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("redirectEndW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long redirectEnd = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("fetchStartW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long fetchStart = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("domainLookupStartW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long domainLookupStart = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("domainLookupEndW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long domainLookupEnd = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("connectStartW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long connectStart = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("connectEndW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long connectEnd = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("secureConnectionStartW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long secureConnectionStart = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("requestStartW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long requestStart = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("responseStartW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long responseStart = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("responseEndW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long responseEnd = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("domLoadingW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long domLoading = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("domInteractiveW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long domInteractive = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("domContentLoadedEventStartW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long domContentLoadedEventStart = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("domContentLoadedEventEndW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long domContentLoadedEventEnd = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("domCompleteW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long domComplete = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("loadEventStartW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long loadEventStart = 0;

		/**
		 * refers to @see <a href=
		 * "https://www.w3.org/TR/navigation-timing/#sec-navigation-timing-interface">Navigation
		 * timings</a>.
		 */
		@JsonProperty("loadEventEndW")
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long loadEventEnd = 0;


		/**
		 * UEM speed index.
		 *
		 * @see <a href="https://github.com/WPO-Foundation/RUM-SpeedIndex">RUM speedindex</a>
		 */
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private double speedIndex = 0;

		/**
		 * First paint event which is involved in the speedindex calculation progress.
		 */
		@JsonSerialize(include = Inclusion.NON_DEFAULT)
		private long firstPaint = 0;

		/**
		 * Gets {@link #navigationStartW}.
		 *
		 * @return {@link #navigationStartW}
		 */
		public long getNavigationStart() {
			return this.navigationStart;
		}

		/**
		 * Sets {@link #navigationStartW}.
		 *
		 * @param navigationStartW
		 *            New value for {@link #navigationStartW}
		 */
		public void setNavigationStart(long navigationStart) {
			this.navigationStart = navigationStart;
		}

		/**
		 * Gets {@link #unloadEventStartW}.
		 *
		 * @return {@link #unloadEventStartW}
		 */
		public long getUnloadEventStart() {
			return this.unloadEventStart;
		}

		/**
		 * Sets {@link #unloadEventStartW}.
		 *
		 * @param unloadEventStartW
		 *            New value for {@link #unloadEventStartW}
		 */
		public void setUnloadEventStart(long unloadEventStart) {
			this.unloadEventStart = unloadEventStart;
		}

		/**
		 * Gets {@link #unloadEventEndW}.
		 *
		 * @return {@link #unloadEventEndW}
		 */
		public long getUnloadEventEnd() {
			return this.unloadEventEnd;
		}

		/**
		 * Sets {@link #unloadEventEndW}.
		 *
		 * @param unloadEventEndW
		 *            New value for {@link #unloadEventEndW}
		 */
		public void setUnloadEventEnd(long unloadEventEnd) {
			this.unloadEventEnd = unloadEventEnd;
		}

		/**
		 * Gets {@link #redirectStartW}.
		 *
		 * @return {@link #redirectStartW}
		 */
		public long getRedirectStart() {
			return this.redirectStart;
		}

		/**
		 * Sets {@link #redirectStartW}.
		 *
		 * @param redirectStartW
		 *            New value for {@link #redirectStartW}
		 */
		public void setRedirectStart(long redirectStart) {
			this.redirectStart = redirectStart;
		}

		/**
		 * Gets {@link #redirectEndW}.
		 *
		 * @return {@link #redirectEndW}
		 */
		public long getRedirectEnd() {
			return this.redirectEnd;
		}

		/**
		 * Sets {@link #redirectEndW}.
		 *
		 * @param redirectEndW
		 *            New value for {@link #redirectEndW}
		 */
		public void setRedirectEnd(long redirectEnd) {
			this.redirectEnd = redirectEnd;
		}

		/**
		 * Gets {@link #fetchStartW}.
		 *
		 * @return {@link #fetchStartW}
		 */
		public long getFetchStart() {
			return this.fetchStart;
		}

		/**
		 * Sets {@link #fetchStartW}.
		 *
		 * @param fetchStartW
		 *            New value for {@link #fetchStartW}
		 */
		public void setFetchStart(long fetchStart) {
			this.fetchStart = fetchStart;
		}

		/**
		 * Gets {@link #domainLookupStartW}.
		 *
		 * @return {@link #domainLookupStartW}
		 */
		public long getDomainLookupStart() {
			return this.domainLookupStart;
		}

		/**
		 * Sets {@link #domainLookupStartW}.
		 *
		 * @param domainLookupStartW
		 *            New value for {@link #domainLookupStartW}
		 */
		public void setDomainLookupStart(long domainLookupStart) {
			this.domainLookupStart = domainLookupStart;
		}

		/**
		 * Gets {@link #domainLookupEndW}.
		 *
		 * @return {@link #domainLookupEndW}
		 */
		public long getDomainLookupEnd() {
			return this.domainLookupEnd;
		}

		/**
		 * Sets {@link #domainLookupEndW}.
		 *
		 * @param domainLookupEndW
		 *            New value for {@link #domainLookupEndW}
		 */
		public void setDomainLookupEnd(long domainLookupEnd) {
			this.domainLookupEnd = domainLookupEnd;
		}

		/**
		 * Gets {@link #connectStartW}.
		 *
		 * @return {@link #connectStartW}
		 */
		public long getConnectStart() {
			return this.connectStart;
		}

		/**
		 * Sets {@link #connectStartW}.
		 *
		 * @param connectStartW
		 *            New value for {@link #connectStartW}
		 */
		public void setConnectStart(long connectStart) {
			this.connectStart = connectStart;
		}

		/**
		 * Gets {@link #connectEndW}.
		 *
		 * @return {@link #connectEndW}
		 */
		public long getConnectEnd() {
			return this.connectEnd;
		}

		/**
		 * Sets {@link #connectEndW}.
		 *
		 * @param connectEndW
		 *            New value for {@link #connectEndW}
		 */
		public void setConnectEnd(long connectEnd) {
			this.connectEnd = connectEnd;
		}

		/**
		 * Gets {@link #secureConnectionStartW}.
		 *
		 * @return {@link #secureConnectionStartW}
		 */
		public long getSecureConnectionStart() {
			return this.secureConnectionStart;
		}

		/**
		 * Sets {@link #secureConnectionStartW}.
		 *
		 * @param secureConnectionStartW
		 *            New value for {@link #secureConnectionStartW}
		 */
		public void setSecureConnectionStart(long secureConnectionStart) {
			this.secureConnectionStart = secureConnectionStart;
		}

		/**
		 * Gets {@link #requestStartW}.
		 *
		 * @return {@link #requestStartW}
		 */
		public long getRequestStart() {
			return this.requestStart;
		}

		/**
		 * Sets {@link #requestStartW}.
		 *
		 * @param requestStartW
		 *            New value for {@link #requestStartW}
		 */
		public void setRequestStart(long requestStart) {
			this.requestStart = requestStart;
		}

		/**
		 * Gets {@link #responseStartW}.
		 *
		 * @return {@link #responseStartW}
		 */
		public long getResponseStart() {
			return this.responseStart;
		}

		/**
		 * Sets {@link #responseStartW}.
		 *
		 * @param responseStartW
		 *            New value for {@link #responseStartW}
		 */
		public void setResponseStart(long responseStart) {
			this.responseStart = responseStart;
		}

		/**
		 * Gets {@link #responseEndW}.
		 *
		 * @return {@link #responseEndW}
		 */
		public long getResponseEnd() {
			return this.responseEnd;
		}

		/**
		 * Sets {@link #responseEndW}.
		 *
		 * @param responseEndW
		 *            New value for {@link #responseEndW}
		 */
		public void setResponseEnd(long responseEnd) {
			this.responseEnd = responseEnd;
		}

		/**
		 * Gets {@link #domLoadingW}.
		 *
		 * @return {@link #domLoadingW}
		 */
		public long getDomLoading() {
			return this.domLoading;
		}

		/**
		 * Sets {@link #domLoadingW}.
		 *
		 * @param domLoadingW
		 *            New value for {@link #domLoadingW}
		 */
		public void setDomLoading(long domLoading) {
			this.domLoading = domLoading;
		}

		/**
		 * Gets {@link #domInteractiveW}.
		 *
		 * @return {@link #domInteractiveW}
		 */
		public long getDomInteractive() {
			return this.domInteractive;
		}

		/**
		 * Sets {@link #domInteractiveW}.
		 *
		 * @param domInteractiveW
		 *            New value for {@link #domInteractiveW}
		 */
		public void setDomInteractive(long domInteractive) {
			this.domInteractive = domInteractive;
		}

		/**
		 * Gets {@link #domContentLoadedEventStartW}.
		 *
		 * @return {@link #domContentLoadedEventStartW}
		 */
		public long getDomContentLoadedEventStart() {
			return this.domContentLoadedEventStart;
		}

		/**
		 * Sets {@link #domContentLoadedEventStartW}.
		 *
		 * @param domContentLoadedEventStartW
		 *            New value for {@link #domContentLoadedEventStartW}
		 */
		public void setDomContentLoadedEventStart(long domContentLoadedEventStart) {
			this.domContentLoadedEventStart = domContentLoadedEventStart;
		}

		/**
		 * Gets {@link #domContentLoadedEventEndW}.
		 *
		 * @return {@link #domContentLoadedEventEndW}
		 */
		public long getDomContentLoadedEventEnd() {
			return this.domContentLoadedEventEnd;
		}

		/**
		 * Sets {@link #domContentLoadedEventEndW}.
		 *
		 * @param domContentLoadedEventEndW
		 *            New value for {@link #domContentLoadedEventEndW}
		 */
		public void setDomContentLoadedEventEnd(long domContentLoadedEventEnd) {
			this.domContentLoadedEventEnd = domContentLoadedEventEnd;
		}

		/**
		 * Gets {@link #domCompleteW}.
		 *
		 * @return {@link #domCompleteW}
		 */
		public long getDomComplete() {
			return this.domComplete;
		}

		/**
		 * Sets {@link #domCompleteW}.
		 *
		 * @param domCompleteW
		 *            New value for {@link #domCompleteW}
		 */
		public void setDomComplete(long domComplete) {
			this.domComplete = domComplete;
		}

		/**
		 * Gets {@link #loadEventStartW}.
		 *
		 * @return {@link #loadEventStartW}
		 */
		public long getLoadEventStart() {
			return this.loadEventStart;
		}

		/**
		 * Sets {@link #loadEventStartW}.
		 *
		 * @param loadEventStartW
		 *            New value for {@link #loadEventStartW}
		 */
		public void setLoadEventStart(long loadEventStart) {
			this.loadEventStart = loadEventStart;
		}

		/**
		 * Gets {@link #loadEventEndW}.
		 *
		 * @return {@link #loadEventEndW}
		 */
		public long getLoadEventEnd() {
			return this.loadEventEnd;
		}

		/**
		 * Sets {@link #loadEventEndW}.
		 *
		 * @param loadEventEndW
		 *            New value for {@link #loadEventEndW}
		 */
		public void setLoadEventEnd(long loadEventEnd) {
			this.loadEventEnd = loadEventEnd;
		}

		/**
		 * Gets {@link #speedindex}.
		 *
		 * @return {@link #speedindex}
		 */
		public double getSpeedIndex() {
			return this.speedIndex;
		}

		/**
		 * Sets {@link #speedindex}.
		 *
		 * @param speedindex
		 *            New value for {@link #speedindex}
		 */
		public void setSpeedIndex(double speedIndex) {
			this.speedIndex = speedIndex;
		}

		/**
		 * Gets {@link #firstpaint}.
		 *
		 * @return {@link #firstpaint}
		 */
		public long getFirstPaint() {
			return this.firstPaint;
		}

		/**
		 * Sets {@link #firstpaint}.
		 *
		 * @param firstpaint
		 *            New value for {@link #firstpaint}
		 */
		public void setFirstPaint(long firstpaint) {
			this.firstPaint = firstpaint;
		}

	}

}
