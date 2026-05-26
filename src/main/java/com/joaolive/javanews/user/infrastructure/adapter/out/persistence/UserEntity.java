package com.joaolive.javanews.user.infrastructure.adapter.out.persistence;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AccessLevel;
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

	@Column(unique = true, nullable = false)
	private String email;

	@Column(unique = true, nullable = false)
	private String username;

	@Setter(AccessLevel.NONE)
	private String password;

	private String firstName;
	private String lastName;

	@Column(columnDefinition = "TEXT")
	private String bio;
	
	private String avatarKey;
	private Instant createdAt;
	private Instant updatedAt;

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "tb_user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	private Set<RoleEntity> roles = new HashSet<>();

	void setPassword(String password) {
		this.password = password;
	}

	public Set<RoleEntity> getRoles() {
		return Set.copyOf(roles);
	}

	public void addRole(RoleEntity role) {
		roles.add(role);
	}
}
