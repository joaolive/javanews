package com.joaolive.javanews.post.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.joaolive.javanews.post.domain.model.Post;

public interface PostRepositoryPort {
	Post save(Post post);
	Optional<Post> findById(UUID id);
	List<Post> findPostsByAuthorId(UUID id);
	void delete(UUID id);
}
