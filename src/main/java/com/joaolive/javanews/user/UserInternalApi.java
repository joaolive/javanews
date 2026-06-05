package com.joaolive.javanews.user;

import java.util.Optional;
import java.util.UUID;

public interface UserInternalApi {
	Optional<UUID> findIdByUsername(String username);
}
