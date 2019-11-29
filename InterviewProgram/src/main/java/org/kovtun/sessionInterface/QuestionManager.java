package org.kovtun.sessionInterface;

import org.kovtun.utils.ParserFile;

public interface QuestionManager {
	public void setFile(ParserFile file);
	public Question getQuestion(String type);
	
}
