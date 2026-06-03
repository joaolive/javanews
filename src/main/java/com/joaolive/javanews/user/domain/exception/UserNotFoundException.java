package com.joaolive.javanews.user.domain.exception;

import com.joaolive.javanews.core.BaseNotFoundException;

public class UserNotFoundException extends BaseNotFoundException {
	public UserNotFoundException(String message) {
		super(message);
	}
}
