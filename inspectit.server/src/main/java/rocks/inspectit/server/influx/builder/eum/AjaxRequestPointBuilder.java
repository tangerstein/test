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
import rocks.inspectit.shared.all.communication.data.eum.AjaxRequest;
import rocks.inspectit.shared.all.communication.data.eum.PageLoadRequest;
import rocks.inspectit.shared.all.communication.data.eum.UserSessionInfo;

/**
 * @author Jonas Kunz
 *
 */
@Component
public class AjaxRequestPointBuilder extends AbstractEUMPointBuilder<AjaxRequest> {

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
	public Collection<Builder> build(UserSessionInfo sessionInfo, PageLoadRequest plr, AjaxRequest ajax) {

		Builder builder = Point.measurement(Series.EumAjax.NAME);
		PlatformIdent platformIdent = cachedDataService.getPlatformIdentForId(ajax.getPlatformIdent());
		builder.tag(Series.TAG_AGENT_ID, String.valueOf(ajax.getPlatformIdent()));
		if (null != platformIdent) {
			builder.tag(Series.TAG_AGENT_NAME, platformIdent.getAgentName());
		}
		if (sessionInfo != null) {
			builder.tag(Series.EUMBasicRequestSeries.TAG_BROWSER, sessionInfo.getBrowser());
			builder.tag(Series.EUMBasicRequestSeries.TAG_DEVICE, sessionInfo.getDevice());
			builder.tag(Series.EUMBasicRequestSeries.TAG_LANGUAGE, sessionInfo.getLanguage());
		}

		builder.tag(Series.EUMBasicRequestSeries.TAG_URL, ajax.getUrl());

		if (plr != null) {
			builder.tag(Series.EumAjax.TAG_BASE_URL, plr.getUrl());
		}

		builder.time(ajax.getEnterTimestamp(), TimeUnit.MILLISECONDS);
		builder.addField(Series.EumAjax.FIELD_DURATION, ajax.getExitTimestamp() - ajax.getEnterTimestamp());
		builder.addField(Series.EumAjax.FIELD_METHOD, ajax.getMethod());
		builder.addField(Series.EumAjax.FIELD_STATUS, ajax.getStatus());

		return Collections.singleton(builder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Class<? extends AbstractEUMElement>> getSupportedTypes() {
		return Collections.<Class<? extends AbstractEUMElement>> singleton(AjaxRequest.class);
	}

}
