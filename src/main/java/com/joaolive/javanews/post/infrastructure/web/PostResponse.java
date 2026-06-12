package com.joaolive.javanews.post.infrastructure.web;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.joaolive.javanews.post.domain.Article;
import com.joaolive.javanews.post.domain.Comment;
import com.joaolive.javanews.post.domain.Post;

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
		return switch (domain) {
			case Article article -> new PostResponse(
				article.getId(),
				null,
				article.getType().name(),
				article.getTitle().value(),
				article.getSlug().value(),
				article.getBody().value(),
				article.getAuthorId(),
				article.getCreatedAt(),
				article.getUpdatedAt(),
				article.getTags().stream().map(x -> x.value()).collect(Collectors.toSet()),
				children
			);
			case Comment comment -> new PostResponse(
				comment.getId(),
				comment.getParentId(),
				comment.getType().name(),
				null,
				null,
				comment.getBody().value(),
				comment.getAuthorId(),
				comment.getCreatedAt(),
				comment.getUpdatedAt(),
				Set.of(),
				children
			);
		};
	}

	public static PostResponse from(Post domain) {
		return from(domain, List.of());
	}
}
