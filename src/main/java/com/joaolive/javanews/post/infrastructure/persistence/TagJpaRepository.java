package com.joaolive.javanews.post.infrastructure.persistence;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagJpaRepository extends JpaRepository<TagEntity, UUID> {
	Set<TagEntity> findByNameIn(Set<String> names);
}
