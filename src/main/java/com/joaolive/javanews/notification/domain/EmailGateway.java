package com.joaolive.javanews.notification.domain;

public interface EmailGateway {
	void send(String to, String subject, String body);
}
