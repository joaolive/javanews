package com.joaolive.javanews.post.infrastructure.adapter.in.web;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaolive.javanews.post.application.usecase.FindPostByIdUseCase;
import com.joaolive.javanews.post.domain.model.Post;
import com.joaolive.javanews.post.infrastructure.adapter.in.web.response.PostResponse;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	private final FindPostByIdUseCase findPostByIdUseCase;

	public PostController(FindPostByIdUseCase findPostByIdUseCase) {
		this.findPostByIdUseCase = findPostByIdUseCase;
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponse> findPostById(@PathVariable UUID id) {
		Post post = findPostByIdUseCase.execute(id);
		PostResponse response = PostWebMapper.toResponse(post);
		return ResponseEntity.ok(response);
	}
}
