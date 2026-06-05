package com.joaolive.javanews.post;

import java.util.Set;
import java.util.UUID;

public record PostCreatedEvent(
	UUID postId,
	UUID authorId,
	Set<String> tags
) {}
