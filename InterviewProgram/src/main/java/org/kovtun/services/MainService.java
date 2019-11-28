package org.kovtun.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jasper.tagplugins.jstl.core.Choose;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.kovtun.Dao.Dao;
import org.kovtun.dataModel.Interview;
import org.kovtun.utils.ParserFile;
import org.kovtun.utils.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MainService {
	@Autowired
	private Dao dao;
	private ParserFile file;
	public HashMap<String, Session> userSesstion = new HashMap<String, Session>();
	private ArrayList<String> data;
	
	public void processGetPost(Map<String, Object> model, String type) {
		String question = null;
		if (!isCommented("default")) {
			model.put("warn", "No commented");
		} else {
			question = getQuestion("default", type);
		}

		if (question == null) {
			question = getLastQuestion("default");
			if (question == null) {
				model.put("qustion", "No question");
			} else {
				model.put("question", question);
			}
		}else {
			model.put("question", question);
		}

		model.put("data", data);
	}

	public void processGetGet(Map<String, Object> model) {
		
		model.put("data", data);
	}

	public void processChoosePost(Map<String, Object> model, org.kovtun.models.Choose v) {
		if (v != null) {
			data = v.getDef();
		} else {

			data = new ArrayList<String>();
		}
	}

	
    public void loadFile(MultipartFile file) throws EncryptedDocumentException, InvalidFormatException, IOException {
		this.file = new ParserFile(file);

	}

	private void deleteSession(String name) {
		userSesstion.remove(name);
	}

	public void addSession(String name) {
		if (userSesstion.containsKey(name)) {
			return;
		}
		userSesstion.put(name, new org.kovtun.utils.Session(file, name));
	}

	private String getQuestion(String name, String type) {

		return userSesstion.get(name).getQuestion(type);
	}

	public Map<String, Object> processChoose(Map<String, Object> obj) {
		obj.put("body", new ArrayList<String>(file.typeQuestions.keySet()));
		return obj;
	}

	public String getLastQuestion(String user) {
		return userSesstion.get(user).getLastQuestion();
	}

	public void addComment(String user, String comment, String type) {
		userSesstion.get(user).storeComment(comment, type);
	}

	public void endInterview(String respondent) throws FileNotFoundException, UnsupportedEncodingException {
		Session s = userSesstion.get("default");

		Interview interview = new Interview();
		interview.setDate(LocalDate.now().toString());
		interview.setQuestionsWithComments(s.getQuestionsWithComments());
		interview.setRespondent(respondent);
		dao.AddInterview(interview);
		deleteSession("default");

	}

	public String getLastType(String user) {
		return userSesstion.get(user).getLastType();
	}

	public boolean isStored(String user, String question) {
		if (userSesstion.get(user).getQuestionComment().containsKey(question)) {
			return true;
		}
		return false;
	}

	public boolean isCommented(String user) {
		return userSesstion.get(user).isStored();
	}
	public String processGetDates(Map<String,Object> model,Integer list) {
		ArrayList<Interview> ans =new ArrayList<Interview>();
		List<Interview> mas=dao.getLastInterviews();
		for (int i=list*5;i<Math.min( dao.getLastInterviews().size(),5*list+5);i++) {
			ans.add(mas.get(i));
		}
		model.put("data", ans);
		model.put("current", list);
		return "dates";
	}
	public List<Interview> getInterviewByDate(String date,Integer list){
		List<Interview> data =dao.getInterviewsByDate(date);
		System.out.println(data.size());
		ArrayList<Interview> ans =new ArrayList<>();
		for (int i=list*5;i<Math.min(list*5+5, data.size());i++) {
			ans.add(data.get(i));
		}
		return ans;
	}
	
}
