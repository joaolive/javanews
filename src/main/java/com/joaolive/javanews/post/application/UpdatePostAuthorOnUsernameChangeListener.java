package com.joaolive.javanews.post.application;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.joaolive.javanews.post.domain.PostRepository;
import com.joaolive.javanews.user.UserUsernameChangeEvent;

@Component
public class UpdatePostAuthorOnUsernameChangeListener {
	private final PostRepository postRepository;

	public UpdatePostAuthorOnUsernameChangeListener(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@ApplicationModuleListener
	void on(UserUsernameChangeEvent event) {
		postRepository.updateAuthorUsername(event.id(), event.username());
	}
}
