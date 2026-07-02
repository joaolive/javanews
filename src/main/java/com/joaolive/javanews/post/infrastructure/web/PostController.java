package com.joaolive.javanews.post.infrastructure.web;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.joaolive.javanews.auth.CurrentUser;
import com.joaolive.javanews.auth.UserContext;
import com.joaolive.javanews.common.PageResult;
import com.joaolive.javanews.common.PaginationRequest;
import com.joaolive.javanews.post.application.PostService;
import com.joaolive.javanews.post.application.DeletePostCommand;
import com.joaolive.javanews.post.domain.Article;
import com.joaolive.javanews.post.domain.Comment;
import com.joaolive.javanews.post.domain.Post;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/posts")
public class PostController {
	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> findById(@PathVariable UUID id) {
		Post post = postService.findById(id);
		PostResponse response = PostResponse.from(post);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<PageResult<PostResponse>> findAllArticles(
			@PageableDefault(size = 30, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
	) {
		Sort.Order sortOrder = pageable.getSort().stream().findFirst()
				.orElse(new Sort.Order(Sort.Direction.DESC, "createdAt"));
		PaginationRequest paginationRequest = new PaginationRequest(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				sortOrder.getProperty(),
				sortOrder.getDirection().name());
		PageResult<Article> domainPage = postService.findAllArticles(paginationRequest);
		PageResult<PostResponse> response = domainPage.map(PostResponse::from);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/author/{username}")
	public ResponseEntity<PageResult<PostResponse>> findPostsByAuthor(
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
		PageResult<Post> domainPage = postService.findPostsByAuthor(username, paginationRequest);
		PageResult<PostResponse> response = domainPage.map(PostResponse::from);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{username}/{slug}")
	public ResponseEntity<PostResponse> findArticleBySlug(
		@PathVariable String username,
		@PathVariable String slug
	) {
		Article article = postService.findArticleBySlug(username, slug);
		PostResponse response = PostResponse.from(article);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<PostResponse> createArticle(
			@Valid @RequestBody CreateArticleRequest request,
			@CurrentUser UserContext author) {
		Article article = postService.createArticle(request.toCommand(author.id(), author.username()));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(article.getId()).toUri();
		return ResponseEntity.created(uri).body(PostResponse.from(article));
	}

	@PostMapping("/{id}/comments")
	public ResponseEntity<PostResponse> createComment(
		@PathVariable UUID id,
		@Valid @RequestBody CreateCommentRequest request,
		@CurrentUser UserContext requester
	) {
		Comment comment = postService.createComment(request.toCommand(id, requester.id(), requester.username()));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(comment.getId()).toUri();
		return ResponseEntity.created(uri).body(PostResponse.from(comment));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostResponse> updateArticle(
		@PathVariable UUID id,
		@Valid @RequestBody UpdateArticleRequest request,
		@CurrentUser UserContext requester
	) {
		Article article = postService.updateArticle(id, requester.id(), request.toCommand());
		return ResponseEntity.ok(PostResponse.from(article));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePostById(@PathVariable UUID id, @CurrentUser UserContext author) {
		DeletePostCommand command = new DeletePostCommand(id, author.id());
		postService.delete(command);
		return ResponseEntity.noContent().build();
	}
}
