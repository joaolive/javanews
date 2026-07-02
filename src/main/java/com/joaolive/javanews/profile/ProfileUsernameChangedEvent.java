package com.joaolive.javanews.profile;

import java.util.UUID;

public record ProfileUsernameChangedEvent(UUID id, String username) {}
