package com.joaolive.javanews.post.infrastructure.web;

import java.util.Set;
import java.util.UUID;

import com.joaolive.javanews.post.application.command.CreateArticleCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateArticleRequest(
	@NotBlank(message = "Title cannot be null or blank")
	@Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
	String title,
	@NotBlank(message = "Body cannot be null or blank")
	@Size(min = 1, max = 20000, message = "Body must be between 1 and 20000 characters")
	String body,
	Set<String> tags
) {
	public CreateArticleCommand toCommand(UUID authorId) {
		return new CreateArticleCommand(
			this.title(),
			this.body(),
			authorId,
			this.tags()
		);
	}
}
