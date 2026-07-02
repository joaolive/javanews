package com.joaolive.javanews.profile.application;

public record UpdateProfileCommand(
	String	firstName,
	String	lastName,
	String	bio,
	String	avatarKey
) {}
