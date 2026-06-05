package com.joaolive.javanews.post.infrastructure.web.response;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.valueobject.Slug;
import com.joaolive.javanews.post.domain.valueobject.Title;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record PostResponse(
	UUID id,
	UUID parentId,
	String type,
	String title,
	String slug,
	String body,
	UUID authorId,
	Instant createdAt,
	Instant updatedAt,
	Set<String> tags,
	List<PostResponse> children
) {
	public static PostResponse from(Post domain, List<PostResponse> children) {
		return new PostResponse(
			domain.getId(),
			domain.getParentId(),
			domain.getType().name(),
			domain.getTitle().map(Title::value).orElse(null),
			domain.getSlug().map(Slug::value).orElse(null),
			domain.getBody().value(),
			domain.getAuthorId(),
			domain.getCreatedAt(),
			domain.getUpdatedAt(),
			domain.getTags().stream().map(x -> x.value()).collect(Collectors.toSet()),
			children
		);
	}

	public static PostResponse from(Post domain) {
		return from(domain, List.of());
	}
}
