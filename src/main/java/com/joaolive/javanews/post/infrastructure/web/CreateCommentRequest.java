package com.joaolive.javanews.post.infrastructure.web;

import java.util.UUID;

import com.joaolive.javanews.post.application.command.CreateCommentCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCommentRequest(
	@NotBlank(message = "Body cannot be null or blank")
	@Size(min = 1, max = 20000, message = "Body must be between 1 and 20000 characters")
	String body
) {
	public CreateCommentCommand toCommand(UUID parentId, UUID authorId, String author) {
		return new CreateCommentCommand(
			parentId,
			authorId,
			author,
			this.body
		);
	}
}
