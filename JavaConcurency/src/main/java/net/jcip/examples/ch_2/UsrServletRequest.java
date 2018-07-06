package net.jcip.examples.ch_2;

/**
 * Created by asorokin on 03.07.2018.
 */
public class UsrServletRequest
{
	private Object value;

	public UsrServletRequest(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
}
