package com.joaolive.javanews.post.domain.exception;

import com.joaolive.javanews.core.BaseNotFoundException;

public class PostNotFoundException extends BaseNotFoundException {
	public PostNotFoundException(String message) {
		super(message);
	}
}
