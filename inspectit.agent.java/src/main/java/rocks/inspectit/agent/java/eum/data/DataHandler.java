/**
 *
 */
package rocks.inspectit.agent.java.eum.data;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rocks.inspectit.shared.all.communication.data.eum.Beacon;
import rocks.inspectit.shared.all.spring.logger.Log;


/**
 * Class for processing beacons which the javascript agent sends back to the agent.
 *
 * @author David Monschein
 */

@Component
public class DataHandler implements IDataHandler {

	@Log
	private Logger log;

	@Autowired
	private IdGenerator idGenerator;

	private ObjectMapper jsonMapper = new ObjectMapper();

	private JsonNodeFactory nodeFactory = jsonMapper.getNodeFactory();

	@Override
	public String insertBeacon(String data) {
		if (data == null) {
			return "{}";
		}

		try {
			Beacon beacon = jsonMapper.readValue(data, Beacon.class);
			ObjectNode response = nodeFactory.objectNode();

			long sessionID = beacon.getSessionID();
			long tabID = beacon.getTabID();

			if (sessionID == Beacon.REQUEST_NEW_SESSION_ID_MARKER) {
				sessionID = idGenerator.generateSessionID();
				response.put("sessionID", sessionID);
			}
			if (beacon.getTabID() == Beacon.REQUEST_NEW_TAB_ID_MARKER) {
				tabID = idGenerator.generateTabID();
				response.put("tabID", tabID);
			}
			// even needed if the IDs were all known, as this also assigns the ids to all stored
			// AbstractEUMElements.
			beacon.assignIDs(sessionID, tabID);
			return jsonMapper.writeValueAsString(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error decoding beacon!", e);
			return "{}";
		}
	}


}
