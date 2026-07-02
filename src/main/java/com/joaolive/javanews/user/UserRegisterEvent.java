package com.joaolive.javanews.user;

import java.util.UUID;

public record UserRegisterEvent(
	UUID userId,
	String username,
	String firstName,
	String lastName
) {}
