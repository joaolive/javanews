package com.joaolive.javanews.post.infrastructure.web;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.joaolive.javanews.core.PageResult;
import com.joaolive.javanews.core.PaginationRequest;
import com.joaolive.javanews.post.application.PostService;
import com.joaolive.javanews.post.application.PostQueryService;
import com.joaolive.javanews.post.application.command.DeletePostCommand;
import com.joaolive.javanews.post.domain.Post;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/posts")
public class PostController {
	private final PostQueryService postQueryService;
	private final PostService postService;

	public PostController(PostQueryService postQueryService, PostService postService) {
		this.postQueryService = postQueryService;
		this.postService = postService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> findArticleById(@PathVariable UUID id) {
		Post post = postQueryService.findById(id);
		PostResponse response = PostResponse.from(post);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<PageResult<PostResponse>> findAll(
			@PageableDefault(size = 30, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
	) {
		Sort.Order sortOrder = pageable.getSort().stream().findFirst()
				.orElse(new Sort.Order(Sort.Direction.DESC, "createdAt"));
		PaginationRequest paginationRequest = new PaginationRequest(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				sortOrder.getProperty(),
				sortOrder.getDirection().name());
		PageResult<Post> domainPage = postQueryService.findAll(paginationRequest);
		PageResult<PostResponse> response = domainPage.map(PostResponse::from);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/author/{username}")
	public ResponseEntity<PageResult<PostResponse>> findArticleByAuthor(
		@PathVariable String username,
		@PageableDefault(size = 30, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
	) {
		Sort.Order sortOrder = pageable.getSort().stream().findFirst()
			.orElse(new Sort.Order(Sort.Direction.DESC, "createdAt"));
		PaginationRequest paginationRequest = new PaginationRequest(
			pageable.getPageNumber(),
			pageable.getPageSize(),
			sortOrder.getProperty(),
			sortOrder.getDirection().name());
		PageResult<Post> domainPage = postQueryService.findArticleByAuthor(username, paginationRequest);
		PageResult<PostResponse> response = new PageResult<>(
			domainPage.data().stream()
					.map(x -> PostResponse.from(x))
					.toList(),
			domainPage.totalPages(),
			domainPage.totalElements(),
			domainPage.currentPage(),
			domainPage.pageSize());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{username}/{slug}")
	public ResponseEntity<PostResponse> findArticleBySlug(
		@PathVariable String username,
		@PathVariable String slug
	) {
		Post post = postQueryService.findArticleBySlug(username, slug);
		PostResponse response = PostResponse.from(post);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<PostResponse> createArticle(
			@Valid @RequestBody CreatePostRequest request,
			@AuthenticationPrincipal Jwt jwt) {
		UUID authorId = UUID.fromString(jwt.getClaimAsString("user_id"));
		Post post = postService.createArticle(request.toCommand(authorId));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(post.getId()).toUri();
		return ResponseEntity.created(uri).body(PostResponse.from(post));
	}

	@PostMapping("/{id}/comments")
	public ResponseEntity<PostResponse> createComment(
		@PathVariable UUID id,
		@Valid @RequestBody CreateCommentRequest request,
		@AuthenticationPrincipal Jwt jwt
	) {
		UUID requesterId = UUID.fromString(jwt.getClaimAsString("user_id"));
		Post post = postService.createComment(request.toCommand(id, requesterId));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(post.getId()).toUri();
		return ResponseEntity.created(uri).body(PostResponse.from(post));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostResponse> updateArticle(
		@PathVariable UUID id,
		@Valid @RequestBody UpdatePostRequest request,
		@AuthenticationPrincipal Jwt jwt
	) {
		UUID requesterId = UUID.fromString(jwt.getClaimAsString("user_id"));
		Post post = postService.updateArticle(id, requesterId, request.toCommand());
		return ResponseEntity.ok(PostResponse.from(post));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePostById(
			@PathVariable UUID id,
			@AuthenticationPrincipal Jwt jwt) {
		UUID authorId = UUID.fromString(jwt.getClaimAsString("user_id"));
		DeletePostCommand command = new DeletePostCommand(id, authorId);
		postService.delete(command);
		return ResponseEntity.noContent().build();
	}
}
