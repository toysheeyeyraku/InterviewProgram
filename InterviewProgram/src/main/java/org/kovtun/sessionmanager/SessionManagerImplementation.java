package org.kovtun.sessionmanager;

import java.util.HashMap;

import org.kovtun.sessionInterface.Session;
import org.kovtun.sessionInterface.SessionImplementation;

public class SessionManagerImplementation implements SessionManager {
	public HashMap<String, Session> userSesstion = new HashMap<String, Session>();
	@Override
	public Session getSession(String sessionName) {
		return userSesstion.get(sessionName);
		
	}

	@Override
	public Session addSesstion(String sessionName) {
		Session ans =new SessionImplementation(sessionName);
		userSesstion.put(sessionName,ans );
		return ans;
		
	}

	@Override
	public Session deleteSession(String sessionName) {
		Session ans=userSesstion.get(sessionName);
		userSesstion.remove(sessionName);
		return ans;
	}

}
