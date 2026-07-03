package com.joaolive.javanews.user.domain.exception;

import com.joaolive.javanews.common.BaseForbiddenException;

public class VerificationCodeExpiredException extends BaseForbiddenException {
	public VerificationCodeExpiredException(String message) {
		super(message);
	}
}
