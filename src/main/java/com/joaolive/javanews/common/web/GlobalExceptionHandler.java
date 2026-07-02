package com.joaolive.javanews.common.web;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.joaolive.javanews.common.BaseConflictException;
import com.joaolive.javanews.common.BaseForbiddenException;
import com.joaolive.javanews.common.BaseNotFoundException;
import com.joaolive.javanews.common.BaseUnauthorizedException;
import com.joaolive.javanews.common.BaseValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({IllegalArgumentException.class, BaseValidationException.class})
	public ProblemDetail handleDomainValidationException(RuntimeException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
		problemDetail.setTitle("Domain Validation Error");
		problemDetail.setType(URI.create("https://api.javanews.com/errors/domain-validation"));
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}

	@ExceptionHandler(BaseUnauthorizedException.class)
	public ProblemDetail handleUnauthorizedException(BaseUnauthorizedException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
		problemDetail.setTitle("Unauthorized / Unauthenticated");
		problemDetail.setType(URI.create("https://api.javanews.com/errors/unauthorized"));
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}

	@ExceptionHandler(BaseNotFoundException.class)
	public ProblemDetail handleNotFoundException(BaseNotFoundException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
		problemDetail.setTitle("Resource Not Found");
		problemDetail.setType(URI.create("https://api.javanews.com/errors/not-found"));
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}

	@ExceptionHandler(BaseForbiddenException.class)
	public ProblemDetail handleForbiddenException(BaseForbiddenException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
		problemDetail.setTitle("Access Forbidden");
		problemDetail.setType(URI.create("https://api.javanews.com/errors/forbidden"));
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}

	@ExceptionHandler(BaseConflictException.class)
	public ProblemDetail handleConflictException(BaseConflictException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
		problemDetail.setTitle("Resource Conflict");
		problemDetail.setType(URI.create("https://api.javanews.com/errors/conflict"));
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ProblemDetail handleDataIntegrityViolation(DataIntegrityViolationException ex) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
				HttpStatus.CONFLICT, 
				"A database conflict or constraint violation occurred."
		);
		problemDetail.setTitle("Resource Conflict");
		problemDetail.setType(URI.create("https://api.javanews.com/errors/conflict"));
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.collect(Collectors.toList());

		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid request payload");
		problemDetail.setTitle("Bad Request");
		problemDetail.setType(URI.create("https://api.javanews.com/errors/bad-request"));
		problemDetail.setProperty("invalid_fields", errors);
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleUncaughtException(Exception ex) {
		ex.printStackTrace(); 
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
				HttpStatus.INTERNAL_SERVER_ERROR, 
				"An unexpected internal server error occurred."
		);
		problemDetail.setTitle("Internal Server Error");
		problemDetail.setType(URI.create("https://api.javanews.com/errors/internal-server-error"));
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}

}
