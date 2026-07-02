package com.joaolive.javanews.common;

public abstract class BaseNotFoundException extends RuntimeException {
	public BaseNotFoundException(String message) {
		super(message);
	}
}
