package mongorepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import models.User;





public interface UserRepository extends MongoRepository<User, String> {

    public Optional<User> findByUsername(String name);
    
    
}
