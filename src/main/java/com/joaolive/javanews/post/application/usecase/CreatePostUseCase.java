package com.joaolive.javanews.post.application.usecase;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.post.PostCreatedEvent;
import com.joaolive.javanews.post.application.command.CreatePostCommand;
import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.PostRepository;
import com.joaolive.javanews.post.domain.valueobject.Body;
import com.joaolive.javanews.post.domain.valueobject.Slug;
import com.joaolive.javanews.post.domain.valueobject.Tag;
import com.joaolive.javanews.post.domain.valueobject.Title;

@Service
public class CreatePostUseCase {
	private final PostRepository postRepository;
	private final ApplicationEventPublisher eventPublisher;

	public CreatePostUseCase(PostRepository postRepository,  ApplicationEventPublisher eventPublisher) {
		this.postRepository = postRepository;
		this.eventPublisher = eventPublisher;
	}

	@Transactional
	public Post execute(CreatePostCommand command) {
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
}
