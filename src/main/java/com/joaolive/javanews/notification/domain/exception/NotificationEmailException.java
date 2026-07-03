package com.joaolive.javanews.notification.domain.exception;

import com.joaolive.javanews.common.BaseInfrastructureException;

public class NotificationEmailException extends BaseInfrastructureException {
	public NotificationEmailException(String message) {
		super(message);
	}
}
