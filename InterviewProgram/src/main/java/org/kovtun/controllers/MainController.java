package org.kovtun.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.kovtun.formdata.EndInterviewData;
import org.kovtun.formdata.FileFormData;
import org.kovtun.formdata.QuestionTypeFormData;
import org.kovtun.models.Choose;
import org.kovtun.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

	@Autowired
	private MainService service;

	@GetMapping("/upload")
	public String listUploadedFiles(Model model) throws IOException {
		return "upload";
	}

	@PostMapping("/upload")
	public String handleFileUpload(@ModelAttribute FileFormData data, RedirectAttributes redirectAttributes)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		service.loadFile(data.getFile());
		return "redirect:/choose";
	}

	@PostMapping("/get")
	public String getQuestion(@ModelAttribute QuestionTypeFormData form,
			@RequestParam(value = "comment", required = false) String comment, @ModelAttribute EndInterviewData end,
			Map<String, Object> model) throws IOException, EncryptedDocumentException, InvalidFormatException {
		String question = null;
		if (form.getType() != null) {
			if (service.getLastQuestion("default") == null
					|| service.getLastQuestion("default") == "No question any more" || service.isStored("default",
							service.getLastType("default") + "-" + service.getLastQuestion("default"))) {
				question = service.getQuestion("default", form.getType());
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
		if (end.getEnd() != null) {
			service.endInterview("default");
			return "redirect:/upload";
		}
		model.put("question", question);
		model.put("data", service.getGetPage());

		return "get";
	}

	@GetMapping("/get")
	public String getQuestionget(Map<String, Object> model)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		service.addSession("default");
		model.put("data", service.getGetPage());
		return "get";
	}

	@PostMapping(path = "/choose")

	public String chooseGet(Principal principal, @ModelAttribute Choose v) throws IOException {
		service.BuildegetPage(v.getDef());
		return "redirect:/get";
	}

	@GetMapping("/choose")
	public String testget(Map<String, Object> model) throws IOException {
		model.put("body", service.BuildCheckBoxes());

		return "choose";
	}

}