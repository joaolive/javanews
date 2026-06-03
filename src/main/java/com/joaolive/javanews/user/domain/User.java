package com.joaolive.javanews.user.domain;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import com.joaolive.javanews.user.domain.valueobject.Bio;
import com.joaolive.javanews.user.domain.valueobject.Email;
import com.joaolive.javanews.user.domain.valueobject.Name;
import com.joaolive.javanews.user.domain.valueobject.Role;
import com.joaolive.javanews.user.domain.valueobject.Username;

public class User {
	private final UUID		id;
	private Email			email;
	private Username		username;
	private String			password;
	private Name			firstName;
	private Name			lastName;
	private Bio				bio;
	private String			avatarKey;
	private final Instant	createdAt;
	private Instant			updatedAt;

	private Set<Role>		roles;

	private User(UUID id, Email email, Username username, String password, Name firstName, Name lastName, Bio bio, String avatarKey,
			Instant createdAt, Instant updatedAt, Set<Role> roles) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.bio = bio;
		this.avatarKey = avatarKey;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.roles = roles;
	}

	public static User createUser(Email email, Username username, String password, Name firstName, Name lastName, Bio bio, String avatarKey) {
		Instant now = Instant.now();
		return new User(UUID.randomUUID(), email, username, password, firstName, lastName, bio, avatarKey, now, now, Set.of(Role.USER));
	}

	public static User reconstitute(UUID id, String email, String username, String password, String firstName, String lastName, String bio, String avatarKey,
			Instant createdAt, Instant updatedAt, Set<Role> roles) {
		return new User(id, Email.restore(email), Username.restore(username), password, Name.restore(firstName), Name.restore(lastName), Bio.restore(bio), avatarKey, createdAt, updatedAt, roles);
	}

	public void updateUser(Name firstName, Name lastName, Bio bio, String avatarKey) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.bio = bio;
		this.avatarKey = avatarKey;
		updatedAt = Instant.now();
	}

	public UUID getId() {
		return id;
	}

	public Email getEmail() {
		return email;
	}

	public Username getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Name getFirstName() {
		return firstName;
	}

	public Name getLastName() {
		return lastName;
	}

	public Bio getBio() {
		return bio;
	}

	public String getAvatarKey() {
		return avatarKey;
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
