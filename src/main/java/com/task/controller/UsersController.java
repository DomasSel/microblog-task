package com.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.entity.User;
import com.task.repo.UserRepository;

@RestController
@RequestMapping("/api")
public class UsersController {
	
	@Autowired
	UserRepository userRepository;

	@PostMapping(value = "/users/create-user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		try {
			User _user = userRepository
		          .save(new User(user.getUsername(), user.getEmail(), user.getPassword()));
		      return new ResponseEntity<>(_user, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	// creating user should require auth
	
}