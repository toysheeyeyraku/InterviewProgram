package mongorepositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import models.Category;



public interface CategoryRepository extends MongoRepository<Category, String> {

    public Optional<Category> findByName(String name);
    
    
}
