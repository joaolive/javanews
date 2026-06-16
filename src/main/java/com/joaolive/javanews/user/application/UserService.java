package com.joaolive.javanews.user.application;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.profile.domain.valueobject.Bio;
import com.joaolive.javanews.profile.domain.valueobject.Name;
import com.joaolive.javanews.profile.domain.valueobject.Username;
import com.joaolive.javanews.user.UserUsernameChangeEvent;
import com.joaolive.javanews.user.application.command.CreateUserCommand;
import com.joaolive.javanews.user.application.command.UpdateProfileCommand;
import com.joaolive.javanews.user.domain.User;
import com.joaolive.javanews.user.domain.UserRepository;
import com.joaolive.javanews.user.domain.exception.UserConflictException;
import com.joaolive.javanews.user.domain.exception.UserNotFoundException;
import com.joaolive.javanews.user.domain.valueobject.Email;

@Service
public class UserService {
	private final ApplicationEventPublisher publisher;
	private final UserRepository userRepository;

	public UserService(ApplicationEventPublisher publisher, UserRepository userRepository) {
		this.publisher = publisher;
		this.userRepository = userRepository;
	}

	@Transactional
	public User create(CreateUserCommand command) {
		Email email = Email.create(command.email());
		Username username = Username.create(command.username());
		Name firstName = Name.create(command.firstName());
		Name lastName = Name.create(command.lastName());
		Bio bio = Bio.create(command.bio());
		if (userRepository.existsByUsername(username))
			throw new UserConflictException("Username is already in use");
		if (userRepository.existsByEmail(email))
			throw new UserConflictException("Email is already in use");
		User user = User.createUser(email, username, command.password(), firstName, lastName, bio, command.avatarKey());
		return userRepository.save(user);
	}

	@Transactional
	public User updateProfile(UUID id, UpdateProfileCommand command) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException("User not found"));
		user.updateUser(Name.create(
			command.firstName()),
			Name.create(command.lastName()),
			Bio.create(command.bio()),
			command.avatarKey());
		return userRepository.save(user);
	}

	@Transactional
	public User updateUsername(UUID id, String newUsername) {
		Username username = Username.create(newUsername);
		User user = userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException("User not found"));
		if (user.getUsername().equals(username))
			return user;
		if (userRepository.existsByUsername(username))
			throw new UserConflictException("Username is already in use");
		user.updateUsername(username);
		user = userRepository.save(user);
		publisher.publishEvent(new UserUsernameChangeEvent(id, username.getValue()));
		return user;
	}
}
