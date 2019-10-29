package mongorepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import models.Question;



public interface QuestionRepository extends MongoRepository<Question, String> {

    public Optional<Question> findByBody(String name);
    public List<Question> findByCategory(String category);
    
}
