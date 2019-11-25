package org.kovtun.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.kovtun.utils.ParserFile;
import org.kovtun.utils.Session;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MainService {

	private ParserFile file;
	private HashMap<String, Session> userSesstion = new HashMap<String, Session>();
	private ArrayList<String> data;
	public ArrayList<String> getData(){
		return data;
	}
	public void loadFile(MultipartFile file) throws EncryptedDocumentException, InvalidFormatException, IOException {
		this.file = new ParserFile(file);

	}

	public void deleteSession(String name) {
		userSesstion.remove(name);
	}

	public void addSession(String name) {
		if (userSesstion.containsKey(name)) {
			return;
		}
		userSesstion.put(name, new org.kovtun.utils.Session(file, name));
	}

	public String getQuestion(String name, String type) {

		return userSesstion.get(name).getQuestion(type);
	}

	public Map<String,Object> processChoose(Map<String,Object> obj)  {
		obj.put("body", new ArrayList<String>( file.typeQuestions.keySet()));
		return obj;
	}
	public void setDataGet(String [] data) {
		this.data =new ArrayList<String>();
		for (String s:data) {
			this.data.add(s);
		}
	}
	

	

	public String getLastQuestion(String user) {
		return userSesstion.get(user).getLastquestion();
	}

	public void addComment(String user, String comment, String type) {
		userSesstion.get(user).storeComment(comment, type);
	}

	public void endInterview(String user) throws FileNotFoundException, UnsupportedEncodingException {
		Session s = userSesstion.get(user);
		String data = s.buildQC();
		userSesstion.remove(user);
		PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
		writer.print(data);
		writer.close();
	}

	public String getLastType(String user) {
		return userSesstion.get(user).getLasttype();
	}

	public boolean isStored(String user, String question) {
		if (userSesstion.get(user).getQuestionComment().containsKey(question)) {
			return true;
		}
		return false;
	}
}
