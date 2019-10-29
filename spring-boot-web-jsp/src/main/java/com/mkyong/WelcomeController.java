package com.mkyong;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import formdata.AddUserBody;
import formdata.FileFormData;
import formdata.QuestionTypeFormData;
import javafx.scene.input.DataFormat;
import services.MainService;





@Controller
public class WelcomeController {

	@Autowired
	private MainService service;

	private String message = "Hello World";
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(path="/welcome")
	public String welcome(Map<String, Object> model,Principal principal) {
		
		model.put("message", this.message);
		try {
			//System.out.println(principal.getName());
		}catch(Exception exc) {
			///System.out.println(principal+" ACTUAL");
		}
		return "welcome";
	}
	@GetMapping(path="/login")
	public String loginGet() {
		
		return "login";
	}
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/registration")
    public String registration() {
       
        return "registration";
    }
	@PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/registration")
    public String registrationpost(@ModelAttribute AddUserBody userForm) {
    	service.addNewUser(userForm.getUsername(), userForm.getPassword());
    	
        return "registration";
    }
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/upload")
    public String listUploadedFiles(Model model) throws IOException {

        

        return "upload";
    }
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/upload")
	public String handleFileUpload(@ModelAttribute FileFormData data,RedirectAttributes redirectAttributes) 
    		throws IOException, EncryptedDocumentException, InvalidFormatException {
		
		 service.loadFile(data.getFile());
	     
        return "redirect:/choose";}
	
	@PostMapping("/get")
    public String getQuestion(Principal principal,@ModelAttribute QuestionTypeFormData form,Map<String, Object> model) 
    		throws IOException, EncryptedDocumentException, InvalidFormatException {
		
		String question= service.getQuestion(principal.getName(),form.getType() );
		if (question==null) {
			question="No Questions any more";
		}
	     model.put("question", question);
	     model.put("data", service.getGetPage());
        return "get";
    }
	@GetMapping("/get")
    public String getQuestionget(Principal principal,Map<String, Object> model) 
    		throws IOException, EncryptedDocumentException, InvalidFormatException {
		
		 service.addSession(principal.getName());
		 model.put("data", service.getGetPage());
	  
        return "get";
    }
	
	
}