package com.test.services;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.entities.UserState;
import com.test.entities.UserStateId;
import com.test.repositories.TestRepositories;

@Service("url_service")
public class RecentURLService {
	@Autowired
	TestRepositories testRepo;
	
	public UserState setURL(UserState us) {
		if(us != null && us.getUserStateId().getUserId() != 0 && !us.getUserStateId().getUrl().isEmpty()) {
			us.setCreatedDt(getCurrentTime());
			return testRepo.save(us);
		}else {
			return null;
		}
	}
	
	public List<UserState> getUrls(int userId){
		return testRepo.findByuserStateIdUserId(userId).get();
	}
	
	public Timestamp getCurrentTime(){
		Date date = new Date(System.currentTimeMillis());
		Timestamp timestamp = new Timestamp(date.getTime());
		//16-NOV-18 03.31.20.457000000 PM
		
		return timestamp;
	}
}
