package com.joaolive.javanews.post.infrastructure.web;

import java.util.Set;

import com.joaolive.javanews.post.application.command.UpdateArticleCommand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateArticleRequest(
	@NotBlank(message = "Title cannot be null or blank")
	@Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
	String title,
	@NotBlank(message = "Body cannot be null or blank")
	@Size(min = 1, max = 20000, message = "Body must be between 1 and 20000 characters")
	String body,
	@NotNull
	@Size(min = 1, max = 5, message = "A post must have between 1 and 5 tags")
	Set<
	    @NotBlank(message = "Tag cannot be null or blank")
    	@Size(min = 1, max = 32, message = "Tag must be between 1 and 32 characters")
		String
	> tags
) {
	public UpdateArticleCommand toCommand() {
		return new UpdateArticleCommand(
			this.title(),
			this.body(),
			this.tags()
		);
	}
}
