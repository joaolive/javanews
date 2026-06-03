package com.joaolive.javanews.core;

public abstract class BaseNotFoundException extends RuntimeException {
	public BaseNotFoundException(String message) {
		super(message);
	}
}
