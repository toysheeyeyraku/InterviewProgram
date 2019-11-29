package org.kovtun.repositories;

import java.util.List;
import java.util.Optional;

import org.kovtun.datamodel.Interview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
public interface InterviewRepository extends MongoRepository<Interview, String> {

    public Optional<Interview> findByRespondent(String name);
    @Query("{}")
    public List<Interview> findByTimeAll();
    public List<Interview> findByDate(String date);
}
