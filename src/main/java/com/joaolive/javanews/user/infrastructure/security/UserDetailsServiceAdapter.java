package com.joaolive.javanews.user.infrastructure.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.joaolive.javanews.user.domain.User;
import com.joaolive.javanews.user.domain.UserRepository;
import com.joaolive.javanews.user.domain.valueobject.Email;

@Service
public class UserDetailsServiceAdapter implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailsServiceAdapter(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(Email.create(email))
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new UserDetailsAdapter(user);
	}
	
}
