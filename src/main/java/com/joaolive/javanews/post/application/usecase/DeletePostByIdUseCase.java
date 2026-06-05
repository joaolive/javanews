package com.joaolive.javanews.post.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.post.application.command.DeletePostCommand;
import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.PostRepository;
import com.joaolive.javanews.post.domain.exception.PostForbiddenException;
import com.joaolive.javanews.post.domain.exception.PostNotFoundException;

@Service
public class DeletePostByIdUseCase {
	private final PostRepository postRepository;

	public DeletePostByIdUseCase(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Transactional
	public void execute(DeletePostCommand command) {
		Post post = postRepository.findById(command.postId())
			.orElseThrow(() -> new PostNotFoundException("Post not found"));
		if (!post.getAuthorId().equals(command.authorId()))
			throw new PostForbiddenException("User is not authorized to delete this post");
		postRepository.delete(command.postId());
	}
}
