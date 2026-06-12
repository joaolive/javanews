package com.joaolive.javanews.core;

public abstract class BaseUnauthorizedException extends RuntimeException {
	public BaseUnauthorizedException(String message) {
		super(message);
	}
	
}
