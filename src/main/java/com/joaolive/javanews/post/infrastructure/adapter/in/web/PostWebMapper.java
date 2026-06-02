package com.joaolive.javanews.post.infrastructure.adapter.in.web;

import java.util.stream.Collectors;

import com.joaolive.javanews.post.domain.model.Post;
import com.joaolive.javanews.post.infrastructure.adapter.in.web.response.PostResponse;

public class PostWebMapper {
	public static PostResponse toResponse(Post domain) {
		if (domain == null)
			return null;
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
