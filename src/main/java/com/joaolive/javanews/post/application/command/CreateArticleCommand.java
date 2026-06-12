package com.joaolive.javanews.post.application.command;

import java.util.Set;
import java.util.UUID;

public record CreateArticleCommand(
	String		title,
	String		body,
	UUID		authorId,
	String		author,
	Set<String>	tags
) {}
