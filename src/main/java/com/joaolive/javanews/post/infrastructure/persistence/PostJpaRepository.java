package com.joaolive.javanews.post.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostJpaRepository extends JpaRepository<PostEntity, UUID> {
	@Query("SELECT p.id as id FROM PostEntity p")
	Page<PostIdProjection> findPagedIds(Pageable pageable);

	@Query("SELECT DISTINCT p FROM PostEntity p LEFT JOIN FETCH p.tags WHERE p.id IN :ids")
	List<PostEntity> findWithTagsByIds(@Param("ids") List<UUID> ids);

	@EntityGraph(attributePaths = {"tags"})
    Optional<PostEntity> findByAuthorIdAndSlug(UUID authorId, String slug);
}
