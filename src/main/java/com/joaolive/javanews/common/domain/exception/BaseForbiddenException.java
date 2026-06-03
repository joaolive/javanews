package com.joaolive.javanews.common.domain.exception;

public abstract class BaseForbiddenException extends RuntimeException {
	public BaseForbiddenException(String message) {
		super(message);
	}

}
