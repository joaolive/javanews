package com.joaolive.javanews.user.application.command;

public record UpdateProfileCommand(
	String	firstName,
	String	lastName,
	String	bio,
	String	avatarKey
) {}
