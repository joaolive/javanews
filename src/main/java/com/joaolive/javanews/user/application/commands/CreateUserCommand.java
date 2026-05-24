package com.joaolive.javanews.user.application.commands;

public record CreateUserCommand(
	String	username, 
	String	firstName, 
	String	lastName, 
	String	bio, 
	String	avatarKey
) {}
