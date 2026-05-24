package com.joaolive.javanews.user.domain;

import java.time.Instant;
import java.util.UUID;

import com.joaolive.javanews.user.records.Bio;
import com.joaolive.javanews.user.records.Name;
import com.joaolive.javanews.user.records.Username;

public class User {
	private final UUID		id;
	private Username		username;
	private Name			firstName;
	private Name			lastName;
	private Bio				bio;
	private String			avatarKey;
	private final Instant	createdAt;
	private Instant			updatedAt;

	private User(UUID id, Username username, Name firstName, Name lastName, Bio bio, String avatarKey,
			Instant createdAt, Instant updatedAt) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.bio = bio;
		this.avatarKey = avatarKey;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static User createUser(Username username, Name firstName, Name lastName, Bio bio, String avatarKey) {
		Instant now = Instant.now();
		return new User(UUID.randomUUID(), username, firstName, lastName, bio, avatarKey, now, now);
	}

	public static User reconstitute(UUID id, String username, String firstName, String lastName, String bio, String avatarKey,
			Instant createdAt, Instant updatedAt) {
		return new User(id, new Username(username), new Name(firstName), new Name(lastName), new Bio(bio), avatarKey, createdAt, updatedAt);
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
