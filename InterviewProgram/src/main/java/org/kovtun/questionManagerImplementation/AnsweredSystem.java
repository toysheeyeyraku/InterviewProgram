package org.kovtun.questionManagerImplementation;

import java.util.HashMap;
import java.util.HashSet;

public class AnsweredSystem {
	private HashMap<String, HashSet<Integer>> answered = new HashMap<String, HashSet<Integer>>();
	public Integer getAnsweredQuestionsSizeByType(String type) {
		check(type);
		return answered.get(type).size();
		
	}
	public Boolean containsQuestion(String type,Integer index) {
		return answered.get(type).contains(index);
	}
	public void addQuestion(String type,Integer index) {
		 answered.get(type).add(index);
	}
	public void restart() {
		answered = new HashMap<String, HashSet<Integer>>();
	}
	private void check(String type) {
		if (!answered.containsKey(type)) {
			answered.put(type, new HashSet<Integer>());
		}
	}
	
	
}
