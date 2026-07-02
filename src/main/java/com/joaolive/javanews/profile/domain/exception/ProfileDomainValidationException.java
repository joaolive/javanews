package com.joaolive.javanews.profile.domain.exception;

import com.joaolive.javanews.common.BaseValidationException;

public class ProfileDomainValidationException extends BaseValidationException {
	public ProfileDomainValidationException(String message) {
		super(message);
	}
}
