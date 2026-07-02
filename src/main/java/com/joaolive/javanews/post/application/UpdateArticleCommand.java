package com.joaolive.javanews.post.application;

import java.util.Set;

public record UpdateArticleCommand(
	String title,
	String body,
	Set<String> tags
) {}
