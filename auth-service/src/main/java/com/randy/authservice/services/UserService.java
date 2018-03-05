package com.randy.authservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.randy.authservice.entities.User;
import com.randy.authservice.repositories.UserRepository;

@Service("login_service")
public class UserService {

	@Autowired
	UserRepository userRepo;

	public User checkUser(User u) {
		Optional<User> ret = userRepo.findByUsername(u.getUsername());
		if (ret.isPresent())
			return ret.get();
		else
			return null;
	}

	public User regUser(User u) {
		Optional<User> ret = userRepo.findByUsername(u.getUsername());
		if (ret.isPresent())
			return null;
		else {
			userRepo.save(u);
			ret = userRepo.findByUsername(u.getUsername());
			return ret.get();
		}

	}
}
