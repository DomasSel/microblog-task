package com.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.dto.LoginDto;

@RestController
@RequestMapping("/api")
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){	
	        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	                loginDto.getUsername(), loginDto.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        return new ResponseEntity<>("User login successful!", HttpStatus.OK);
    }
	
	// FOR TESTING - DELETE LATER
	@GetMapping("/current-user")
	public ResponseEntity<String> getCurrentUser(){	
			Authentication _authentication = SecurityContextHolder.getContext().getAuthentication();
	        String currentPrincipalName = _authentication.getName();
	        return new ResponseEntity<>(currentPrincipalName, HttpStatus.OK);
    }
	
}