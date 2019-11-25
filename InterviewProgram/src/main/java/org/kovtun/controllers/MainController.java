package org.kovtun.controllers;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

	@Autowired
	private MainService service;

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {
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
			@RequestParam(value = "comment", required = false) String comment, @RequestParam(value="end",required = false) String end,
			Map<String, Object> model) throws IOException, EncryptedDocumentException, InvalidFormatException {
		String question = null;
		if (type != null) {
			if (service.getLastQuestion("default") == null
					|| service.getLastQuestion("default").equals( "No question any more" )|| service.isStored("default",
							service.getLastType("default") + "-" + service.getLastQuestion("default"))) {
				question = service.getQuestion("default", type);
			} else {
				model.put("warn", "No stored");
			}
		}
		if (comment != null && comment.length() > 0) {
			if (service.getLastType("default") != null) {
				service.addComment("default", comment, service.getLastType("default"));
			}
		}
		if (question == null) {
			question = service.getLastQuestion("default");
		}
		if (end != null) {
			service.endInterview("default");
			return "redirect:/";
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
		service.setDataGet(v.getDef());
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
}