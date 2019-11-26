package org.kovtun.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
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
	public String listUploadedFiles(Map<String, Object> model) throws IOException {
		ArrayList<String> mas =new ArrayList<String>();
		mas.add("Value1");
		mas.add("Value2");
		model.put("students",mas);
		return "upload";
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam(value="file") MultipartFile file, RedirectAttributes redirectAttributes)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		service.loadFile(file);
		return "redirect:/choose";
	}

	@PostMapping("/get")
	public String getQuestion(@RequestParam(value="type",required = false) String type,
			
			Map<String, Object> model) throws IOException, EncryptedDocumentException, InvalidFormatException {
		String question = null;
		
			if (service.getLastQuestion("default") == null
					|| service.getLastQuestion("default").equals( "No question any more" )|| service.isStored("default",
							service.getLastType("default") + "-" + service.getLastQuestion("default"))) {
				question = service.getQuestion("default", type);
			} else {
				model.put("warn", "<div id='noStored'>No Stored</div>");
			}
		
		
		if (question == null) {
			question = service.getLastQuestion("default");
		}
		
		model.put("question", question);
		model.put("data", service.getData());

		return "get";
	}

	@GetMapping("/get")
	public String getQuestionget(Map<String, Object> model)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		service.addSession("default");
		model.put("data", service.getData());
		return "get";
	}

	@PostMapping(path = "/choose")

	public String chooseGet( @ModelAttribute Choose v) throws IOException {
		if (v!=null) {
			service.setDataGet(v.getDef());
		}else {
			
			service.setDataGet(new ArrayList<String>() );
		}
		
		return "redirect:/get";
	}

	@GetMapping("/choose")
	public String testget(Map<String, Object> model) throws IOException {
		service.processChoose(model);

		return "choose";
	}
	@GetMapping("/test")
	public String testmap(Map<String, Object> model) {
		model.put("s", "some value");
		
		return "test";
	}
	@PostMapping("/endinterview")
	@ResponseBody
	@CrossOrigin
	public String endInterview() throws FileNotFoundException, UnsupportedEncodingException {
		service.endInterview("default");
		return "ok";
	}
	@PostMapping("/addComment")
	@ResponseBody
	@CrossOrigin
	public String addComment(@RequestBody String comment) throws FileNotFoundException, UnsupportedEncodingException {
		System.out.println(comment);
		if (comment != null && comment.length() > 0) {
			if (service.getLastType("default") != null) {
				service.addComment("default", comment, service.getLastType("default"));
			}
		}
		return "ok";
	}
}