package com.joaolive.javanews.post.infrastructure.adapter.in.web.response;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record PostResponse(
	UUID id,
	String title,
	String slug,
	String body,
	UUID authorId,
	Instant createdAt,
	Instant updatedAt,
	Set<String> tags
) {}
