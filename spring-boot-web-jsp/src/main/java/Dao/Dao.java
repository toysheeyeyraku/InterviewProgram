package Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.Category;
import models.Question;
import models.User;
import mongorepositories.CategoryRepository;
import mongorepositories.QuestionRepository;
import mongorepositories.UserRepository;


@Service
public class Dao {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Optional<User> findByUsername(String name){
		
		return userRepository.findByUsername(name);
	}
	public void saveUser(User user) {
		userRepository.save(user);
	}
	public Optional<Category> findByName(String category){
		return categoryRepository.findByName(category);
	}
	public Optional<Question> findByBody(String body){
		return questionRepository.findByBody(body);
	}
	public void saveCategory(Category category) {
		categoryRepository.save(category);
	}
	public void saveQuestion(Question question) {
		questionRepository.save(question);
	}
	public List<Question> getByCategory(String category){
		return questionRepository.findByCategory(category);
	}
}
