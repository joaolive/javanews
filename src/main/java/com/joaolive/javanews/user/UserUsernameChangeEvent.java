package com.joaolive.javanews.user;

import java.util.UUID;

public record UserUsernameChangeEvent(UUID id, String username) {	
}
