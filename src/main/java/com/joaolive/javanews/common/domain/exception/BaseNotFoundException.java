package com.joaolive.javanews.common.domain.exception;

public abstract class BaseNotFoundException extends RuntimeException {
	public BaseNotFoundException(String message) {
		super(message);
	}
}
