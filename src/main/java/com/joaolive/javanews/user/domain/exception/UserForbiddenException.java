package com.joaolive.javanews.user.domain.exception;

import com.joaolive.javanews.common.domain.exception.BaseForbiddenException;

public class UserForbiddenException extends BaseForbiddenException {
	public UserForbiddenException(String message) {
		super(message);
	}
}
