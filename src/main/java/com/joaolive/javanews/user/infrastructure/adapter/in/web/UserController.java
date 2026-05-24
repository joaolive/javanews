package com.joaolive.javanews.user.infrastructure.adapter.in.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.joaolive.javanews.user.application.usecase.CheckUsernameAvailabilityUseCase;
import com.joaolive.javanews.user.application.usecase.CreateUserUseCase;
import com.joaolive.javanews.user.application.usecase.FindUserByIdUseCase;
import com.joaolive.javanews.user.domain.model.User;
import com.joaolive.javanews.user.infrastructure.adapter.in.web.request.CreateUserRequest;
import com.joaolive.javanews.user.infrastructure.adapter.in.web.response.UserResponse;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final FindUserByIdUseCase findUserByIdUseCase;
	private final CheckUsernameAvailabilityUseCase checkUsernameAvailabilityUseCase;
	private final CreateUserUseCase createUserUseCase;

	public UserController(FindUserByIdUseCase findUserByIdUseCase,
			CheckUsernameAvailabilityUseCase checkUsernameAvailabilityUseCase, CreateUserUseCase createUserUseCase) {
		this.findUserByIdUseCase = findUserByIdUseCase;
		this.checkUsernameAvailabilityUseCase = checkUsernameAvailabilityUseCase;
		this.createUserUseCase = createUserUseCase;
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> findUserById(@PathVariable UUID id) {
		User user = findUserByIdUseCase.execute(id);
		UserResponse response = UserResponse.from(user);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
		User user = createUserUseCase.execute(request.toCommand());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(UserResponse.from(user));
	}
}
