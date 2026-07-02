package com.joaolive.javanews.post.infrastructure.persistence;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.joaolive.javanews.common.Identifiable;
import com.joaolive.javanews.post.domain.valueobject.PostType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_post")
public class PostEntity implements Identifiable<UUID> {
	@Id
	private UUID	id;
	@Column(name = "parent_id")
	private UUID	parentId;
	@Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PostType type;
	private String	title;
	private String	slug;
	@Column(columnDefinition = "TEXT")
	private String	body;
	@Column(name = "author_id")
	private UUID	authorId;
	private String	author;
	private Instant	createdAt;
	private Instant	updatedAt;

	@Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(
		name = "tb_post_tag",
		joinColumns = @JoinColumn(name = "post_id"),
		inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<TagEntity>	tags = new HashSet<>();

	public PostEntity(UUID id, UUID parentId, PostType type, String title, String slug, String body, UUID authorId, String author, Instant createdAt,
			Instant updatedAt) {
		this.id = id;
		this.parentId = parentId;
		this.type = type;
		this.title = title;
		this.slug = slug;
		this.body = body;
		this.authorId = authorId;
		this.author = author;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Set<TagEntity> getTags() {
		return Collections.unmodifiableSet(this.tags);
	}

	public void addTag(TagEntity tag) {
		this.tags.add(tag);
	}

	public void removeTag(TagEntity tag) {
		this.tags.remove(tag);
	}
}
