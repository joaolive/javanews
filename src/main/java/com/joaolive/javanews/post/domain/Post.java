package com.joaolive.javanews.post.domain;

import java.time.Instant;
import java.util.UUID;

import com.joaolive.javanews.post.domain.valueobject.Body;
import com.joaolive.javanews.post.domain.valueobject.PostType;

public abstract sealed class Post permits Article, Comment {
	protected final UUID	id;
	protected Body			body;
	protected UUID			authorId;
	protected final Instant	createdAt;
	protected Instant		updatedAt;
	
	protected Post(UUID id, Body body, UUID authorId, Instant createdAt, Instant updatedAt) {
		this.id = id;
		this.body = body;
		this.authorId = authorId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UUID getId() {
		return id;
	}

	public Body getBody() {
		return body;
	}

	public UUID getAuthorId() {
		return authorId;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public abstract PostType getType();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Post other = (Post) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
