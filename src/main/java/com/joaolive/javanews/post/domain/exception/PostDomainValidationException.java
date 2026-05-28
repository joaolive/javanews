package com.joaolive.javanews.post.domain.exception;

public class PostDomainValidationException extends RuntimeException {
	public PostDomainValidationException(String msg) {
		super(msg);
	}
}
