package com.joaolive.javanews.user.domain;

public record RegistrationPayload(
	String firstName,
	String lastName,
	String username,
	String password
) {}
