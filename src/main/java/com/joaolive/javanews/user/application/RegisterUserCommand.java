package com.joaolive.javanews.user.application;

public record RegisterUserCommand(
	String firstName,
	String lastName,
	String username,
	String email,
	String password
) {}
