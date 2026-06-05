package com.joaolive.javanews.post.application.usecase;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.post.application.command.UpdatePostCommand;
import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.PostRepository;
import com.joaolive.javanews.post.domain.exception.PostForbiddenException;
import com.joaolive.javanews.post.domain.exception.PostNotFoundException;
import com.joaolive.javanews.post.domain.valueobject.Tag;

@Service
public class UpdatePostUseCase {
	private final PostRepository postRepository;

	public UpdatePostUseCase(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Transactional
	public Post execute(UUID postId, UUID requesterId, UpdatePostCommand command) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException("Post not found with ID " + postId));
		if (!post.getAuthorId().equals(requesterId))
			throw new PostForbiddenException("User is not authorized to edit this post");
		Set<Tag> tags = command.tags().stream()
			.map(x -> Tag.create(x))
			.collect(Collectors.toSet());
		post.updatePost(command.title(), command.body(), tags);
		return postRepository.save(post);
	}
}
