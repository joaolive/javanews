package com.joaolive.javanews.user.infrastructure.security;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.joaolive.javanews.auth.AuthenticatedUser;
import com.joaolive.javanews.user.domain.User;

public class UserDetailsAdapter implements AuthenticatedUser {
	private final User user;

	public UserDetailsAdapter(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getRoles().stream()
				.map(x -> new SimpleGrantedAuthority("ROLE_" + x.name()))
				.collect(Collectors.toSet());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail().getValue();
	}

	@Override
	public UUID getId() {
		return user.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
