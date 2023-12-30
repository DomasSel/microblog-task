package com.task.service;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.task.entity.User;
import com.task.repo.UserRepository;

@Service
public class UserDetail implements UserDetailsService {
	
	@Autowired    
	UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if(user==null){
			throw new UsernameNotFoundException("User does not exist by Username");
		}
		Set<GrantedAuthority> authorities = user.getRoles().stream()
		                .map((role) -> new SimpleGrantedAuthority(role.getName()))
		                .collect(Collectors.toSet());
		        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),authorities);
    }
}
