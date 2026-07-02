package com.joaolive.javanews.post.domain.exception;

import com.joaolive.javanews.common.BaseForbiddenException;

public class PostForbiddenException extends BaseForbiddenException {
	public PostForbiddenException(String message) {
		super(message);
	}
}
