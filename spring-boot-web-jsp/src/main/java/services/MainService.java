package services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mkyong.ParserFile;

import Dao.Dao;
import formdata.QuestionAddBody;
import models.Category;
import models.Question;
import models.Role;
import models.User;



@Service
public class MainService {
	@Autowired
	private Dao repository;
	private ParserFile file ;
	private HashMap<String,Session> userSesstion =new  HashMap<String,Session>();
	public void addNewUser(String login,String password) {
		User ans=new User();
		ans.setUsername(login);
		ans.setPassword("{noop}"+password);
		List<Role> roles =new ArrayList<Role>();
		roles.add(Role.USER);
		ans.setAuthorities(roles);
		ans.setAccountNonExpired(true);
		ans.setAccountNonLocked(true);
		ans.setCredentialsNonExpired(true);
		ans.setEnabled(true);
		
			repository.saveUser(ans);
		
	}
	public void addNewUser(User user,MultipartFile file) throws IOException {
		FileOutputStream out = new FileOutputStream("files/"+user.getUsername()+file.getOriginalFilename());
		out.write(file.getBytes());
		out.close();
		user.setResumePath("files/"+user.getUsername()+file.getOriginalFilename());
		repository.saveUser(user);
	}
	
	public void loadFile(MultipartFile file) throws EncryptedDocumentException, InvalidFormatException, IOException {
		this.file= new ParserFile(file);
		
	}
	public void deleteSession(String name) {
		userSesstion.remove(name);
	}
	public void addSession(String name) {
		if (userSesstion.containsKey(name)) {
			return ;
		}
		userSesstion.put(name, new services.Session(file,name));
	}
	public String  getQuestion(String name,String type) {
		
		return userSesstion.get(name).getQuestion(type);
	}
	public String BuildCheckBoxes() {
		
		StringBuilder body =new StringBuilder();
		String s="<label >%s</label><input type=\"checkbox\"  name=\"def\" value=\"%s\"><Br>";
		for (String key:file.typeQuestions.keySet()) {
			body.append(String.format(s,key,key )+"\n");
		}
		return body.toString();
	}
	private String data;
	public String BuildegetPage(String[] p) {
		String shab="<form th:action=\"@{/get}\" method=\"post\" enctype=\"application/json\">\r\n" + 
				"             <input type=\"hidden\" name=\"type\" value=\"%s\"/> \r\n" + 
				"           \r\n" + 
				"            <input type=\"hidden\"\r\n" + 
				"    		name=\"${_csrf.parameterName}\"\r\n" + 
				"    		value=\"${_csrf.token}\"/>\r\n" + 
				"            <div><input type=\"submit\" value=\"%s\"/></div>\r\n" + 
				"        </form>";
		StringBuilder ans =new StringBuilder();
		if (p!=null) {
			for (String s:p) {
				ans.append(String.format(shab, s,s));
			}
		}
		data=ans.toString();
		return ans.toString();
	}
	public String getGetPage() {
		return data;
	}
	public String getLastQuestion(String user) {
		return userSesstion.get(user).getLastquestion();
	}
	public void addComment(String user,String comment,String type) {
		userSesstion.get(user).storeComment(comment,type);
	}
	public void endInterview(String user) throws FileNotFoundException, UnsupportedEncodingException {
		Session s=userSesstion.get(user);
		String data=s.buildQC();
		userSesstion.remove(user);
		PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
		writer.print(data);
		writer.close();
	}
	public String getLastType(String user) {
		return userSesstion.get(user).getLasttype();
	}
	public boolean isStored(String user,String question) {
		if (userSesstion.get(user).getQuestionComment().containsKey(question)) {
			return true;
		}
		return false;
	}
}
