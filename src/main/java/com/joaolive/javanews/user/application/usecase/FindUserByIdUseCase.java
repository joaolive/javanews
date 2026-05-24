package com.joaolive.javanews.user.application.usecase;

import java.util.UUID;

import com.joaolive.javanews.user.domain.model.User;

public interface FindUserByIdUseCase {
	User execute(UUID id);	
}
