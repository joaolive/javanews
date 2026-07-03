package com.joaolive.javanews.user.domain;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

import com.joaolive.javanews.user.domain.exception.InvalidVerificationCodeException;
import com.joaolive.javanews.user.domain.exception.RegistrationAlreadyConfirmedException;
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
	private static final SecureRandom SECURE_RANDOM = new SecureRandom();

	public enum RegistrationStatus {
		PENDING, CONFIRMED
	}

	private Registration(UUID id, Email email, String password, String username, String firstName, String lastName,
			String verificationCode, RegistrationStatus status, Instant createdAt) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.verificationCode = verificationCode;
		this.status = status;
		this.createdAt = createdAt;
	}

	public static Registration create(Email email, String password, String username, String firstName, String lastName) {
		int secureCode = 100000 + SECURE_RANDOM.nextInt(900000);
		return new Registration(UUID.randomUUID(), email, password, username, firstName, lastName, String.valueOf(secureCode), RegistrationStatus.PENDING, Instant.now());
	}

	public static Registration reconstitute(UUID id, String email, String password,
			String username, String firstName, String lastName, String verificationCode, String status, Instant createdAt) {
		return new Registration(id, Email.restore(email), password, username, firstName, lastName, verificationCode, RegistrationStatus.valueOf(status), createdAt);
	}

	public void verify(String code) {
		if (this.status == RegistrationStatus.CONFIRMED)
			throw new RegistrationAlreadyConfirmedException("Registration is already confirmed");
		if (!this.verificationCode.equals(code))
			throw new InvalidVerificationCodeException("Invalid verification code");
		this.status = RegistrationStatus.CONFIRMED;
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

}
