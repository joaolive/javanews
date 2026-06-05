package com.joaolive.javanews.post.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.post.application.command.CreateCommentCommand;
import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.PostRepository;
import com.joaolive.javanews.post.domain.exception.PostNotFoundException;
import com.joaolive.javanews.post.domain.valueobject.Body;

@Service
public class CreateCommentUseCase {
	private final PostRepository postRepository;

	public CreateCommentUseCase(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Transactional
	public Post execute(CreateCommentCommand command) {
		postRepository.findById(command.parentId())
			.orElseThrow(() -> new PostNotFoundException("Post not found"));
		Post comment = Post.createComment(command.parentId(), Body.create(command.body()), command.authorId());
		return postRepository.save(comment);
	}
}
