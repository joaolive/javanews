package com.joaolive.javanews.user.infrastructure.adapter.out.persistence;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_user")
public class UserEntity {
	@Id
	private UUID id;
	@Column(unique = true)
	private String email;
	private String username;
	private String firstName;
	private String lastName;
	@Column(columnDefinition = "TEXT")
	private String bio;
	private String avatarKey;
	private Instant createdAt;
	private Instant updatedAt;
}
