package net.jcip.examples.ch_16;

import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

/**
 * SafeStates
 * <p/>
 * Initialization safety for immutable objects
 *
 * @author Brian Goetz and Tim Peierls
 */
@ThreadSafe
public class SafeStates {
	private final Map<String, String> states;

	public SafeStates() {
		states = new HashMap<String, String>();
		states.put("alaska", "AK");
		states.put("alabama", "AL");
	    /*...*/
		states.put("wyoming", "WY");
	}

	public String getAbbreviation(String s) {
		return states.get(s);
	}
}
