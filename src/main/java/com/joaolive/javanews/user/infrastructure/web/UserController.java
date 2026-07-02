package com.joaolive.javanews.user.infrastructure.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaolive.javanews.user.application.RegisterUserCommand;
import com.joaolive.javanews.user.application.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<Void> register(@RequestBody @Valid RegisterUserRequest request) {
		userService.register(new RegisterUserCommand(
			request.firstName(),
			request.lastName(),
			request.username(),
			request.email(),
			request.password()));
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
