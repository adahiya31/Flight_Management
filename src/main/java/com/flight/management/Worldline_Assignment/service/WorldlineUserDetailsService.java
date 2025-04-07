package com.flight.management.Worldline_Assignment.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WorldlineUserDetailsService implements UserDetailsService {

	private final Map<String, UserDetails> users = new HashMap<>();

	public WorldlineUserDetailsService() {
		users.put("worldline", User.builder()
				.username("worldline")
				.password(new BCryptPasswordEncoder().encode("worldline!"))
				.roles("USER")
				.build());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = getUsers().get(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}

	public Map<String, UserDetails> getUsers() {
		return users;
	}


}
