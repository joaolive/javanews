package com.joaolive.javanews.user.domain;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import com.joaolive.javanews.user.domain.valueobject.Email;
import com.joaolive.javanews.user.domain.valueobject.Role;

public class User {
	private final UUID id;
	private Email email;
	private String password;
	private final Instant createdAt;
	private Instant updatedAt;
	private Set<Role> roles;

	private User(UUID id, Email email, String password,
			Instant createdAt, Instant updatedAt, Set<Role> roles) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.roles = roles;
	}

	public static User create(Email email, String password) {
		Instant now = Instant.now();
		return new User(UUID.randomUUID(), email, password, now, now, Set.of(Role.USER));
	}

	public static User reconstitute(UUID id, String email, String password,
			Instant createdAt, Instant updatedAt, Set<Role> roles) {
		return new User(id, Email.restore(email), password, createdAt, updatedAt, roles);
	}

	public UUID getId() {
		return id;
	}

	public Email getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public Set<Role> getRoles() {
		return roles;
	}
}
