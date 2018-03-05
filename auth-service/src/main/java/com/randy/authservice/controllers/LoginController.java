package com.randy.authservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.randy.authservice.entities.User;
import com.randy.authservice.services.UserService;

@RestController
@RequestMapping("cred")
public class LoginController {
	@Autowired
	UserService logService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User u)
	{
		User ret = logService.checkUser(u);
		if (ret == null)
		{
			return new ResponseEntity<>("no user found for " + u, HttpStatus.UNAUTHORIZED);
		}
		else {
			return new ResponseEntity<>("welcome "+ ret.getFirstname(), HttpStatus.OK);
		}
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User u)
	{
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	

}
