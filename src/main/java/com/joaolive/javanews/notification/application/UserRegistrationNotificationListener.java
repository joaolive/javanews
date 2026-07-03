package com.joaolive.javanews.notification.application;

import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.joaolive.javanews.notification.domain.EmailGateway;
import com.joaolive.javanews.user.RegistrationInitiatedEvent;

@Component
public class UserRegistrationNotificationListener {
	private final EmailGateway emailGateway;

	public UserRegistrationNotificationListener(EmailGateway emailGateway) {
		this.emailGateway = emailGateway;
	}

	@ApplicationModuleListener
	public void on(RegistrationInitiatedEvent event) {
		/*
			TODO: Replace this plain text email with an MJML template rendered via Thymeleaf
			to provide responsive, maintainable, and branded HTML emails.
		*/
		String subject = "Welcome to Javanews! Confirm Your Email";
		String body = """
			Hello!
			
			We received a registration request for Javanews.
			To activate your account, please use the verification code below:
			
			%s
			
			If you did not request this registration, you can safely ignore this email.
			""".formatted(event.verificationCode());

		emailGateway.send(event.email(), subject, body);
	}

}
