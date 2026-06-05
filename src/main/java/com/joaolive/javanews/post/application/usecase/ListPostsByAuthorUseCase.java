package com.joaolive.javanews.post.application.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.core.PageResult;
import com.joaolive.javanews.core.PaginationRequest;
import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.PostRepository;
import com.joaolive.javanews.post.domain.exception.PostNotFoundException;
import com.joaolive.javanews.user.UserInternalApi;

@Service
public class ListPostsByAuthorUseCase {
	private final PostRepository postRepository;
	private final UserInternalApi userInternalApi;

	public ListPostsByAuthorUseCase(PostRepository postRepository, UserInternalApi userInternalApi) {
		this.postRepository = postRepository;
		this.userInternalApi = userInternalApi;
	}

	@Transactional(readOnly = true)
	public PageResult<Post> execute(String username, PaginationRequest request) {
		UUID authorId = userInternalApi.findIdByUsername(username)
			.orElseThrow(() -> new PostNotFoundException("Author not found"));
		String safeSortBy = request.sortBy()
			.equalsIgnoreCase("title")
				? "title" : "createdAt";
		PaginationRequest safeRequest = new PaginationRequest(
			request.page(),
			request.size(),
			safeSortBy,
			request.direction()
		);
		return postRepository.findByAuthorId(authorId, safeRequest);
	}
}
