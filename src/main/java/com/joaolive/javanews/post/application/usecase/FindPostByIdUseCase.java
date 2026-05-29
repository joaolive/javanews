package com.joaolive.javanews.post.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.post.application.port.out.PostRepositoryPort;
import com.joaolive.javanews.post.domain.exception.PostNotFoundException;
import com.joaolive.javanews.post.domain.model.Post;

@Service
public class FindPostByIdUseCase {
	private final PostRepositoryPort postRepository;

	public FindPostByIdUseCase(PostRepositoryPort postRepository) {
		this.postRepository = postRepository;
	}

	@Transactional(readOnly = true)
	public Post execute(UUID id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException("Post with ID '" + id + "' not found"));
	}
}
