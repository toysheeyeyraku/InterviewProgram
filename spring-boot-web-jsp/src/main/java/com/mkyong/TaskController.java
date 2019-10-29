package com.mkyong;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import models.Choose;
import models.User;
import services.MainService;

@Controller
public class TaskController {
	@Autowired
	private MainService service;
	@PostMapping(path="/choose")
	
    public String chooseGet(Principal principal,@ModelAttribute Choose v ) throws IOException {
		service.BuildegetPage(v.getDef());
        return "redirect:/get";
    }
	@GetMapping("/choose")
    public String testget(Map<String,Object> model) throws IOException {
		model.put("body", service.BuildCheckBoxes());
		
        return "choose";
    }
}
