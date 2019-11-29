package org.kovtun.session;

import org.kovtun.questionsystem.QuestionManager;
/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
public interface Session {
	public String getSessionName();

	public QuestionManager getQuestionManager();

}
