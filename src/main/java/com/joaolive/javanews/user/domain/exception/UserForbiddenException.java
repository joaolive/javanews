package com.joaolive.javanews.user.domain.exception;

import com.joaolive.javanews.core.BaseForbiddenException;

public class UserForbiddenException extends BaseForbiddenException {
	public UserForbiddenException(String message) {
		super(message);
	}
}
