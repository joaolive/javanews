package com.joaolive.javanews.post.domain.exception;

import com.joaolive.javanews.core.exception.BaseNotFoundException;

public class PostNotFoundException extends BaseNotFoundException {
	public PostNotFoundException(String message) {
		super(message);
	}
}
