package rocks.inspectit.server.service.rest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.diagnoseit.standalone.Launcher;
import org.diagnoseit.standalone.Launcher.RulePackage;
import org.spec.research.open.xtrace.adapters.inspectit.source.InspectITTraceConverter;
import org.spec.research.open.xtrace.api.core.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import io.gsonfire.GsonFireBuilder;
import io.gsonfire.TypeSelector;
import rocks.inspectit.server.cache.IBuffer;
import rocks.inspectit.server.cache.impl.BufferElement;
import rocks.inspectit.server.service.rest.error.JsonError;
import rocks.inspectit.server.util.MobileTraceStorage;
import rocks.inspectit.server.util.PlatformIdentCache;
import rocks.inspectit.shared.all.cmr.model.PlatformIdent;
import rocks.inspectit.shared.all.communication.DefaultData;
import rocks.inspectit.shared.all.communication.data.InvocationSequenceData;
import rocks.inspectit.shared.all.communication.data.eum.AbstractBeacon;
import rocks.inspectit.shared.all.communication.data.eum.mobile.MobileBeacon;
import rocks.inspectit.shared.all.communication.data.eum.mobile.MobileIOSElement;
import rocks.inspectit.shared.all.communication.data.eum.mobile.MobileMeasurement;
import rocks.inspectit.shared.all.communication.data.eum.mobile.MobilePeriodicMeasurement;
import rocks.inspectit.shared.all.communication.data.eum.mobile.MobileUsecaseElement;
import rocks.inspectit.shared.all.communication.data.eum.mobile.RemoteCallMeasurement;
import rocks.inspectit.shared.all.communication.data.eum.mobile.RemoteCallMeasurementContainer;
import rocks.inspectit.shared.all.tracing.data.SpanIdent;

/**
 * Restful service provider for detail {@link MobileBeacon} information.
 *
 * @author Tobias Angerstein, Alper Hidiroglu, Manuel Palenga
 *
 */
@Controller
@RequestMapping(value = "/mobile/newinvocation")
public class AgentRestfulService {

	@Autowired
	private PlatformIdentCache platformCache;

	@Autowired
	private MobileTraceStorage mobileTraceStorage;

	@Autowired
	private IBuffer<InvocationSequenceData> buffer;

	/**
	 * Handling of all the exceptions happening in this controller.
	 *
	 * @param exception
	 *            Exception being thrown
	 * @return {@link ModelAndView}
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception exception) {
		return new JsonError(exception).asModelAndView();
	}

	/**
	 * TODO
	 */
	@RequestMapping(method = POST, value = "")
	@ResponseBody
	public void addNewMobileBeacon(@RequestBody String json) {

		Gson gson = getGson();
		MobileBeacon mobileBeacon = gson.fromJson(json, MobileBeacon.class);

		/*
		 * TODO: List is only for testing
		 */
		List<InvocationSequenceData> listSequenceDatas = new ArrayList<InvocationSequenceData>();

		for (DefaultData data : mobileBeacon.getData()) {
			if (data instanceof MobileIOSElement) {
				MobileIOSElement iOSElement = (MobileIOSElement) data;

				// MobileUsecaseElement usecaseElement = new
				// MobileUsecaseElement();
				MobileUsecaseElement usecaseElement = (MobileUsecaseElement) iOSElement;

				// Set data which are not in the MobileIOSElement class
				usecaseElement.setDeviceID(mobileBeacon.getDeviceID());
				List<MobilePeriodicMeasurement> relevantMeasurements = mobileBeacon.getMeasurements();

				// Remove irrelevant measurements, which are not in the defined
				// timeslot
				for (MobilePeriodicMeasurement measurement : relevantMeasurements) {
					if (!(measurement.getTimestamp() <= iOSElement.getStopMeasurement().getTimestamp())
							&& !(measurement.getTimestamp() >= iOSElement.getStartMeasurement().getTimestamp())) {
						relevantMeasurements.remove(measurement);
					}
				}
				usecaseElement.setMeasurements(mobileBeacon.getMeasurements());
				usecaseElement.getMeasurements().add(0, iOSElement.getStartMeasurement());
				usecaseElement.getMeasurements().add(iOSElement.getStopMeasurement());
				mobileTraceStorage.push(usecaseElement);
			}
			// TODO Convertion
			List<PlatformIdent> platformIdentList = new ArrayList<PlatformIdent>();
			Collection<PlatformIdent> platformIdents = platformCache.getCleanPlatformIdents();
			for (PlatformIdent platIdent : platformIdents) {
				platformIdentList.add(platIdent);
			}
			PlatformIdent ident = new PlatformIdent();
			ident.setId(-1L);
			platformIdentList.add(ident);
			// TODO
			for (InvocationSequenceData invocationSequenceData : listSequenceDatas) {
				InspectITTraceConverter converter = new InspectITTraceConverter();
				Trace trace = converter.convertTraces(invocationSequenceData, platformIdentList);
				// Trace trace = TraceCreator.getTestTrace1();
				try {
					Launcher.startLauncher(trace, RulePackage.MobilePackage);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private Gson getGson() {
		GsonFireBuilder builder = new GsonFireBuilder().registerTypeSelector(DefaultData.class,
				new TypeSelector<DefaultData>() {
					@Override
					public Class<? extends DefaultData> getClassForElement(JsonElement readElement) {
						String type = readElement.getAsJsonObject().get("type").getAsString();
						if (type.equals("IOSMeasuredUseCase")) {
							return MobileIOSElement.class;
						} else {
							/*
							 * returning null will trigger Gson's default
							 * behavior
							 */
							return null;
						}
					}
				});
		builder.registerTypeSelector(MobileMeasurement.class, new TypeSelector<MobileMeasurement>() {

			@Override
			public Class<? extends MobileMeasurement> getClassForElement(JsonElement readElement) {
				String type = readElement.getAsJsonObject().get("type").getAsString();

				if (type.equals("IOSMeasurement")) {
					return MobilePeriodicMeasurement.class;
				} else {
					/*
					 * returning null will trigger Gson's default behavior
					 */
					return null;
				}
			}
		});
		Gson gson = builder.createGson();
		return gson;
	}

	/**
	 * Test method
	 */
	@RequestMapping(method = POST, value = "/defaultJSON")
	@ResponseBody
	public AbstractBeacon getNewMobileBeacon() {
		MobileBeacon beacon = new MobileBeacon();
		MobilePeriodicMeasurement measurement1 = new MobilePeriodicMeasurement(2423234524L, 12, 81.236218F,
				83.24683246F);
		MobilePeriodicMeasurement measurement2 = new MobilePeriodicMeasurement(2423234525L, 23, 96.99F, 10.500000F);
		MobilePeriodicMeasurement measurement3 = new MobilePeriodicMeasurement(2423234726L, 34, 81.236218F,
				70.24683246F);
		MobilePeriodicMeasurement measurement4 = new MobilePeriodicMeasurement(2423234727L, 45, 80.99F, 11.500000F);
		MobilePeriodicMeasurement measurement5 = new MobilePeriodicMeasurement(2423244828L, 56, 83.236218F,
				82.24683246F);
		MobilePeriodicMeasurement measurement6 = new MobilePeriodicMeasurement(2423244829L, 67, 97.99F, 11.400000F);

		List<RemoteCallMeasurementContainer> listRemoteCallContainer = new ArrayList<RemoteCallMeasurementContainer>();
		RemoteCallMeasurement remoteMeasurement = new RemoteCallMeasurement(2423234624L, 1,
				"On my way", "4G", "zero", 200, false, 53.4234523, 23.45234532);
		RemoteCallMeasurement remoteMeasurement1 = new RemoteCallMeasurement(2423234636L, 2,
				"On my way", "4G", "zero", 200, false, 53.4234523, 23.45234532);
		RemoteCallMeasurementContainer container = new RemoteCallMeasurementContainer(remoteMeasurement,
				remoteMeasurement1);
		listRemoteCallContainer.add(container);

		MobileIOSElement mobileIOSElement = new MobileIOSElement("Login", 123456789023L, 3456345634L,
				listRemoteCallContainer, measurement1, measurement2);
		MobileIOSElement mobileIOSElement2 = new MobileIOSElement("Create Group", 123456789022L, 123434564L,
				listRemoteCallContainer, measurement3, measurement4);

		beacon.getData().add(mobileIOSElement);
		beacon.getData().add(mobileIOSElement2);
		beacon.getMeasurements().add(measurement5);
		beacon.getMeasurements().add(measurement6);
		return beacon;
	}

	/**
	 * Test method
	 */
	@RequestMapping(method = POST, value = "/createRemoteCall")
	@ResponseBody
	public void createRemoteCall() {
		InvocationSequenceData remoteCall = new InvocationSequenceData();
		remoteCall.setPlatformIdent(-1);
		remoteCall.setTimeStamp(new Timestamp(1000000000 + (new Random()).nextInt(1000)));
		remoteCall.setId(42);
		// RemoteCall ID which will be set by the header
		remoteCall.setSpanIdent(new SpanIdent(1, 0, 0));

		InvocationSequenceData remoteCall2 = new InvocationSequenceData();
		remoteCall2.setPlatformIdent(-1);
		remoteCall2.setTimeStamp(new Timestamp(1000000000 + (new Random()).nextInt(1000)));
		remoteCall2.setId(43);
		remoteCall2.setSpanIdent(new SpanIdent(2, 0, 0));
		buffer.put(new BufferElement<InvocationSequenceData>(remoteCall));
		buffer.put(new BufferElement<InvocationSequenceData>(remoteCall2));

	}

	/**
	 * Header information for swagger requests.
	 *
	 * @param response
	 *            Response information
	 */
	@ModelAttribute
	public void setVaryResponseHeader(HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
	}
}
