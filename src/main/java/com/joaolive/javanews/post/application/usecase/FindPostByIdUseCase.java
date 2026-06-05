package com.joaolive.javanews.post.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.PostRepository;
import com.joaolive.javanews.post.domain.exception.PostNotFoundException;

@Service
public class FindPostByIdUseCase {
	private final PostRepository postRepository;

	public FindPostByIdUseCase(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Transactional(readOnly = true)
	public Post execute(UUID id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException("Post not found"));
	}
}
