package org.kovtun.Dao;

import org.kovtun.dataModel.Interview;
import org.kovtun.repositories.InterviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Dao {
	@Autowired
	InterviewRepository interviewRepository;

	public void AddInterview(Interview interview) {
		interviewRepository.insert(interview);
	}
}
