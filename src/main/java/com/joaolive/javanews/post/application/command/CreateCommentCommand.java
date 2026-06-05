package com.joaolive.javanews.post.application.command;

import java.util.UUID;

public record CreateCommentCommand(
	UUID parentId,
	UUID authorId,
	String body
) {}
