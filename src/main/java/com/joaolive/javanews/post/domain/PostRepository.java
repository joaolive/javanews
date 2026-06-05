package com.joaolive.javanews.post.domain;

import java.util.Optional;
import java.util.UUID;

import com.joaolive.javanews.core.PageResult;
import com.joaolive.javanews.core.PaginationRequest;

public interface PostRepository {
	Post save(Post post);
	Optional<Post> findById(UUID id);
	PageResult<Post> findAll(PaginationRequest request);
	PageResult<Post> findByAuthorId(UUID id, PaginationRequest request);
	void delete(UUID id);
	Optional<Post> findByAuthorIdAndSlug(UUID authorId, String slug);
}
