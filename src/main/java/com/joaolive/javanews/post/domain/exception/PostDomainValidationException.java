package com.joaolive.javanews.post.domain.exception;

import com.joaolive.javanews.core.exception.BaseValidationException;

public class PostDomainValidationException extends BaseValidationException {
	public PostDomainValidationException(String message) {
		super(message);
	}
}
