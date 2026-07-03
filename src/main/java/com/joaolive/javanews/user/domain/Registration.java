package com.joaolive.javanews.user.domain;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.joaolive.javanews.user.domain.valueobject.Email;

public class Registration {
	private UUID id;
	private Email email;
	private String password;
	private String username;
	private String firstName;
	private String lastName;
	private String verificationCode;
	private RegistrationStatus status;
	private Instant createdAt;
	private Instant expiresAt;
	private int failedAttempts;

	private static final int MAX_ATTEMPTS = 5;
	private static final int EXPIRATION_MINUTES = 15;

	private static final SecureRandom SECURE_RANDOM = new SecureRandom();

	public enum RegistrationStatus {
		PENDING,
		CONFIRMED,
		FAILED
	}

	public enum VerificationResult {
		SUCCESS,
		INVALID_CODE,
		EXPIRED,
		BLOCKED
	}

	private Registration(UUID id, Email email, String password, String username, String firstName, String lastName,
			String verificationCode, RegistrationStatus status, Instant createdAt, Instant expiresAt, int failedAttempts) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.verificationCode = verificationCode;
		this.status = status;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.failedAttempts = failedAttempts;
	}

	public static Registration create(Email email, String password, String username, String firstName, String lastName) {
		Instant now = Instant.now();
		Instant expiration = now.plus(EXPIRATION_MINUTES, ChronoUnit.MINUTES);
		String code = String.format("%06d", SECURE_RANDOM.nextInt(1000000));
		return new Registration(UUID.randomUUID(), email, password, username, firstName, lastName, code, RegistrationStatus.PENDING, now, expiration, 0);
	}

	public static Registration reconstitute(UUID id, String email, String password,
			String username, String firstName, String lastName, String verificationCode, RegistrationStatus status, Instant createdAt, Instant expiresAt, int failedAttempts) {
		return new Registration(id, Email.restore(email), password, username, firstName, lastName, verificationCode, status, createdAt, expiresAt, failedAttempts);
	}

	public VerificationResult verify(String code, Instant currentTime) {
		if (this.status == RegistrationStatus.FAILED || this.failedAttempts >= MAX_ATTEMPTS) {
			this.status = RegistrationStatus.FAILED;
			return VerificationResult.BLOCKED;
		}
		if (currentTime.isAfter(this.expiresAt)) {
			this.status = RegistrationStatus.FAILED;
			return VerificationResult.EXPIRED;
		}
		if (!this.verificationCode.equals(code)) {
			this.failedAttempts++;
			if (this.failedAttempts >= MAX_ATTEMPTS) {
				this.status = RegistrationStatus.FAILED;
				return VerificationResult.BLOCKED;
			}
			return VerificationResult.INVALID_CODE;
		}
		this.status = RegistrationStatus.CONFIRMED;
		return VerificationResult.SUCCESS;
	}

	public VerificationResult verify(String code) {
		return verify(code, Instant.now());
	}

	public UUID getId() {
		return id;
	}

	public Email getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public RegistrationStatus getStatus() {
		return status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public int getFailedAttempts() {
		return failedAttempts;
	}

}
