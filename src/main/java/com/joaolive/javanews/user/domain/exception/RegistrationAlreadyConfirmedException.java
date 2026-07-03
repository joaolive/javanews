package com.joaolive.javanews.user.domain.exception;

import com.joaolive.javanews.common.BaseConflictException;

public class RegistrationAlreadyConfirmedException extends BaseConflictException {
	public RegistrationAlreadyConfirmedException(String message) {
		super(message);
	}
}
