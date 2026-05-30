package com.joaolive.javanews.post.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.post.application.command.DeletePostCommand;
import com.joaolive.javanews.post.application.port.out.PostRepositoryPort;
import com.joaolive.javanews.post.domain.exception.PostDomainValidationException;
import com.joaolive.javanews.post.domain.exception.PostNotFoundException;
import com.joaolive.javanews.post.domain.model.Post;

@Service
public class DeletePostByIdUseCase {
	private final PostRepositoryPort postRepository;

	public DeletePostByIdUseCase(PostRepositoryPort postRepository) {
		this.postRepository = postRepository;
	}

	@Transactional
	public void execute(DeletePostCommand command) {
		Post post = postRepository.findById(command.postId())
			.orElseThrow(() -> new PostNotFoundException("Post not found with ID " + command.authorId()));
		if (!post.getAuthorId().equals(command.authorId()))
			throw new PostDomainValidationException("User is not authorized to delete this post");
		postRepository.delete(command.postId());
	}
}
