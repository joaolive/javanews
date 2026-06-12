package com.joaolive.javanews.post.domain;

import java.util.Optional;
import java.util.UUID;

import com.joaolive.javanews.core.PageResult;
import com.joaolive.javanews.core.PaginationRequest;

public interface PostRepository {
	<T extends Post>T save(T post);
	Optional<Post> findById(UUID id);
	PageResult<Article> findAllArticles(PaginationRequest request);
	PageResult<Post> findByAuthorId(UUID id, PaginationRequest request);
	void delete(UUID id);
	Optional<Article> findByAuthorIdAndSlug(UUID authorId, String slug);
}
