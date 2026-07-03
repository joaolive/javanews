package com.joaolive.javanews.user.domain.exception;

import com.joaolive.javanews.common.BaseValidationException;

public class InvalidVerificationCodeException extends BaseValidationException {
	public InvalidVerificationCodeException(String message) {
		super(message);
	}
}
