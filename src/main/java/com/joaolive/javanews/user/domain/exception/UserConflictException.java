package com.joaolive.javanews.user.domain.exception;

import com.joaolive.javanews.common.BaseConflictException;

public class UserConflictException extends BaseConflictException {
	public UserConflictException(String message) {
		super(message);
	}
}
