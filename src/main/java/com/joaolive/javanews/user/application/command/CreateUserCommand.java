package com.joaolive.javanews.user.application.command;

public record CreateUserCommand(
	String	email,
	String	username, 
	String	password,
	String	firstName, 
	String	lastName, 
	String	bio, 
	String	avatarKey
) {}
