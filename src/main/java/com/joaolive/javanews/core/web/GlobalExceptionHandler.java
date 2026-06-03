package com.joaolive.javanews.core.web;

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

import com.joaolive.javanews.core.exception.BaseForbiddenException;
import com.joaolive.javanews.core.exception.BaseNotFoundException;
import com.joaolive.javanews.core.exception.BaseValidationException;

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

}
