package com.joaolive.javanews.auth.web;

import com.joaolive.javanews.common.BaseUnauthorizedException;

public class InvalidUserIdentityException extends BaseUnauthorizedException {
	public InvalidUserIdentityException(String message) {
		super(message);
	}
}
