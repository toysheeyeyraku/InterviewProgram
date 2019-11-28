package org.kovtun.dataModel;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection = "Interview")
public class Interview {
	@Id
	private String id;
	private String respondent;
	private String date;
	private ArrayList<String> questionsWithComments;
	
}
