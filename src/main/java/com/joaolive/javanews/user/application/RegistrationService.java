package com.joaolive.javanews.user.application;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.user.RegistrationInitiatedEvent;
import com.joaolive.javanews.user.UserRegisterEvent;
import com.joaolive.javanews.user.domain.Registration;
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
		Registration registration = Registration.create(email, hash, command.username(), command.firstName(), command.lastName());
		registrationRepository.save(registration);
		publisher.publishEvent(new RegistrationInitiatedEvent(email.value(), registration.getVerificationCode()));
	}

	public VerificationResult confirmRegister(VerifyEmailCommand command) {
		Email email = Email.create(command.email());
		Registration registration = registrationRepository.findByEmailAndStatusPending(email)
			.orElseThrow(() -> new UserNotFoundException("Record not found"));
		VerificationResult result = registration.verify(command.code());
		registrationRepository.save(registration);
		if (result == VerificationResult.SUCCESS) {
			User user = User.create(email, registration.getPassword());
			user = userRepository.save(user);
			publisher.publishEvent(new UserRegisterEvent(
				user.getId(),
				registration.getUsername(),
				registration.getFirstName(),
				registration.getLastName()));
		}
		return result;
	}
}
