package org.kovtun.questionsystem;

import lombok.Data;
/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
@Data
public class Question {
	private String question;
	private QuestionSystemVerdict verdict;

}
