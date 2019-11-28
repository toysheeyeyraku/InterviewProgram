package org.kovtun.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.kovtun.dataModel.Interview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;







public interface InterviewRepository extends MongoRepository<Interview, String> {

    public Optional<Interview> findByRespondent(String name);
    @Query("{}")
    public List<Interview> findByTimeAll();
    public List<Interview> findByDate(String date);
}
