package rocks.inspectit.agent.java.eum.data;

import java.util.Random;

import org.springframework.stereotype.Component;

/**
 * @author Jonas Kunz
 *
 */
@Component
public class IdGenerator {

	private Random rnd = new Random();

	public long generateSessionID() {
		return rnd.nextLong();
	}

	public long generateTabID() {
		return rnd.nextLong();
	}

}
