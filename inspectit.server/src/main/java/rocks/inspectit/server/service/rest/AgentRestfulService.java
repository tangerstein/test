package rocks.inspectit.server.service.rest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

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
import rocks.inspectit.server.service.rest.error.JsonError;
import rocks.inspectit.shared.all.communication.data.InvocationSequenceData;
import rocks.inspectit.shared.all.communication.data.eum.AbstractBacon;
import rocks.inspectit.shared.all.communication.data.eum.AbstractEUMElement;
import rocks.inspectit.shared.all.communication.data.eum.MobileBacon;
import rocks.inspectit.shared.all.communication.data.eum.MobileIOSElement;
import rocks.inspectit.shared.all.communication.data.eum.MobileIOSMeasurement;

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
	public MobileBacon getNewMobileBeacon(@RequestBody String JSON) {
		Gson gson = getGson();
		MobileBacon mobileBacon = gson.fromJson(JSON, MobileBacon.class);
		return mobileBacon;
	}

	private Gson getGson() {
		GsonFireBuilder builder = new GsonFireBuilder().registerTypeSelector(AbstractEUMElement.class,
				new TypeSelector<AbstractEUMElement>() {
			@Override
					public Class<? extends AbstractEUMElement> getClassForElement(JsonElement readElement) {
				String type = readElement.getAsJsonObject().get("type").getAsString();
						if (type.equals("IOSMeasuredUseCase")) {
							return MobileIOSElement.class;
				} else {
					return null; // returning null will trigger Gson's default
									// behavior
				}
			}
		});
		Gson gson = builder.createGson();
		return gson;
	}

	/**
	 * TODO
	 */
	@RequestMapping(method = POST, value = "/defaultJSON")
	@ResponseBody
	public AbstractBacon getNewMobileBeacon() {
		AbstractBacon bacon = new MobileBacon();
		MobileIOSMeasurement measurement = new MobileIOSMeasurement(23423452345L, 234523453245L, 2423234524L);
		MobileIOSElement mobileIOSElement = new MobileIOSElement("Login", "sasdgfs76aedt", new ArrayList<MobileIOSMeasurement>(Arrays.asList(new MobileIOSMeasurement[]{measurement})));
		bacon.getData().add(mobileIOSElement);
		return bacon;
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
