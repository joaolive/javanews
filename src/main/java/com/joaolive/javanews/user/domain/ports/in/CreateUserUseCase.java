package com.joaolive.javanews.user.domain.ports.in;

import com.joaolive.javanews.user.application.commands.CreateUserCommand;
import com.joaolive.javanews.user.domain.User;

public interface CreateUserUseCase {
	User execute(CreateUserCommand command);
}
