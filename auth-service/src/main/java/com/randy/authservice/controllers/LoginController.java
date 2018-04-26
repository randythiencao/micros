package com.randy.authservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.randy.authservice.entities.User;
import com.randy.authservice.services.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("cred")
public class LoginController {
	@Autowired
	UserService logService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User u) throws JsonProcessingException
	{
		User ret = logService.checkUser(u);
		System.out.println(u);
		if (ret == null)
		{
			System.out.println("returning failed");
			return new ResponseEntity<>("no user found for " + u, HttpStatus.UNAUTHORIZED);
		}
		else {
			
			ObjectMapper mapper = new ObjectMapper();
			User hi = new User();
			hi.setFirstname("success");
			hi.setLastname("return");


			//Object to JSON in String
			String jsonInString = mapper.writeValueAsString(hi);
		
			System.out.println("returning success");
			return new ResponseEntity<>(jsonInString, HttpStatus.OK);
		}
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User u)
	{
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	

}
