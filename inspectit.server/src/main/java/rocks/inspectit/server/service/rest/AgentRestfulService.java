package rocks.inspectit.server.service.rest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import rocks.inspectit.server.service.rest.error.JsonError;
import rocks.inspectit.shared.all.communication.data.InvocationSequenceData;

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
	public String insertNewMeasurement(@RequestBody String JSON) {
		JsonParser parser = new JsonParser();
		JsonObject jsonObject = parser.parse(JSON).getAsJsonObject();
		long startTimeStamp = jsonObject.get("startTimestamp").getAsLong();
		long endTimeStamp = jsonObject.get("endTimestamp").getAsLong();
		long duration = endTimeStamp - startTimeStamp;
		return "Response time= " + duration;
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
