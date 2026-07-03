package com.joaolive.javanews.user.infrastructure.persistence;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.joaolive.javanews.user.domain.Registration.RegistrationStatus;
import com.joaolive.javanews.user.domain.RegistrationPayload;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	private String verificationCode;
	@Enumerated(EnumType.STRING)
	private RegistrationStatus status;
	private Instant createdAt;
	private Instant expiresAt;
	private Integer failedAttempts;
	@JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
    private RegistrationPayload payload;
}
