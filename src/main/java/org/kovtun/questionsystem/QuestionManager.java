package org.kovtun.questionsystem;

import java.util.ArrayList;
import java.util.Set;

import org.kovtun.utils.ParserFile;
/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
public interface QuestionManager {
	public void setFile(ParserFile file);

	public Question getQuestion(String type);

	public Question getLastQuestion();

	public ArrayList<String> getQuestionWithComments();

	public void storeComment(String comment);

	public Set<String> getTypes();

	public void setChosenTypes(ArrayList<String> types);

	public ArrayList<String> getChosenTypes();

}
