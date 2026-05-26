package com.joaolive.javanews.user.application.usecase;

import com.joaolive.javanews.user.application.command.CreateUserCommand;
import com.joaolive.javanews.user.domain.model.User;

public interface CreateUserUseCase {
	User execute(CreateUserCommand command);
}
