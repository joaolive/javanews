package com.joaolive.javanews.user.application;

import java.time.Instant;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.user.RegistrationInitiatedEvent;
import com.joaolive.javanews.user.UserRegisterEvent;
import com.joaolive.javanews.user.domain.Registration;
import com.joaolive.javanews.user.domain.RegistrationPayload;
import com.joaolive.javanews.user.domain.RegistrationRepository;
import com.joaolive.javanews.user.domain.User;
import com.joaolive.javanews.user.domain.UserRepository;
import com.joaolive.javanews.user.domain.Registration.VerificationResult;
import com.joaolive.javanews.user.domain.exception.UserConflictException;
import com.joaolive.javanews.user.domain.exception.UserNotFoundException;
import com.joaolive.javanews.user.domain.valueobject.Email;

@Service
@Transactional
public class RegistrationService {
	private final ApplicationEventPublisher publisher;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final RegistrationRepository registrationRepository;

	public RegistrationService(ApplicationEventPublisher publisher, PasswordEncoder passwordEncoder,
			UserRepository userRepository, RegistrationRepository registrationRepository) {
		this.publisher = publisher;
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.registrationRepository = registrationRepository;
	}

	public void initiateRegister(RegisterUserCommand command) {
		Email email = Email.create(command.email());
		if (userRepository.existsByEmail(email))
			throw new UserConflictException("Email is already in use");
		String hash = passwordEncoder.encode(command.password());
		RegistrationPayload payload = new RegistrationPayload(hash, command.username(), command.firstName(), command.lastName());
		Registration registration = Registration.create(email, Instant.now(), payload);
		registrationRepository.save(registration);
		publisher.publishEvent(new RegistrationInitiatedEvent(email.value(), registration.getVerificationCode().value()));
	}

	public VerificationResult confirmRegister(VerifyEmailCommand command) {
		Email email = Email.create(command.email());
		Registration registration = registrationRepository.findByEmailAndStatusPending(email)
			.orElseThrow(() -> new UserNotFoundException("Record not found"));
		VerificationResult result = registration.verify(command.code());
		registrationRepository.save(registration);
		if (result != VerificationResult.SUCCESS) {
			registrationRepository.save(registration);
			return result;
		}
		RegistrationPayload payload = registration.getPayload();
		User user = User.create(email, payload.password());
		user = userRepository.save(user);
		registrationRepository.deleteById(registration.getId());
		publisher.publishEvent(new UserRegisterEvent(
			user.getId(),
			payload.username(),
			payload.firstName(),
			payload.lastName()));
		return result;
	}
}
