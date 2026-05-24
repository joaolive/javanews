package com.joaolive.javanews.user.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String msg) {
		super(msg);
	}
}
