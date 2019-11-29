package org.kovtun.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.kovtun.models.Choose;
import org.kovtun.questionsystem.Question;
import org.kovtun.questionsystem.QuestionSystemVerdict;
import org.kovtun.services.MainService;
import org.kovtun.utils.ParserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
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
		service.addSession("default");
		return "upload";
	}

	@PostMapping("/upload")
	public String handleFileUpload(@RequestParam(value = "file") MultipartFile file,
			RedirectAttributes redirectAttributes)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		service.getSesstionManager().getSession("default").getQuestionManager().setFile(new ParserFile(file));

		return "redirect:/choose";
	}

	@PostMapping("/get")
	public String getQuestion(@RequestParam(value = "type", required = false) String type, Map<String, Object> model)
			throws IOException, EncryptedDocumentException, InvalidFormatException {
		Question question = service.getSesstionManager().getSession("default").getQuestionManager().getQuestion(type);

		if (question.getVerdict() == QuestionSystemVerdict.INVALID_TYPE) {
			model.put("warn", "Invalid type");
		}
		if (question.getVerdict() == QuestionSystemVerdict.NO_COMMENTED) {
			model.put("warn", "No commented");
			model.put("question", service.getSesstionManager().getSession("default").getQuestionManager()
					.getLastQuestion().getQuestion());
		}
		if (question.getVerdict() == QuestionSystemVerdict.NO_FILE_LOADED) {
			model.put("warn", "No File Loaded");
		}
		if (question.getVerdict() == QuestionSystemVerdict.QUESTIONS_RUN_OUT) {
			model.put("warn", "questions run out");
		}
		if (question.getVerdict() == QuestionSystemVerdict.OK) {
			model.put("question", question.getQuestion());
		}
		model.put("data", service.getSesstionManager().getSession("default").getQuestionManager().getChosenTypes());
		return "get";

	}

	@GetMapping("/get")
	public String getQuestionget(Map<String, Object> model) {

		if (!service.getSesstionManager().containsSession("default")) {
			model.put("warn", "session not created");
			return "error1";
		}

		Question question = service.getSesstionManager().getSession("default").getQuestionManager().getLastQuestion();
		if (question.getVerdict() == QuestionSystemVerdict.OK) {

			model.put("question", question.getQuestion());
		}
		model.put("data", service.getSesstionManager().getSession("default").getQuestionManager().getChosenTypes());
		return "get";
	}

	@PostMapping(path = "/choose")

	public String chooseGet(@ModelAttribute Choose options, Map<String, Object> model) throws IOException {
		ArrayList<String> chosenTypes;
		if (options != null) {
			chosenTypes = options.getOptions();
		} else {

			chosenTypes = new ArrayList<String>();
		}
		service.getSesstionManager().getSession("default").getQuestionManager().setChosenTypes(chosenTypes);
		return "redirect:/get";
	}

	@GetMapping("/choose")
	public String testget(Map<String, Object> model) throws IOException {
		if (!service.getSesstionManager().containsSession("default")) {
			model.put("warn", "session not created");
			return "error1";
		}
		model.put("body", service.getTypes());

		return "choose";
	}

	@PostMapping("/endinterview")
	@ResponseBody
	@CrossOrigin
	public String endInterview(@RequestParam("name") String respondent)
			throws FileNotFoundException, UnsupportedEncodingException {
		service.endInterview(respondent);
		return "ok";
	}

	@PostMapping("/addComment")
	@ResponseBody
	@CrossOrigin
	public String addComment(@RequestParam("comment") String comment)
			throws FileNotFoundException, UnsupportedEncodingException {
		System.out.println(comment);

		service.getSesstionManager().getSession("default").getQuestionManager().storeComment(comment);

		return "ok";
	}

	@GetMapping("interviews/{id}")
	public String getInterviews(Map<String, Object> model,
			@PathVariable(value = "id", required = false) Integer offset) {

		if (offset == null) {
			offset = 0;
		}
		model.put("data", service.getAllInterviews(offset));
		model.put("current", offset);
		return "dates";
	}

	@GetMapping("search")
	public String getInterviewsPost(Map<String, Object> model) {
		model.put("current", 0);
		return "search";
	}

	@GetMapping("search/{date}/{list}")
	public String getInterviewsSearch(Map<String, Object> model, @PathVariable("date") String date,
			@PathVariable("list") Integer offset) {
		model.put("current", offset);
		model.put("good", true);
		String[] args = date.split("-");
		model.put("year", args[0]);
		model.put("month", args[1]);
		model.put("day", args[2]);

		model.put("data", service.getInterviewByDate(date, offset));
		return "search";
	}
}