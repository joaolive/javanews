package com.joaolive.javanews.common;

public abstract class BaseUnauthorizedException extends RuntimeException {
	public BaseUnauthorizedException(String message) {
		super(message);
	}
	
}
