package com.joaolive.javanews.post.application;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.post.PostCreatedEvent;
import com.joaolive.javanews.post.application.command.CreateCommentCommand;
import com.joaolive.javanews.post.application.command.CreatePostCommand;
import com.joaolive.javanews.post.application.command.DeletePostCommand;
import com.joaolive.javanews.post.application.command.UpdatePostCommand;
import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.PostRepository;
import com.joaolive.javanews.post.domain.exception.PostForbiddenException;
import com.joaolive.javanews.post.domain.exception.PostNotFoundException;
import com.joaolive.javanews.post.domain.valueobject.Body;
import com.joaolive.javanews.post.domain.valueobject.Slug;
import com.joaolive.javanews.post.domain.valueobject.Tag;
import com.joaolive.javanews.post.domain.valueobject.Title;

@Service
public class PostService {
	private final PostRepository postRepository;
	private final ApplicationEventPublisher eventPublisher;

	public PostService(PostRepository postRepository, ApplicationEventPublisher eventPublisher) {
		this.postRepository = postRepository;
		this.eventPublisher = eventPublisher;
	}

	@Transactional
	public Post createArticle(CreatePostCommand command) {
		Title title = Title.create(command.title());
		Slug slug = Slug.create(command.title());
		Body body = Body.create(command.body());
		Set<Tag> tags = command.tags().stream()
				.map(x -> Tag.create(x))
				.collect(Collectors.toSet());
		Post savedPost =  postRepository.save(Post.createArticle(
			title, slug, body, command.authorId(), tags
		));
		eventPublisher.publishEvent(new PostCreatedEvent(
			savedPost.getId(),
			savedPost.getAuthorId(),
			savedPost.getTags().stream()
				.map(x -> x.value())
				.collect(Collectors.toSet())
		));
		return savedPost;
	}

	@Transactional
	public Post createComment(CreateCommentCommand command) {
		postRepository.findById(command.parentId())
			.orElseThrow(() -> new PostNotFoundException("Post not found"));
		Post comment = Post.createComment(command.parentId(), Body.create(command.body()), command.authorId());
		return postRepository.save(comment);
	}

	@Transactional
	public Post updateArticle(UUID articleId, UUID requesterId, UpdatePostCommand command) {
		Post post = postRepository.findById(articleId)
			.orElseThrow(() -> new PostNotFoundException("Post not found"));
		if (!post.getAuthorId().equals(requesterId))
			throw new PostForbiddenException("User is not authorized to edit this post");
		Set<Tag> tags = command.tags().stream()
			.map(x -> Tag.create(x))
			.collect(Collectors.toSet());
		post.updateArticle(command.title(), command.body(), tags);
		return postRepository.save(post);
	}

	@Transactional
	public void delete(DeletePostCommand command) {
		Post post = postRepository.findById(command.postId())
			.orElseThrow(() -> new PostNotFoundException("Post not found"));
		if (!post.getAuthorId().equals(command.authorId()))
			throw new PostForbiddenException("User is not authorized to delete this post");
		postRepository.delete(command.postId());
	}
}
