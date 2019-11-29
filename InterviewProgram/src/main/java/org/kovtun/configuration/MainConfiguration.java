package org.kovtun.configuration;

import org.kovtun.questionsystem.QuestionManager;
import org.kovtun.questionsystem.QuestionManagerImplementation;
import org.kovtun.sessionmanager.SessionManager;
import org.kovtun.sessionmanager.SessionManagerImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
@Configuration
public class MainConfiguration {
	@Bean
	public SessionManager getSesstionManager() {
		return new SessionManagerImplementation();
	}
	@Bean
	@Scope("prototype")
	public QuestionManager getQuestionManager() {
		return new QuestionManagerImplementation();
	}
}
