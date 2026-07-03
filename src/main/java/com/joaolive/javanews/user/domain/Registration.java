package com.joaolive.javanews.user.domain;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.joaolive.javanews.user.domain.valueobject.Email;
import com.joaolive.javanews.user.domain.valueobject.VerificationCode;

public class Registration {
	private UUID id;
	private Email email;
	private VerificationCode verificationCode;
	private RegistrationStatus status;
	private Instant createdAt;
	private Instant expiresAt;
	private int failedAttempts;
	private final RegistrationPayload payload;

	private static final int MAX_ATTEMPTS = 5;
	private static final int EXPIRATION_MINUTES = 15;

	public enum RegistrationStatus {
		PENDING,
		FAILED
	}

	public enum VerificationResult {
		SUCCESS,
		INVALID_CODE,
		EXPIRED,
		BLOCKED
	}

	public Registration(UUID id, Email email, VerificationCode verificationCode, RegistrationStatus status, Instant createdAt,
			Instant expiresAt, int failedAttempts, RegistrationPayload payload) {
		this.id = id;
		this.email = email;
		this.verificationCode = verificationCode;
		this.status = status;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.failedAttempts = failedAttempts;
		this.payload = payload;
	}

	public static Registration create(Email email, Instant now, RegistrationPayload payload) {
		Instant expiration = now.plus(EXPIRATION_MINUTES, ChronoUnit.MINUTES);
		return new Registration(UUID.randomUUID(), email, VerificationCode.generate(), RegistrationStatus.PENDING, now, expiration, 0, payload);
	}

	public static Registration reconstitute(UUID id, String email, String verificationCode, RegistrationStatus status,
			Instant createdAt, Instant expiresAt, int failedAttempts, RegistrationPayload payload) {
		return new Registration(id, Email.restore(email), VerificationCode.restore(verificationCode), status, createdAt, expiresAt, failedAttempts, payload);
	}

	public VerificationResult verify(String code, Instant currentTime) {
		if (isAlreadyBlocked())
			return failWith(VerificationResult.BLOCKED);
		if (isExpired(currentTime))
			return failWith(VerificationResult.EXPIRED);
		if (isInvalid(code))
			return handleInvalidCode();
		return VerificationResult.SUCCESS;
	}

	public VerificationResult verify(String code) {
		return verify(code, Instant.now());
	}

	private boolean isAlreadyBlocked() {
		return this.status == RegistrationStatus.FAILED || this.failedAttempts >= MAX_ATTEMPTS;
	}

	private boolean isExpired(Instant currentTime) {
		return currentTime.isAfter(this.expiresAt);
	}

	private boolean isInvalid(String inputCode) {
		return !this.verificationCode.value().equals(inputCode);
	}

	private VerificationResult failWith(VerificationResult result) {
		this.status = RegistrationStatus.FAILED;
		return result;
	}

	private VerificationResult handleInvalidCode() {
		this.failedAttempts++;
		if (this.failedAttempts >= MAX_ATTEMPTS)
			return failWith(VerificationResult.BLOCKED);
		return VerificationResult.INVALID_CODE;
	}

	public UUID getId() {
		return id;
	}

	public Email getEmail() {
		return email;
	}

	public VerificationCode getVerificationCode() {
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

	public RegistrationPayload getPayload() {
		return payload;
	}

}
