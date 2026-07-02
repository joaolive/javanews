package com.joaolive.javanews.post.infrastructure.persistence;

import java.util.UUID;

import com.joaolive.javanews.common.Identifiable;

public interface PostIdProjection extends Identifiable<UUID> {
	UUID getId();
}
