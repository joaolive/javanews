package com.joaolive.javanews.post.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.joaolive.javanews.core.PageResult;
import com.joaolive.javanews.core.PaginationRequest;

public interface PostRepository {
	Post save(Post post);
	Optional<Post> findById(UUID id);
	PageResult<Post> findAll(PaginationRequest request);
	List<Post> findPostsByAuthorId(UUID id);
	void delete(UUID id);
}
