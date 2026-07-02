package com.joaolive.javanews.profile.domain.exception;

import com.joaolive.javanews.common.BaseConflictException;

public class ProfileConflictException extends BaseConflictException{
	public ProfileConflictException(String message) {
		super(message);
	}
}
