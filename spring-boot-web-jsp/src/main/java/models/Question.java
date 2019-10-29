package models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="questions")
public class Question {
	@Id
		private String id;
		private String body ;
		private String category;
}
