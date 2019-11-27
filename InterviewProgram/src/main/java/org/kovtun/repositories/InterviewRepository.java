package org.kovtun.repositories;

import java.util.List;
import java.util.Optional;

import org.kovtun.dataModel.Interview;
import org.springframework.data.mongodb.repository.MongoRepository;







public interface InterviewRepository extends MongoRepository<Interview, String> {

    public Optional<Interview> findByRespondent(String name);
    
    
}
