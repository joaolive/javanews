package com.joaolive.javanews.user.application.services;

import com.joaolive.javanews.user.application.commands.CreateUserCommand;
import com.joaolive.javanews.user.domain.User;
import com.joaolive.javanews.user.domain.exceptions.InvalidUsernameException;
import com.joaolive.javanews.user.domain.ports.in.CreateUserUseCase;
import com.joaolive.javanews.user.domain.ports.out.UserRepository;
import com.joaolive.javanews.user.domain.records.Bio;
import com.joaolive.javanews.user.domain.records.Name;
import com.joaolive.javanews.user.domain.records.Username;

public class CreateUserService implements CreateUserUseCase {
	private final UserRepository userRepository;

	public CreateUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User execute(CreateUserCommand command) {
		Username username = new Username(command.username());
		Name firstName = new Name(command.firstName());
		Name lastName = new Name(command.lastName());
		Bio bio = new Bio(command.bio());
		if (userRepository.existsByUsername(username))
			throw new InvalidUsernameException("Username is already in use");
		User user = User.createUser(username, firstName, lastName, bio, command.avatarKey());
		userRepository.save(user);
		return (user);
	}
}
