package com.joaolive.javanews.post.application;

import java.util.UUID;

import com.joaolive.javanews.post.domain.exception.PostDomainValidationException;

public record DeletePostCommand(UUID postId, UUID authorId) {
	public DeletePostCommand {
		if (postId == null || authorId == null)
			throw new PostDomainValidationException("All args are required");
	}
}
