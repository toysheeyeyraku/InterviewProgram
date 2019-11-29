package org.kovtun.sessionInterface;

import org.kovtun.questionManagerImplementation.QuestionManagerImplementation;

public class SessionImplementation implements Session {
	private String sesstionName;
	private QuestionManager questionManager;
	public  SessionImplementation(String sessionName) {
		this.sesstionName=sessionName;
		questionManager=new QuestionManagerImplementation();
	}
	@Override
	public String getSessionName() {
		
		return sesstionName;
	}

	@Override
	public QuestionManager getQuestionManager() {
		
		return questionManager;
	}

}
