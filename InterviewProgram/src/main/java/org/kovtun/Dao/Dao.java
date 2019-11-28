package org.kovtun.Dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
	public List<Interview> getLastInterviews(){
		return interviewRepository.findByTimeAll();
	}
	public List<Interview>getInterviewsByDate(String date){
		return interviewRepository.findByDate(date);
	}
}
