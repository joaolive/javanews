package com.joaolive.javanews.user.infrastructure.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaolive.javanews.user.application.RegisterUserCommand;
import com.joaolive.javanews.user.application.RegistrationService;
import com.joaolive.javanews.user.application.VerifyEmailCommand;
import com.joaolive.javanews.user.domain.Registration.VerificationResult;
import com.joaolive.javanews.user.domain.exception.InvalidVerificationCodeException;
import com.joaolive.javanews.user.domain.exception.MaxVerificationAttemptsExceededException;
import com.joaolive.javanews.user.domain.exception.VerificationCodeExpiredException;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final RegistrationService registrationService;

	public UserController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid RegisterUserRequest request) {
		registrationService.initiateRegister(new RegisterUserCommand(
			request.firstName(),
			request.lastName(),
			request.username(),
			request.email(),
			request.password()));
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@PostMapping("/verify")
	public ResponseEntity<Void> verify(@RequestBody @Valid VerifyEmailRequest request) {
		VerificationResult result = registrationService.confirmRegister(
			new VerifyEmailCommand(request.email(), request.code())
		);
		return switch (result) {
			case BLOCKED -> throw new MaxVerificationAttemptsExceededException("Maximum attempts exceeded.");
			case EXPIRED -> throw new VerificationCodeExpiredException("The verification code has expired.");
			case INVALID_CODE -> throw new InvalidVerificationCodeException("Invalid verification code.");
			case SUCCESS -> ResponseEntity.status(HttpStatus.CREATED).build();
		};
	}
}
