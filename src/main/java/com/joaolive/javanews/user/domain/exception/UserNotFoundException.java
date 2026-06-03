package com.joaolive.javanews.user.domain.exception;

import com.joaolive.javanews.common.domain.exception.BaseNotFoundException;

public class UserNotFoundException extends BaseNotFoundException {
	public UserNotFoundException(String message) {
		super(message);
	}
}
