package com.joaolive.javanews.post.application;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.core.PageResult;
import com.joaolive.javanews.core.PaginationRequest;
import com.joaolive.javanews.post.domain.Article;
import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.PostRepository;
import com.joaolive.javanews.post.domain.exception.PostNotFoundException;
import com.joaolive.javanews.user.UserInternalApi;

@Service
public class PostQueryService {
	private final PostRepository postRepository;
	private final UserInternalApi userInternalApi;

	public PostQueryService(PostRepository postRepository, UserInternalApi userInternalApi) {
		this.postRepository = postRepository;
		this.userInternalApi = userInternalApi;
	}

	@Transactional(readOnly = true)
	public Post findById(UUID id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new PostNotFoundException("Post not found"));
	}

	@Transactional(readOnly = true)
	public PageResult<Article> findAllArticles(PaginationRequest request) {
		String safeSortBy = request.sortBy().equalsIgnoreCase("title") ? "title" : "createdAt";
		return postRepository.findAllArticles(new PaginationRequest(
			request.page(),
			request.size(),
			safeSortBy,
			request.direction()));
	}

	@Transactional(readOnly = true)
	public PageResult<Post> findPostsByAuthor(String username, PaginationRequest request) {
		UUID authorId = userInternalApi.findIdByUsername(username)
			.orElseThrow(() -> new PostNotFoundException("Author not found"));
		PaginationRequest safeRequest = new PaginationRequest(
			request.page(),
			request.size(),
			request.sortBy().equalsIgnoreCase("updatedAt") ? "updatedAt" : "createdAt",
			request.direction()
		);
		return postRepository.findByAuthorId(authorId, safeRequest);
	}

	@Transactional(readOnly = true)
	public Article findArticleBySlug(String username, String slug) {
		UUID authorId = userInternalApi.findIdByUsername(username)
			.orElseThrow(() -> new PostNotFoundException("Post not found"));
		return postRepository.findByAuthorIdAndSlug(authorId, slug)
			.orElseThrow(() -> new PostNotFoundException("Post not found"));
	}

}
