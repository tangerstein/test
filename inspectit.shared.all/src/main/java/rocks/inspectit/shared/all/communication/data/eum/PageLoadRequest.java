package rocks.inspectit.shared.all.communication.data.eum;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * Represents the initial request responsible for loadign hte page showed in this tab. This request
 * is always a direct child of the corresponding {@link PageLoadAction}.
 *
 * @author David Monschein, Jonas Kunz
 *
 */
public class PageLoadRequest extends AbstractRequest {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -2379341294938690998L;

	/**
	 * If the capturing of the navigation timings or the speedindex was enabled, this field will
	 * hold the measured values.
	 */
	@JsonSerialize(include = Inclusion.NON_NULL)
	@JsonProperty
	private NavigationTimings navigationTimings;

	/**
	 * Stores the number of resources this pageload request laoded explicitly.
	 */
	@JsonSerialize(include = Inclusion.NON_DEFAULT)
	@JsonProperty
	private int resourceCount = -1;

	/**
	 * Stores the navigation timings and the speedindex if the corresponding modules are enabled.
	 * All timing fields store timestamps in milliseconds relative to the epoche.
	 *
	 * if a certain timing was not available, the corresponding field holds a zero value isntead.
	 *
	 * @author Jonas Kunz
	 *
	 */
	public static class NavigationTimings implements Serializable {

		/**
		 * serial version UID.
		 */
		private static final long serialVersionUID = 102220423619146599L;

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
		 * Gets {@link #navigationStart}.
		 *
		 * @return {@link #navigationStart}
		 */
		public long getNavigationStart() {
			return this.navigationStart;
		}

		/**
		 * Gets {@link #unloadEventStart}.
		 *
		 * @return {@link #unloadEventStart}
		 */
		public long getUnloadEventStart() {
			return this.unloadEventStart;
		}

		/**
		 * Gets {@link #unloadEventEnd}.
		 *
		 * @return {@link #unloadEventEnd}
		 */
		public long getUnloadEventEnd() {
			return this.unloadEventEnd;
		}

		/**
		 * Gets {@link #redirectStart}.
		 *
		 * @return {@link #redirectStart}
		 */
		public long getRedirectStart() {
			return this.redirectStart;
		}

		/**
		 * Gets {@link #redirectEnd}.
		 *
		 * @return {@link #redirectEnd}
		 */
		public long getRedirectEnd() {
			return this.redirectEnd;
		}

		/**
		 * Gets {@link #fetchStart}.
		 *
		 * @return {@link #fetchStart}
		 */
		public long getFetchStart() {
			return this.fetchStart;
		}

		/**
		 * Gets {@link #domainLookupStart}.
		 *
		 * @return {@link #domainLookupStart}
		 */
		public long getDomainLookupStart() {
			return this.domainLookupStart;
		}

		/**
		 * Gets {@link #domainLookupEnd}.
		 *
		 * @return {@link #domainLookupEnd}
		 */
		public long getDomainLookupEnd() {
			return this.domainLookupEnd;
		}

		/**
		 * Gets {@link #connectStart}.
		 *
		 * @return {@link #connectStart}
		 */
		public long getConnectStart() {
			return this.connectStart;
		}

		/**
		 * Gets {@link #connectEnd}.
		 *
		 * @return {@link #connectEnd}
		 */
		public long getConnectEnd() {
			return this.connectEnd;
		}

		/**
		 * Gets {@link #secureConnectionStart}.
		 *
		 * @return {@link #secureConnectionStart}
		 */
		public long getSecureConnectionStart() {
			return this.secureConnectionStart;
		}

		/**
		 * Gets {@link #requestStart}.
		 *
		 * @return {@link #requestStart}
		 */
		public long getRequestStart() {
			return this.requestStart;
		}

		/**
		 * Gets {@link #responseStart}.
		 *
		 * @return {@link #responseStart}
		 */
		public long getResponseStart() {
			return this.responseStart;
		}

		/**
		 * Gets {@link #responseEnd}.
		 *
		 * @return {@link #responseEnd}
		 */
		public long getResponseEnd() {
			return this.responseEnd;
		}

		/**
		 * Gets {@link #domLoading}.
		 *
		 * @return {@link #domLoading}
		 */
		public long getDomLoading() {
			return this.domLoading;
		}

		/**
		 * Gets {@link #domInteractive}.
		 *
		 * @return {@link #domInteractive}
		 */
		public long getDomInteractive() {
			return this.domInteractive;
		}

		/**
		 * Gets {@link #domContentLoadedEventStart}.
		 *
		 * @return {@link #domContentLoadedEventStart}
		 */
		public long getDomContentLoadedEventStart() {
			return this.domContentLoadedEventStart;
		}

		/**
		 * Gets {@link #domContentLoadedEventEnd}.
		 *
		 * @return {@link #domContentLoadedEventEnd}
		 */
		public long getDomContentLoadedEventEnd() {
			return this.domContentLoadedEventEnd;
		}

		/**
		 * Gets {@link #domComplete}.
		 *
		 * @return {@link #domComplete}
		 */
		public long getDomComplete() {
			return this.domComplete;
		}

		/**
		 * Gets {@link #loadEventStart}.
		 *
		 * @return {@link #loadEventStart}
		 */
		public long getLoadEventStart() {
			return this.loadEventStart;
		}

		/**
		 * Gets {@link #loadEventEnd}.
		 *
		 * @return {@link #loadEventEnd}
		 */
		public long getLoadEventEnd() {
			return this.loadEventEnd;
		}

		/**
		 * Gets {@link #speedIndex}.
		 *
		 * @return {@link #speedIndex}
		 */
		public double getSpeedIndex() {
			return this.speedIndex;
		}

		/**
		 * Gets {@link #firstPaint}.
		 *
		 * @return {@link #firstPaint}
		 */
		public long getFirstPaint() {
			return this.firstPaint;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = (prime * result) + (int) (this.connectEnd ^ (this.connectEnd >>> 32));
			result = (prime * result) + (int) (this.connectStart ^ (this.connectStart >>> 32));
			result = (prime * result) + (int) (this.domComplete ^ (this.domComplete >>> 32));
			result = (prime * result) + (int) (this.domContentLoadedEventEnd ^ (this.domContentLoadedEventEnd >>> 32));
			result = (prime * result) + (int) (this.domContentLoadedEventStart ^ (this.domContentLoadedEventStart >>> 32));
			result = (prime * result) + (int) (this.domInteractive ^ (this.domInteractive >>> 32));
			result = (prime * result) + (int) (this.domLoading ^ (this.domLoading >>> 32));
			result = (prime * result) + (int) (this.domainLookupEnd ^ (this.domainLookupEnd >>> 32));
			result = (prime * result) + (int) (this.domainLookupStart ^ (this.domainLookupStart >>> 32));
			result = (prime * result) + (int) (this.fetchStart ^ (this.fetchStart >>> 32));
			result = (prime * result) + (int) (this.firstPaint ^ (this.firstPaint >>> 32));
			result = (prime * result) + (int) (this.loadEventEnd ^ (this.loadEventEnd >>> 32));
			result = (prime * result) + (int) (this.loadEventStart ^ (this.loadEventStart >>> 32));
			result = (prime * result) + (int) (this.navigationStart ^ (this.navigationStart >>> 32));
			result = (prime * result) + (int) (this.redirectEnd ^ (this.redirectEnd >>> 32));
			result = (prime * result) + (int) (this.redirectStart ^ (this.redirectStart >>> 32));
			result = (prime * result) + (int) (this.requestStart ^ (this.requestStart >>> 32));
			result = (prime * result) + (int) (this.responseEnd ^ (this.responseEnd >>> 32));
			result = (prime * result) + (int) (this.responseStart ^ (this.responseStart >>> 32));
			result = (prime * result) + (int) (this.secureConnectionStart ^ (this.secureConnectionStart >>> 32));
			long temp;
			temp = Double.doubleToLongBits(this.speedIndex);
			result = (prime * result) + (int) (temp ^ (temp >>> 32));
			result = (prime * result) + (int) (this.unloadEventEnd ^ (this.unloadEventEnd >>> 32));
			result = (prime * result) + (int) (this.unloadEventStart ^ (this.unloadEventStart >>> 32));
			return result;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			NavigationTimings other = (NavigationTimings) obj;
			if (this.connectEnd != other.connectEnd) {
				return false;
			}
			if (this.connectStart != other.connectStart) {
				return false;
			}
			if (this.domComplete != other.domComplete) {
				return false;
			}
			if (this.domContentLoadedEventEnd != other.domContentLoadedEventEnd) {
				return false;
			}
			if (this.domContentLoadedEventStart != other.domContentLoadedEventStart) {
				return false;
			}
			if (this.domInteractive != other.domInteractive) {
				return false;
			}
			if (this.domLoading != other.domLoading) {
				return false;
			}
			if (this.domainLookupEnd != other.domainLookupEnd) {
				return false;
			}
			if (this.domainLookupStart != other.domainLookupStart) {
				return false;
			}
			if (this.fetchStart != other.fetchStart) {
				return false;
			}
			if (this.firstPaint != other.firstPaint) {
				return false;
			}
			if (this.loadEventEnd != other.loadEventEnd) {
				return false;
			}
			if (this.loadEventStart != other.loadEventStart) {
				return false;
			}
			if (this.navigationStart != other.navigationStart) {
				return false;
			}
			if (this.redirectEnd != other.redirectEnd) {
				return false;
			}
			if (this.redirectStart != other.redirectStart) {
				return false;
			}
			if (this.requestStart != other.requestStart) {
				return false;
			}
			if (this.responseEnd != other.responseEnd) {
				return false;
			}
			if (this.responseStart != other.responseStart) {
				return false;
			}
			if (this.secureConnectionStart != other.secureConnectionStart) {
				return false;
			}
			if (Double.doubleToLongBits(this.speedIndex) != Double.doubleToLongBits(other.speedIndex)) {
				return false;
			}
			if (this.unloadEventEnd != other.unloadEventEnd) {
				return false;
			}
			if (this.unloadEventStart != other.unloadEventStart) { // NOPMD
				return false;
			}
			return true;
		}


	}

	/**
	 * Gets {@link #navigationTimings}.
	 *
	 * @return {@link #navigationTimings}
	 */
	public NavigationTimings getNavigationTimings() {
		return this.navigationTimings;
	}

	/**
	 * Gets {@link #resourceCount}.
	 * 
	 * @return {@link #resourceCount}
	 */
	public int getResourceCount() {
		return this.resourceCount;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((this.navigationTimings == null) ? 0 : this.navigationTimings.hashCode());
		result = (prime * result) + this.resourceCount;
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PageLoadRequest other = (PageLoadRequest) obj;
		if (this.navigationTimings == null) {
			if (other.navigationTimings != null) {
				return false;
			}
		} else if (!this.navigationTimings.equals(other.navigationTimings)) {
			return false;
		}
		if (this.resourceCount != other.resourceCount) {
			return false;
		}
		return true;
	}


}
