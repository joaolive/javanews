package com.joaolive.javanews.post.domain.exception;

import com.joaolive.javanews.core.BaseConflictException;

public class PostConflictException extends BaseConflictException {
	public PostConflictException(String message) {
		super(message);
	}
}
