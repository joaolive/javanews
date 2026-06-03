package com.joaolive.javanews.common.domain.exception;

public abstract class BaseValidationException extends RuntimeException {
	public BaseValidationException(String message) {
		super(message);
	}

}
