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
import com.joaolive.javanews.post.application.command.DeletePostCommand;
import com.joaolive.javanews.post.application.usecase.CreatePostUseCase;
import com.joaolive.javanews.post.application.usecase.DeletePostByIdUseCase;
import com.joaolive.javanews.post.application.usecase.FindPostByIdUseCase;
import com.joaolive.javanews.post.application.usecase.ListPostsUseCase;
import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.infrastructure.web.request.CreatePostRequest;
import com.joaolive.javanews.post.infrastructure.web.response.PostResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	private final FindPostByIdUseCase findPostByIdUseCase;
	private final ListPostsUseCase listPostsUseCase;
	private final CreatePostUseCase createPostUseCase;
	private final DeletePostByIdUseCase deletePostByIdUseCase;

	public PostController(
			FindPostByIdUseCase findPostByIdUseCase,
			ListPostsUseCase listPostsUseCase,
			CreatePostUseCase createPostUseCase,
			DeletePostByIdUseCase deletePostByIdUseCase) {
		this.findPostByIdUseCase = findPostByIdUseCase;
		this.listPostsUseCase = listPostsUseCase;
		this.createPostUseCase = createPostUseCase;
		this.deletePostByIdUseCase = deletePostByIdUseCase;
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> findPostById(@PathVariable UUID id) {
		Post post = findPostByIdUseCase.execute(id);
		PostResponse response = PostResponse.from(post);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<PageResult<PostResponse>> findAll(
			@PageableDefault(size = 30, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Sort.Order sortOrder = pageable.getSort().stream().findFirst()
				.orElse(new Sort.Order(Sort.Direction.DESC, "createdAt"));
		PaginationRequest paginationRequest = new PaginationRequest(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				sortOrder.getProperty(),
				sortOrder.getDirection().name());
		PageResult<Post> domainPage = listPostsUseCase.execute(paginationRequest);
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

	@PostMapping
	public ResponseEntity<PostResponse> createPost(
			@Valid @RequestBody CreatePostRequest request,
			@AuthenticationPrincipal Jwt jwt) {
		UUID authorId = UUID.fromString(jwt.getSubject());
		Post post = createPostUseCase.execute(request.toCommand(authorId));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(post.getId()).toUri();
		return ResponseEntity.created(uri).body(PostResponse.from(post));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePostById(
			@PathVariable UUID id,
			@AuthenticationPrincipal Jwt jwt) {
		UUID authorId = UUID.fromString(jwt.getSubject());
		DeletePostCommand command = new DeletePostCommand(id, authorId);
		deletePostByIdUseCase.execute(command);
		return ResponseEntity.noContent().build();
	}
}
