package com.joaolive.javanews.common;

public abstract class BaseValidationException extends RuntimeException {
	public BaseValidationException(String message) {
		super(message);
	}
}
