package com.joaolive.javanews.post.application;

import java.util.UUID;

public record CreateCommentCommand(
	UUID	parentId,
	UUID	authorId,
	String	author,
	String	body
) {}
