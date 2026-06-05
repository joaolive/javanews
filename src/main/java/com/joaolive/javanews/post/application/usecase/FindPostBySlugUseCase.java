package com.joaolive.javanews.post.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.PostRepository;
import com.joaolive.javanews.post.domain.exception.PostNotFoundException;
import com.joaolive.javanews.user.UserInternalApi;

@Service
public class FindPostBySlugUseCase {
	private final PostRepository postRepository;
	private final UserInternalApi userInternalApi;

	public FindPostBySlugUseCase(PostRepository postRepository, UserInternalApi userInternalApi) {
		this.postRepository = postRepository;
		this.userInternalApi = userInternalApi;
	}

	@Transactional(readOnly = true)
	public Post execute(String username, String slug) {
		UUID authorId = userInternalApi.findIdByUsername(username)
			.orElseThrow(() -> new PostNotFoundException("Post not found"));
		return postRepository.findByAuthorIdAndSlug(authorId, slug)
			.orElseThrow(() -> new PostNotFoundException("Post not found"));
	}
}
