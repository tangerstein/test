package rocks.inspectit.server.service.rest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import io.gsonfire.GsonFireBuilder;
import io.gsonfire.TypeSelector;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import rocks.inspectit.server.service.rest.error.JsonError;
import rocks.inspectit.shared.all.communication.DefaultData;
import rocks.inspectit.shared.all.communication.data.InvocationSequenceData;
import rocks.inspectit.shared.all.communication.data.MobileClientData;
import rocks.inspectit.shared.all.communication.data.eum.AbstractBeacon;
import rocks.inspectit.shared.all.communication.data.eum.mobile.MobileBeacon;
import rocks.inspectit.shared.all.communication.data.eum.mobile.MobileIOSElement;
import rocks.inspectit.shared.all.communication.data.eum.mobile.MobileIOSMeasurement;
import rocks.inspectit.shared.all.communication.data.eum.mobile.MobileMeasurement;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * Restful service provider for detail {@link InvocationSequenceData}
 * information.
 *
 * @author Mario Mann
 *
 */
@Controller
@RequestMapping(value = "/mobile/newinvocation")
public class AgentRestfulService {

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
	public List<InvocationSequenceData> addNewMobileBeacon(@RequestBody String json) {
		Gson gson = getGson();
		MobileBeacon mobileBeacon = gson.fromJson(json, MobileBeacon.class);

		/*
		 * TODO: List is only for testing
		 */
		List<InvocationSequenceData> listSequenceDatas = new ArrayList<InvocationSequenceData>();
		
		for(DefaultData data : mobileBeacon.getData()){
			if(data instanceof MobileIOSElement){
				MobileIOSElement iOSElement = (MobileIOSElement) data;
				
				InvocationSequenceData rootSequenceData = new InvocationSequenceData();
				listSequenceDatas.add(rootSequenceData);
				
				// Set mobileData on the root node of the sequence.
				MobileClientData rootMobileClientData = new MobileClientData();
				rootMobileClientData.setUseCaseDescription(iOSElement.getUsecaseDescription());
				rootMobileClientData.setUseCaseID(iOSElement.getUsecaseID());
				rootSequenceData.setMobileData(rootMobileClientData);
				
				// Get and set mobile measurement points
				for (MobileMeasurement mobileMeasurement : iOSElement.getMeasurements()) {
					MobileIOSMeasurement iOSMeasurement = (MobileIOSMeasurement) mobileMeasurement;
								
					InvocationSequenceData nestedSequenceData = new InvocationSequenceData();
					MobileClientData mobileClientData = new MobileClientData();
					
					// Set timestamp of the measurement
					Timestamp timestamp = new Timestamp(iOSMeasurement.getTimestamp());
					mobileClientData.setTimeStamp(timestamp);
					
					// Set mobile measurement data
					mobileClientData.setPower(iOSMeasurement.getPower());
					mobileClientData.setCpu(iOSMeasurement.getCpu());
					mobileClientData.setMemory(iOSMeasurement.getMemory());
					mobileClientData.setLatitude(iOSMeasurement.getLatitude());
					mobileClientData.setLongitude(iOSMeasurement.getLongitude());
					mobileClientData.setNetworkConnection(iOSMeasurement.getNetworkConnection());
					
					// Set use case data
					mobileClientData.setUseCaseDescription(iOSElement.getUsecaseDescription());
					mobileClientData.setUseCaseID(iOSElement.getUsecaseID());
					
					nestedSequenceData.setMobileData(mobileClientData);
					rootSequenceData.getNestedSequences().add(nestedSequenceData);
				}
				
			}
		}
		
		return listSequenceDatas;
	}

	private Gson getGson() {
		GsonFireBuilder builder = new GsonFireBuilder().registerTypeSelector(
				DefaultData.class, new TypeSelector<DefaultData>() {
					@Override
					public Class<? extends DefaultData> getClassForElement(
							JsonElement readElement) {
						String type = readElement.getAsJsonObject().get("type")
								.getAsString();
						if (type.equals("IOSMeasuredUseCase")) {
							return MobileIOSElement.class;
						} else {
							return null; // returning null will trigger Gson's
											// default
											// behavior
						}
					}
				});
		builder.registerTypeSelector(MobileMeasurement.class,
				new TypeSelector<MobileMeasurement>() {

					@Override
					public Class<? extends MobileMeasurement> getClassForElement(
							JsonElement readElement) {
						String type = readElement.getAsJsonObject().get("type")
								.getAsString();

						if (type.equals("IOSMeasurement")) {
							return MobileIOSMeasurement.class;
						} else {
							return null; // returning null will trigger Gson's
											// default
							// behavior
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
		AbstractBeacon beacon = new MobileBeacon();
		MobileIOSMeasurement measurement = new MobileIOSMeasurement(
				53.4234523F, 23.45234532F, 2423234524L, 43, "4G",
				81.236218F, 8324683246F);
		MobileIOSMeasurement measurement2 = new MobileIOSMeasurement(
				27.34567890F, 18.1234245F, 2423234524L, 87, "3G",
				99.99F, 10400000F);

		MobileIOSElement mobileIOSElement = new MobileIOSElement("Login",
				"1234-5678-90AB", new ArrayList<MobileMeasurement>(Arrays.asList(new MobileMeasurement[] { measurement})), 3456345634L);
		
		MobileIOSElement mobileIOSElement2 = new MobileIOSElement("Create Group",
				"XYZ4-1234-90AB", new ArrayList<MobileMeasurement>(Arrays.asList(new MobileMeasurement[] { measurement, measurement2})), 123434564L);
		beacon.getData().add(mobileIOSElement);
		beacon.getData().add(mobileIOSElement2);
		return beacon;
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
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept");
	}
}
