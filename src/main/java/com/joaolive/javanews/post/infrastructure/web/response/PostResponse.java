package com.joaolive.javanews.post.infrastructure.web.response;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.joaolive.javanews.post.domain.Post;

public record PostResponse(
	UUID id,
	String title,
	String slug,
	String body,
	UUID authorId,
	Instant createdAt,
	Instant updatedAt,
	Set<String> tags
) {
	public static PostResponse from(Post domain) {
		return new PostResponse(
			domain.getId(),
			domain.getTitle().value(),
			domain.getSlug().value(),
			domain.getBody().value(),
			domain.getAuthorId(),
			domain.getCreatedAt(),
			domain.getUpdatedAt(),
			domain.getTags().stream().map(x -> x.value()).collect(Collectors.toSet())
		);
	}
}
