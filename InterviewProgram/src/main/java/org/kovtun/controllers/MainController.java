package org.kovtun.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.kovtun.models.Choose;
import org.kovtun.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

	@Autowired
	private MainService service;
    @GetMapping("/")
    public String start() {
    	
    	return "start";
    }
	@GetMapping("/upload")
	public String listUploadedFiles(Map<String, Object> model) throws IOException {
		
		return "upload";
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam(value="file") MultipartFile file, RedirectAttributes redirectAttributes)throws IOException, EncryptedDocumentException, InvalidFormatException {
		service.loadFile(file);
		service.addSession("default");
		return "redirect:/choose";
	}

	@PostMapping("/get")
	public String getQuestion(@RequestParam(value="type",required = false) String type,Map<String, Object> model) throws IOException, EncryptedDocumentException, InvalidFormatException {
		service.processGetPost(model, type);

		return "get";
	}

	@GetMapping("/get")
	public String getQuestionget(Map<String, Object> model) throws IOException, EncryptedDocumentException, InvalidFormatException {
		if (service.userSesstion.size()==0) {
			model.put("warn", "session not created");
			return "error1";
		}
		service.processGetGet(model);
		return "get";
	}

	@PostMapping(path = "/choose")

	public String chooseGet( @ModelAttribute Choose v,Map<String,Object> model) throws IOException {
		service.processChoosePost(model, v);
		
		return "redirect:/get";
	}

	@GetMapping("/choose")
	public String testget(Map<String, Object> model) throws IOException {
		if (service.userSesstion.size()==0) {
			model.put("warn", "session not created");
			return "error1";
		}
		service.processChoose(model);

		return "choose";
	}
	
	@PostMapping("/endinterview")
	@ResponseBody
	@CrossOrigin
	public String endInterview(@RequestParam("name") String respondent) throws FileNotFoundException, UnsupportedEncodingException {
		service.endInterview(respondent);
		return "ok";
	}
	@PostMapping("/addComment")
	@ResponseBody
	@CrossOrigin
	public String addComment(@RequestParam("comment")String comment) throws FileNotFoundException, UnsupportedEncodingException {
		System.out.println(comment);
		
			if (service.getLastQuestion("default")!=null) {
				service.addComment("default", comment, service.getLastType("default"));
			}
		
		return "ok";
	}
	@GetMapping("interviews/{id}")
	public String getInterviews(Map<String,Object> model,@PathVariable(value="id",required = false) Integer list) {
		if (list==null) {
			list=0;
		}
		return service.processGetDates(model,list);
	}
	@GetMapping("search")
	public String getInterviewsPost(Map<String,Object> model) {
		model.put("current", 0);
		return "search";
	}
	@GetMapping("search/{date}/{list}")
	public String getInterviewsSearch(Map<String,Object> model,@PathVariable("date") String date,@PathVariable("list") Integer list) {
		model.put("current", list);
		model.put("good", true);
		String[] args=date.split("-");
		model.put("year", args[0]);
		model.put("month", args[1]);
		model.put("day", args[2]);
		
		
		
		
		model.put("data", service.getInterviewByDate(date, list));
		return "search";
	}
}