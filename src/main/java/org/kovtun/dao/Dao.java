package org.kovtun.dao;

import java.util.List;

import org.kovtun.datamodel.Interview;
import org.kovtun.repositories.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Bogdan Kovtun
 * @version 1
 */
@Service
public class Dao {
	@Autowired
	InterviewRepository interviewRepository;

	public void addInterview(Interview interview) {
		interviewRepository.insert(interview);
	}

	public List<Interview> getLastInterviews() {
		return interviewRepository.findByTimeAll();
	}

	public List<Interview> getInterviewsByDate(String date) {
		return interviewRepository.findByDate(date);
	}
}
