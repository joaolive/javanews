package com.joaolive.javanews.post.application.command;

import java.util.Set;
import java.util.UUID;

public record CreatePostCommand(
	String		title,
	String		body,
	UUID		authorId,
	Set<String>	tags
) {}
