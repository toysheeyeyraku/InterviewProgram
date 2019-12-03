package org.kovtun.questionsystem;

import java.util.HashMap;
import java.util.HashSet;
/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
public class AnsweredSystem {
	/**Store answered questions*/
	
	/**Here stored topic type as a key and set of indexes of questions that corresponds to topic type as a value*/
	private HashMap<String, HashSet<Integer>> answered = new HashMap<String, HashSet<Integer>>();

	public int getAnsweredQuestionsSizeByType(String type) {
		checkContainsType(type);
		return answered.get(type).size();

	}

	public boolean containsQuestion(String type, Integer index) {
		return answered.get(type).contains(index);
	}

	public void addQuestion(String type, Integer index) {
		answered.get(type).add(index);
	}

	public void restart() {
		answered = new HashMap<String, HashSet<Integer>>();
	}

	private void checkContainsType(String type) {
		if (!answered.containsKey(type)) {
			answered.put(type, new HashSet<Integer>());
		}
	}

}
