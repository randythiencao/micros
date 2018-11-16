package com.test.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.entities.UserState;
import com.test.services.RecentURLService;

@CrossOrigin(origins = "http://10.20.181.167:4200")
@RestController
@RequestMapping("/initTest")
public class InitTestController {
	@Autowired
	RecentURLService urlService;
	
	@GetMapping("/{id}/recentURL")
	public ResponseEntity<?> getRecentURL(@PathVariable int id) throws JsonProcessingException {
		List<UserState> uState = urlService.getUrls(id);
		for(UserState uS : uState) {
			System.out.println(uS.toString());
		}
		return null;
	}
	
	@PostMapping("/recentURL")
	public ResponseEntity<?> saveRecentURLs(@RequestBody UserState u) throws JsonProcessingException {
		if(urlService.setURL(u) != null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return null;
	}
}
