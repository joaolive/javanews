package com.joaolive.javanews.profile.domain;

import java.time.Instant;
import java.util.UUID;

import com.joaolive.javanews.profile.domain.valueobject.Bio;
import com.joaolive.javanews.profile.domain.valueobject.Name;
import com.joaolive.javanews.profile.domain.valueobject.Username;

public class Profile {
	private final UUID		userId;
	private Name			firstName;
	private Name			lastName;
	private Username		username;
	private Bio				bio;
	private String			avatarKey;
	private final Instant	createdAt;
	private Instant			updatedAt;

	private Profile(UUID userId, Name firstName, Name lastName, Username username, Bio bio, String avatarKey, Instant createdAt, Instant updatedAt) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.bio = bio;
		this.avatarKey = avatarKey;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static Profile create(UUID userId, Name firstName, Name lastName, Username username, Bio bio, String avatarKey) {
		Instant now = Instant.now();
		return new Profile(userId, firstName, lastName, username, bio, avatarKey, now, now);
	}

	public static Profile reconstitute(UUID userId, String firstName, String lastName, String username, String bio, String avatarKey, Instant createdAt, Instant updatedAt) {
		return new Profile(userId, Name.restore(firstName), Name.restore(lastName), Username.restore(username), Bio.restore(bio), avatarKey, createdAt, updatedAt);
	}

	public UUID getUserId() {
		return userId;
	}

	public Name getFirstName() {
		return firstName;
	}

	public Name getLastName() {
		return lastName;
	}

	public Username getUsername() {
		return username;
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

	public void update(Name firstName, Name lastName, Bio bio, String avatarKey) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.bio = bio;
		this.avatarKey = avatarKey;
		updatedAt = Instant.now();
	}

	public void updateUsername(Username username) {
		this.username = username;
		updatedAt = Instant.now();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profile other = (Profile) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}