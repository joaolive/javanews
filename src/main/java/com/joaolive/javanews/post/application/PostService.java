package com.joaolive.javanews.post.application;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.common.PageResult;
import com.joaolive.javanews.common.PaginationRequest;
import com.joaolive.javanews.post.PostCreatedEvent;
import com.joaolive.javanews.post.domain.Article;
import com.joaolive.javanews.post.domain.Comment;
import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.PostRepository;
import com.joaolive.javanews.post.domain.exception.PostConflictException;
import com.joaolive.javanews.post.domain.exception.PostForbiddenException;
import com.joaolive.javanews.post.domain.exception.PostNotFoundException;
import com.joaolive.javanews.post.domain.valueobject.Body;
import com.joaolive.javanews.post.domain.valueobject.Slug;
import com.joaolive.javanews.post.domain.valueobject.Tag;
import com.joaolive.javanews.post.domain.valueobject.Title;
import com.joaolive.javanews.profile.ProfileInternalApi;

@Service
public class PostService {
	private final PostRepository postRepository;
	private final ProfileInternalApi profileInternalApi;
	private final ApplicationEventPublisher eventPublisher;

	public PostService(PostRepository postRepository, ProfileInternalApi profileInternalApi, ApplicationEventPublisher eventPublisher) {
		this.postRepository = postRepository;
		this.profileInternalApi = profileInternalApi;
		this.eventPublisher = eventPublisher;
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
		UUID authorId = profileInternalApi.findIdByUsername(username)
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
		UUID authorId = profileInternalApi.findIdByUsername(username)
			.orElseThrow(() -> new PostNotFoundException("Post not found"));
		return postRepository.findByAuthorIdAndSlug(authorId, slug)
			.orElseThrow(() -> new PostNotFoundException("Post not found"));
	}

	@Transactional
	public Article createArticle(CreateArticleCommand command) {
		Article article = postRepository.save(Article.create(
			Title.create(command.title()),
			Slug.create(command.title()),
			Body.create(command.body()),
			command.authorId(),
			command.author(),
			command.tags().stream()
				.map(Tag::create)
				.collect(Collectors.toSet())));
		eventPublisher.publishEvent(new PostCreatedEvent(
			article.getId(),
			article.getAuthorId(),
			article.getTags().stream()
				.map(Tag::value)
				.collect(Collectors.toSet())));
		return article;
	}

	@Transactional
	public Comment createComment(CreateCommentCommand command) {
		if (!postRepository.existsById(command.parentId()))
			throw new PostNotFoundException("Parent post not found");
		Comment comment = Comment.create(Body.create(command.body()), command.authorId(), command.author(), command.parentId());
		return postRepository.save(comment);
	}

	@Transactional
	public Article updateArticle(UUID articleId, UUID requesterId, UpdateArticleCommand command) {
		Post post = findById(articleId);
		if (!post.getAuthorId().equals(requesterId))
			throw new PostForbiddenException("User is not authorized to edit this post");
		if (!(post instanceof Article article))
			throw new PostConflictException("Only articles can have their title and tags changed.");
		article.update(
			Title.create(command.title()),
			Body.create(command.body()),
			command.tags().stream()
				.map(Tag::create)
				.collect(Collectors.toSet()));
		return postRepository.save(article);
	}

	@Transactional
	public void delete(DeletePostCommand command) {
		Post post = findById(command.postId());
		if (!post.getAuthorId().equals(command.authorId()))
			throw new PostForbiddenException("User is not authorized to delete this post");
		postRepository.delete(command.postId());
	}
}
