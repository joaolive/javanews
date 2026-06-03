package com.joaolive.javanews.core.exception;

public abstract class BaseValidationException extends RuntimeException {
	public BaseValidationException(String message) {
		super(message);
	}

}
