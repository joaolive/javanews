package com.joaolive.javanews.user.domain.exception;

import com.joaolive.javanews.common.BaseForbiddenException;

public class MaxVerificationAttemptsExceededException extends BaseForbiddenException {
	public MaxVerificationAttemptsExceededException(String message) {
		super(message);
	}
}
