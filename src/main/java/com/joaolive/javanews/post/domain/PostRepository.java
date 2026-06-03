package com.joaolive.javanews.post.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository {
	Post save(Post post);
	Optional<Post> findById(UUID id);
	List<Post> findPostsByAuthorId(UUID id);
	void delete(UUID id);
}
