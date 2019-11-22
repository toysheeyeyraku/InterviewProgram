package org.kovtun.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

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

	public String BuildCheckBoxes() {

		StringBuilder body = new StringBuilder();
		String s = "<label >%s</label><input type=\"checkbox\"  name=\"def\" value=\"%s\"><Br>";
		for (String key : file.typeQuestions.keySet()) {
			body.append(String.format(s, key, key) + "\n");
		}
		return body.toString();
	}

	private String data;

	public String BuildegetPage(String[] p) {
		String shab = "<form th:action=\"@{/get}\" method=\"post\" enctype=\"application/json\">\r\n"
				+ "             <input type=\"hidden\" name=\"type\" value=\"%s\"/> \r\n" + "           \r\n"
				+ "            <input type=\"hidden\"\r\n" + "    		name=\"${_csrf.parameterName}\"\r\n"
				+ "    		value=\"${_csrf.token}\"/>\r\n"
				+ "            <div><input type=\"submit\" value=\"%s\"/></div>\r\n" + "        </form>";
		StringBuilder ans = new StringBuilder();
		if (p != null) {
			for (String s : p) {
				ans.append(String.format(shab, s, s));
			}
		}
		data = ans.toString();
		return ans.toString();
	}

	public String getGetPage() {
		return data;
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
