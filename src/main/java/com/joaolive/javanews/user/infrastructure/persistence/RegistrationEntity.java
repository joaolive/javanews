package com.joaolive.javanews.user.infrastructure.persistence;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_registration")
public class RegistrationEntity {
	@EqualsAndHashCode.Include
	@Id
	private UUID id;
	private String email;
	private String password;
	private String username;
	private String firstName;
	private String lastName;
	private String verificationCode;
	private String status;
	private Instant createdAt;
}
