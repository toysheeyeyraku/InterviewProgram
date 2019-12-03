package org.kovtun.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.kovtun.dao.Dao;
import org.kovtun.datamodel.Interview;
import org.kovtun.sessionmanager.SessionManager;
import org.kovtun.utils.ParserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
@Service
public class MainService {
	@Autowired
	private Dao dao;
	@Autowired
	private SessionManager sessionManager;
	@Value("${block.offset}")
	private Integer offsetBlock;

	public SessionManager getSesstionManager() {
		return sessionManager;
	}

	public void loadFile(String sessionName, MultipartFile file)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		ParserFile f = new ParserFile(file);
		sessionManager.getSession(sessionName).getQuestionManager().setFile(f);
	}

	private void deleteSession(String sessionName) {
		sessionManager.deleteSession(sessionName);
	}

	public void addSession(String sessionName) {
		if (sessionManager.containsSession(sessionName)) {
			return;
		}
		sessionManager.addSesstion(sessionName);
	}

	/** This method gets topic types from session */
	public ArrayList<String> getTypes(String sessionName) {
		ArrayList<String> ans = new ArrayList<String>();
		for (String s : sessionManager.getSession(sessionName).getQuestionManager().getTypes()) {
			ans.add(s);
		}
		return ans;
	}

	public void addComment(String comment,String sessionName) {
		sessionManager.getSession(sessionName).getQuestionManager().storeComment(comment);
	}

	public void endInterview(String respondent,String sessionName) 
			throws FileNotFoundException, UnsupportedEncodingException {
		org.kovtun.session.Session s = sessionManager.getSession(sessionName);

		Interview interview = new Interview();
		interview.setDate(LocalDate.now().toString());
		interview.setQuestionsWithComments(s.getQuestionManager().getQuestionWithComments());
		interview.setRespondent(respondent);
		dao.addInterview(interview);
		deleteSession(sessionName);
	}

	public ArrayList<Interview> getAllInterviews(Integer offset) {
		ArrayList<Interview> ans = new ArrayList<Interview>();
		List<Interview> mas = dao.getLastInterviews();
		int m = Math.min(dao.getLastInterviews().size(), offsetBlock * offset + offsetBlock);
		for (int i = offset * offsetBlock; i < m; i++) {
			ans.add(mas.get(i));
		}
		return ans;
	}

	public List<Interview> getInterviewByDate(String date, Integer offset) {
		List<Interview> data = dao.getInterviewsByDate(date);
		ArrayList<Interview> ans = new ArrayList<>();
		int m = Math.min(offset * offsetBlock + offsetBlock, data.size());
		for (int i = offset * offsetBlock; i < m; i++) {
			ans.add(data.get(i));
		}
		return ans;
	}

}
