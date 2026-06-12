package com.joaolive.javanews.post.domain;

import java.time.Instant;
import java.util.UUID;


import com.joaolive.javanews.post.domain.valueobject.Body;
import com.joaolive.javanews.post.domain.valueobject.PostType;

public final class Comment extends Post {
	private final UUID	parentId;

	private Comment(UUID id, Body body, UUID authorId, Instant createdAt, Instant updatedAt, UUID parentId) {
		super(id, body, authorId, createdAt, updatedAt);
		this.parentId = parentId;
	}

	public static Comment create(Body body, UUID authorId, UUID parentId) {
		Instant now = Instant.now();
		return new Comment(UUID.randomUUID(), body, authorId, now, now, parentId);
	}

	public static Comment reconstitute(UUID id, String body, UUID authorId, Instant createdAt, Instant updatedAt, UUID parentId) {
		return new Comment(id, Body.restore(body), authorId, createdAt, updatedAt, parentId);
	}

	public void update(Body body) {
		this.body = body;
		this.updatedAt = Instant.now();
	}

	public UUID getParentId() {
		return parentId;
	}

	@Override
	public PostType getType() {
		return PostType.ARTICLE;
	}

}
