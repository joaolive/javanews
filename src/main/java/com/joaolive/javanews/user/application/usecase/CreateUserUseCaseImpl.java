package com.joaolive.javanews.user.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaolive.javanews.user.application.command.CreateUserCommand;
import com.joaolive.javanews.user.application.port.out.UserRepository;
import com.joaolive.javanews.user.domain.exception.InvalidUsernameException;
import com.joaolive.javanews.user.domain.model.User;
import com.joaolive.javanews.user.domain.valueobject.Bio;
import com.joaolive.javanews.user.domain.valueobject.Name;
import com.joaolive.javanews.user.domain.valueobject.Username;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {
	private final UserRepository userRepository;

	public CreateUserUseCaseImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
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
