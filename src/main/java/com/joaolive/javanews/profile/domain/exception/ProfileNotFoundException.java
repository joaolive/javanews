package com.joaolive.javanews.profile.domain.exception;

import com.joaolive.javanews.common.BaseNotFoundException;

public class ProfileNotFoundException extends BaseNotFoundException {
	public ProfileNotFoundException(String message) {
		super(message);
	}
}
