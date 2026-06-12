package com.joaolive.javanews.core.web;

import com.joaolive.javanews.core.BaseUnauthorizedException;

public class InvalidUserIdentityException extends BaseUnauthorizedException {
    public InvalidUserIdentityException(String message) {
        super(message);
    }
}
