package com.joaolive.javanews.user.domain.exception;

import com.joaolive.javanews.core.BaseValidationException;

public class UserDomainValidationException extends BaseValidationException {
	public UserDomainValidationException(String message) {
		super(message);
	}
}
