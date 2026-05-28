package com.joaolive.javanews.user.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {
	public UserAlreadyExistsException(String msg) {
		super(msg);
	}
}
