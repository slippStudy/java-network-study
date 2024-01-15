package org.example.len.ch16_advenced.event;

import java.util.Map;

public class Job {
	
	private int eventType;
	private Map session = null;
	
	public Job() {}
	
	public Job(int eventType, Map session) {
		this.eventType = eventType;
		this.session = session;
	}

	/**
	 * @return Returns the session.
	 */
	public Map getSession() {
		return session;
	}
	/**
	 * @param session The session to set.
	 */
	public void setSession(Map session) {
		this.session = session;
	}
	
	/**
	 * @return Returns the eventType.
	 */
	public int getEventType() {
		return eventType;
	}
	/**
	 * @param eventType The eventType to set.
	 */
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
}
