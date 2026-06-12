package com.joaolive.javanews.user.infrastructure.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.joaolive.javanews.user.application.UserService;
import com.joaolive.javanews.core.CurrentUser;
import com.joaolive.javanews.core.UserContext;
import com.joaolive.javanews.user.application.UserQueryService;
import com.joaolive.javanews.user.domain.User;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserQueryService userQueryService;
	private final UserService userService;

	public UserController(UserQueryService userQueryService, UserService userService) {
		this.userQueryService = userQueryService;
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
		User user = userQueryService.findById(id);
		UserResponse response = UserResponse.from(user);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/check-email")
	public ResponseEntity<Boolean> checkEmailAvailability(@RequestParam String email) {
		return ResponseEntity.ok(userQueryService.checkEmailAvailability(email));
	}
	@GetMapping("/check-username")
	public ResponseEntity<Boolean> checkUsernameAvailability(@RequestParam String username) {
		return ResponseEntity.ok(userQueryService.checkUsernameAvailability(username));
	}
	
	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
		User user = userService.create(request.toCommand());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(UserResponse.from(user));
	}

	@PutMapping("/me/profile")
	public ResponseEntity<UserResponse> updateProfile(
		@CurrentUser UserContext requester,
		@Valid @RequestBody UpdateProfileRequest request
	) {
		User user = userService.updateProfile(requester.id(), request.toCommand());
		return ResponseEntity.ok(UserResponse.from(user));
	}
}
