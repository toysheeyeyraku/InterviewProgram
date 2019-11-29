package org.kovtun.datamodel;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
@Data
@Document(collection = "Interview")
public class Interview {
	@Id
	private String id;
	private String respondent;
	private String date;
	private ArrayList<String> questionsWithComments;

}
