package com.joaolive.javanews.user.domain.exception;

public class DomainValidationException extends RuntimeException {
	public DomainValidationException(String msg) {
		super(msg);
	}
}
