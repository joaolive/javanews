package com.joaolive.javanews.notification.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.joaolive.javanews.notification.domain.EmailGateway;
import com.joaolive.javanews.notification.domain.exception.NotificationEmailException;

@Component
public class SmtpEmailGateway implements EmailGateway {
	private final String emailFrom;
	private final JavaMailSender emailSender;
	
	public SmtpEmailGateway(@Value("${spring.mail.from}") String emailFrom, JavaMailSender emailSender) {
		this.emailFrom = emailFrom;
		this.emailSender = emailSender;
	}

	@Override
	public void send(String to, String subject, String body) {
		try{
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(emailFrom);
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);
			emailSender.send(message);
		} 
		catch (MailException e){
			throw new NotificationEmailException("Failed to send email");
		} 
	}
}

