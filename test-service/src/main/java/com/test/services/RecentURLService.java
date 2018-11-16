package com.test.services;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.entities.UserState;
import com.test.repositories.TestRepositories;

@Service("url_service")
public class RecentURLService {
	@Autowired
	TestRepositories testRepo;
	
	public UserState setURL(UserState us) {
		if(us != null && us.getUserId() != 0) {
			us.setCreatedDt(getCurrentTime());
			return testRepo.save(us);
		}else {
			return null;
		}
	}
	
	public List<UserState> getUrls(int userId){
		return (List<UserState>) testRepo.findByuserId(userId).get();
	}
	
	public Timestamp getCurrentTime(){
		Date date = new Date(System.currentTimeMillis());
		Timestamp timestamp = new Timestamp(date.getTime());
		return timestamp;
	}
}
