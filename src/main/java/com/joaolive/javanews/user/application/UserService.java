package com.joaolive.javanews.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.user.application.command.CreateUserCommand;
import com.joaolive.javanews.user.domain.User;
import com.joaolive.javanews.user.domain.UserRepository;
import com.joaolive.javanews.user.domain.exception.UserConflictException;
import com.joaolive.javanews.user.domain.valueobject.Bio;
import com.joaolive.javanews.user.domain.valueobject.Email;
import com.joaolive.javanews.user.domain.valueobject.Name;
import com.joaolive.javanews.user.domain.valueobject.Username;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
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
		userRepository.save(user);
		return user;
	}
}
