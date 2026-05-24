package com.joaolive.javanews.user.domain.model;

import java.time.Instant;
import java.util.UUID;

import com.joaolive.javanews.user.domain.valueobject.Bio;
import com.joaolive.javanews.user.domain.valueobject.Email;
import com.joaolive.javanews.user.domain.valueobject.Name;
import com.joaolive.javanews.user.domain.valueobject.Username;

public class User {
	private final UUID		id;
	private Email			email;
	private Username		username;
	private Name			firstName;
	private Name			lastName;
	private Bio				bio;
	private String			avatarKey;
	private final Instant	createdAt;
	private Instant			updatedAt;

	private User(UUID id, Email email, Username username, Name firstName, Name lastName, Bio bio, String avatarKey,
			Instant createdAt, Instant updatedAt) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.bio = bio;
		this.avatarKey = avatarKey;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static User createUser(Email email, Username username, Name firstName, Name lastName, Bio bio, String avatarKey) {
		Instant now = Instant.now();
		return new User(UUID.randomUUID(), email, username, firstName, lastName, bio, avatarKey, now, now);
	}

	public static User reconstitute(UUID id, String email, String username, String firstName, String lastName, String bio, String avatarKey,
			Instant createdAt, Instant updatedAt) {
		return new User(id, Email.restore(email), Username.restore(username), Name.restore(firstName), Name.restore(lastName), Bio.restore(bio), avatarKey, createdAt, updatedAt);
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
}
