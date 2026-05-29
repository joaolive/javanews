package com.joaolive.javanews.post.domain.exception;

public class PostNotFoundException extends RuntimeException {
	public PostNotFoundException(String msg) {
		super(msg);
	}
}
