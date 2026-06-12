package com.joaolive.javanews.post.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.joaolive.javanews.post.domain.valueobject.PostType;

public interface PostJpaRepository extends JpaRepository<PostEntity, UUID> {
	@Query("SELECT p.id as id FROM PostEntity p WHERE p.type = :type")
	Page<PostIdProjection> findPagedIds(@Param("type") PostType type, Pageable pageable);

	@Query("SELECT p.id as id FROM PostEntity p WHERE p.authorId = :authorId")
	Page<PostIdProjection> findPagedIdsByAuthorId(@Param("authorId") UUID authorId, Pageable pageable);

	@Query("SELECT DISTINCT p FROM PostEntity p LEFT JOIN FETCH p.tags WHERE p.id IN :ids")
	List<PostEntity> findWithTagsByIds(@Param("ids") List<UUID> ids);

	@EntityGraph(attributePaths = {"tags"})
	Optional<PostEntity> findByAuthorIdAndSlugAndType(UUID authorId, String slug, PostType type);

	@Modifying
	@Query("UPDATE PostEntity p SET p.authorUsername = :newUsername WHERE p.authorId = :authorId")
	void updateAuthorUsername(@Param("authorId") UUID authorId, @Param("newUsername") String newUsername);
}
