package com.joaolive.javanews.user;

public record RegistrationInitiatedEvent(
	String email,
	String verificationCode
) {}
