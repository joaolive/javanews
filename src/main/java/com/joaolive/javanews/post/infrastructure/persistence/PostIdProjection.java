package com.joaolive.javanews.post.infrastructure.persistence;

import java.util.UUID;

import com.joaolive.javanews.core.Identifiable;

public interface PostIdProjection extends Identifiable<UUID> {
	UUID getId();
}
