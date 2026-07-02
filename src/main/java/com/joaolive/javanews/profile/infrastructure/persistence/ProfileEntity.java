package com.joaolive.javanews.profile.infrastructure.persistence;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_profile")
public class ProfileEntity {
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "user_id")
	private UUID	userId;
	private String	firstName;
	private String	lastName;
	@Column(unique = true, nullable = false)
	private String	username;
	@Column(columnDefinition = "TEXT")
	private String bio;
	private String	avatarKey;
	private Instant	createdAt;
	private Instant	updatedAt;
	@Version
	private Long version;
}
