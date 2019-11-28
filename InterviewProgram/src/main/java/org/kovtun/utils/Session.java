package org.kovtun.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import lombok.Data;

@Data
public class Session {
	private HashMap<String, HashSet<Integer>> answered = new HashMap<String, HashSet<Integer>>();
	private String userName;
	private ParserFile file;
	private HashMap<String, String> questionComment = new HashMap<String, String>();
	private String lastType;
	private String lastQuestion;
	private boolean isStored;
	public Session(ParserFile file, String userName) {
		this.file = file;
		this.userName = userName;
		isStored=true;
	}

	

	public String getQuestion(String type) {
		System.out.println(type);
		if (file == null) {
			return null;
		}
		if (!file.typeQuestions.containsKey(type)) {
			return null;
		}
		if (!answered.containsKey(type)) {
			answered.put(type, new HashSet<Integer>());
		}

		if (file.typeQuestions.get(type).size() == answered.get(type).size()) {
			lastQuestion = "null";
			return null;
		}
		Random rand = new Random();
		int ans = -1;
		while (true) {
			int randomNum = rand.nextInt((file.typeQuestions.get(type).size() - 1) + 1);
			if (!answered.get(type).contains(randomNum)) {
				ans = randomNum;
				answered.get(type).add(ans);
				break;
			}
		}
		lastType = type;
		lastQuestion = file.typeQuestions.get(type).get(ans);
		isStored=false;
		return file.typeQuestions.get(type).get(ans);

	}

	public void storeComment(String com, String type) {

		if (lastQuestion != null && !lastQuestion.equals("No question any more")) {
			questionComment.put(type + "-" + lastQuestion, com);
			isStored=true;
		}
	}

	public ArrayList<String> getQuestionsWithComments(){
		ArrayList<String> ans =new ArrayList<String>();
		for (java.util.Map.Entry<String, String> k : questionComment.entrySet()) {
			ans.add(k.getKey() + " " + k.getValue() + "\n");
		}
		return ans;
	}

}
