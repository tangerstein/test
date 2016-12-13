package rocks.inspectit.server.influx.builder.eum;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rocks.inspectit.server.influx.constants.Series;
import rocks.inspectit.shared.all.cmr.model.PlatformIdent;
import rocks.inspectit.shared.all.cmr.service.ICachedDataService;
import rocks.inspectit.shared.all.communication.data.eum.AbstractEUMElement;
import rocks.inspectit.shared.all.communication.data.eum.PageLoadRequest;
import rocks.inspectit.shared.all.communication.data.eum.ResourceLoadRequest;
import rocks.inspectit.shared.all.communication.data.eum.UserSessionInfo;

/**
 * @author Jonas Kunz
 *
 */
@Component
public class ResourceLoadRequestPointBuilder extends AbstractEUMPointBuilder<ResourceLoadRequest> {

	/**
	 * {@link ICachedDataService} for resolving all needed names.
	 */
	@Autowired
	protected ICachedDataService cachedDataService;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean requiresSessionMetaInfo() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean requiresPageLoadRequest() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Builder> build(UserSessionInfo sessionInfo, PageLoadRequest plr, ResourceLoadRequest res) {

		Builder builder = Point.measurement(Series.EumResourceLoad.NAME);
		PlatformIdent platformIdent = cachedDataService.getPlatformIdentForId(res.getPlatformIdent());
		builder.tag(Series.TAG_AGENT_ID, String.valueOf(res.getPlatformIdent()));
		if (null != platformIdent) {
			builder.tag(Series.TAG_AGENT_NAME, platformIdent.getAgentName());
		}
		if (sessionInfo != null) {
			builder.tag(Series.EUMBasicRequestSeries.TAG_BROWSER, sessionInfo.getBrowser());
			builder.tag(Series.EUMBasicRequestSeries.TAG_DEVICE, sessionInfo.getDevice());
			builder.tag(Series.EUMBasicRequestSeries.TAG_LANGUAGE, sessionInfo.getLanguage());
		}

		builder.tag(Series.EUMBasicRequestSeries.TAG_URL, res.getUrl());

		if (plr != null) {
			builder.tag(Series.EumResourceLoad.TAG_INITIATOR_URL, plr.getUrl());
		}

		builder.time(res.getEnterTimestamp(), TimeUnit.MILLISECONDS);
		builder.addField(Series.EumAjax.FIELD_DURATION, res.getExitTimestamp() - res.getEnterTimestamp());
		builder.tag(Series.EumResourceLoad.TAG_INITIATOR_TYPE, res.getInitiatorType());
		builder.addField(Series.EumResourceLoad.FIELD_TRANSFER_SIZE, res.getTransferSize());

		return Collections.singleton(builder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Class<? extends AbstractEUMElement>> getSupportedTypes() {
		return Collections.<Class<? extends AbstractEUMElement>> singleton(ResourceLoadRequest.class);
	}

}
