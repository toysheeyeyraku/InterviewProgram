package org.kovtun.questionManagerImplementation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import org.kovtun.sessionInterface.Question;
import org.kovtun.sessionInterface.QuestionManager;
import org.kovtun.sessionInterface.QuestionSystemVerdict;
import org.kovtun.utils.ParserFile;

public class QuestionManagerImplementation implements QuestionManager {
	private ParserFile file ;
	private AnsweredSystem answeredSystem;
	private HashMap<String, String> questionComment = new HashMap<String, String>();
	private String lastType;
	private String lastQuestion;
	
	@Override
	public void setFile(ParserFile file) {
		
		this.file=file;
		answeredSystem.restart();
	}

	@Override
	public Question getQuestion(String type) {
		
		if (file == null) {
			Question question =new Question();
			question.setVerdict(QuestionSystemVerdict.NO_FILE_LOADED);
			return question;
		}
		if (!file.typeQuestions.containsKey(type)) {
			Question question =new Question();
			question.setVerdict(QuestionSystemVerdict.INVALID_TYPE);
			return question;
		}
		

		if (file.typeQuestions.get(type).size() == answeredSystem.getAnsweredQuestionsSizeByType(type)) {
			Question question =new Question();
			question.setVerdict(QuestionSystemVerdict.QUESTIONS_RUN_OUT);
			return question;
		}
		
		return okQuestion(type);
		
		
	}
	
	private Question okQuestion(String type) {
		Question question =new Question();
		Random rand = new Random();
		int ans = -1;
		while (true) {
			int randomNum = rand.nextInt((file.typeQuestions.get(type).size() - 1) + 1);
			if (!answeredSystem.containsQuestion(type, randomNum)) {
				ans = randomNum;
				answeredSystem.addQuestion(type, ans);
				break;
			}
		}
		lastType = type;
		lastQuestion = file.typeQuestions.get(type).get(ans);
		question.setVerdict(QuestionSystemVerdict.OK);
		question.setQuestion(lastQuestion);
		return question;
	}
}
