package com.joaolive.javanews.user.domain.exception;

import com.joaolive.javanews.core.BaseConflictException;

public class UserConflictException extends BaseConflictException {
	public UserConflictException(String message) {
		super(message);
	}
}
