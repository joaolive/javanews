package com.joaolive.javanews.post.application;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.joaolive.javanews.post.infrastructure.persistence.PostJpaRepository;
import com.joaolive.javanews.user.UserUsernameChangeEvent;

@Component
public class UpdatePostAuthorOnUsernameChangeListener {
	private final PostJpaRepository postJpaRepository;

	public UpdatePostAuthorOnUsernameChangeListener(PostJpaRepository postJpaRepository) {
		this.postJpaRepository = postJpaRepository;
	}

	@ApplicationModuleListener
	void on(UserUsernameChangeEvent event) {
		postJpaRepository.updateAuthorUsername(event.id(), event.username());
	}
}
