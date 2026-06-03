package com.joaolive.javanews.post.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.core.PageResult;
import com.joaolive.javanews.core.PaginationRequest;
import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.PostRepository;

@Service
public class ListPostsUseCase {
	private final PostRepository postRepository;

	public ListPostsUseCase(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Transactional(readOnly = true)
	public PageResult<Post> execute(PaginationRequest request) {
		String safeSortBy = request.sortBy()
			.equalsIgnoreCase("title")
				? "title" : "createdAt";
		PaginationRequest safeRequest = new PaginationRequest(
			request.page(),
			request.size(),
			safeSortBy,
			request.direction()
		);
		return postRepository.findAll(safeRequest);
	}
}
