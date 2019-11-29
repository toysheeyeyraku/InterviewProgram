package org.kovtun.questionsystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.kovtun.utils.ParserFile;
/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
public class QuestionManagerImplementation implements QuestionManager {
	private ParserFile file;
	private AnsweredSystem answeredSystem;
	private ArrayList<String> questionsWithComments = new ArrayList<String>();
	private ArrayList<String> chosedTypes;
	private String lastType;
	private String lastQuestion;
	private boolean commented = true;
	

	public QuestionManagerImplementation() {
		answeredSystem = new AnsweredSystem();
	}

	@Override
	public void setFile(ParserFile file) {

		this.file = file;
		answeredSystem.restart();
	}

	@Override
	public Question getQuestion(String type) {

		if (!commented) {
			Question question = new Question();
			question.setVerdict(QuestionSystemVerdict.NO_COMMENTED);
			return question;
		}
		if (file == null) {
			Question question = new Question();
			question.setVerdict(QuestionSystemVerdict.NO_FILE_LOADED);
			lastQuestion = null;
			return question;
		}
		if (!file.getTypeQuestions().containsKey(type)) {
			Question question = new Question();
			question.setVerdict(QuestionSystemVerdict.INVALID_TYPE);
			lastQuestion = null;
			return question;
		}

		if (file.getTypeQuestions().get(type).size() == answeredSystem.getAnsweredQuestionsSizeByType(type)) {
			Question question = new Question();
			question.setVerdict(QuestionSystemVerdict.QUESTIONS_RUN_OUT);
			lastQuestion = null;
			return question;
		}

		return getRandomQuestionFromType(type);

	}

	@Override
	public void storeComment(String comment) {

		if (lastQuestion != null) {
			questionsWithComments
					.add("Type:" + lastType + " " + "Question:" + lastQuestion + " " + "Comment:" + comment);
			commented = true;
		}
	}

	@Override
	public Question getLastQuestion() {
		Question ans = new Question();
		if (lastQuestion != null) {
			ans.setVerdict(QuestionSystemVerdict.OK);
			ans.setQuestion(lastQuestion);
		} else {
			ans.setVerdict(QuestionSystemVerdict.NO_COMMENTED);
		}
		return ans;
	}

	@Override
	public ArrayList<String> getQuestionWithComments() {

		return questionsWithComments;
	}

	@Override
	public Set<String> getTypes() {

		return file.getTypeQuestions().keySet();
	}
    /**This method is called when all checks passed well and this method get random question*/
	private Question getRandomQuestionFromType(String type) {
		commented = false;
		Question question = new Question();
		Random rand = new Random();
		int ans = -1;
		while (true) {
			int randomNum = rand.nextInt((file.getTypeQuestions().get(type).size() - 1) + 1);
			if (!answeredSystem.containsQuestion(type, randomNum)) {
				ans = randomNum;
				answeredSystem.addQuestion(type, ans);
				break;
			}
		}
		lastType = type;
		lastQuestion = file.getTypeQuestions().get(type).get(ans);
		question.setVerdict(QuestionSystemVerdict.OK);
		question.setQuestion(lastQuestion);
		return question;
	}

	@Override
	public void setChosenTypes(ArrayList<String> types) {
		chosedTypes = types;

	}

	@Override
	public ArrayList<String> getChosenTypes() {

		return chosedTypes;
	}

}
