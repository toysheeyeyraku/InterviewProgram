package org.kovtun.sessionmanager;

import org.kovtun.session.Session;

/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
public interface SessionManager {
	public Session getSession(String sessionName);

	public Session addSesstion(String sessionName);

	public Session deleteSession(String sessionName);

	public boolean containsSession(String sessionName);
}
