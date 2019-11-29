package org.kovtun.sessionmanager;

import org.kovtun.sessionInterface.Session;

public interface SessionManager {
	public Session getSession(String sessionName);
	public Session addSesstion(String sessionName);
	public Session deleteSession(String sessionName);
}
