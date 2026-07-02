package com.joaolive.javanews.profile;

import java.util.Optional;
import java.util.UUID;

public interface ProfileInternalApi {
	Optional<UUID> findIdByUsername(String username);
}
