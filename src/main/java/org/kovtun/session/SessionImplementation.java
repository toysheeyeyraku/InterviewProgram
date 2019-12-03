package org.kovtun.session;

import org.kovtun.questionsystem.QuestionManager;
import org.kovtun.questionsystem.QuestionManagerImplementation;
import org.springframework.stereotype.Component;
/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
@Component
public class SessionImplementation implements Session {
	private String sesstionName;

	private QuestionManager questionManager;

	public SessionImplementation(String sessionName) {
		this.sesstionName = sessionName;
		questionManager = new QuestionManagerImplementation();

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
