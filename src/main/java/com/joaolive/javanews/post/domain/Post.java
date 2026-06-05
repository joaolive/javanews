package com.joaolive.javanews.post.domain;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.joaolive.javanews.post.domain.valueobject.Body;
import com.joaolive.javanews.post.domain.valueobject.Slug;
import com.joaolive.javanews.post.domain.valueobject.Tag;
import com.joaolive.javanews.post.domain.valueobject.Title;

public class Post {
	private final UUID		id;
	private Title			title;
	private final Slug		slug;
	private Body			body;
	private UUID			authorId;
	private final Instant	createdAt;
	private Instant			updatedAt;
	private Set<Tag>		tags;

	private Post(UUID id, Title title, Slug slug, Body body, UUID authorId, Instant createdAt, Instant updatedAt,
			Set<Tag> tags) {
		this.id = id;
		this.title = title;
		this.slug = slug;
		this.body = body;
		this.authorId = authorId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.tags = new HashSet<>(tags);
	}

	public static Post createPost(Title title, Slug slug, Body body, UUID authorId, Set<Tag> tags) {
		Instant now = Instant.now();
		return new Post(UUID.randomUUID(), title, slug, body, authorId, now, now, tags);
	}

	public void updatePost(String title, String body, Set<Tag> tags) {
		this.title = new Title(title);
		this.body = new Body(body);
		this.tags = new HashSet<>(tags);
		this.updatedAt = Instant.now();
	}

	public static Post reconstitute(UUID id, String title, String slug, String body, UUID authorId, Instant createdAt,
			Instant updatedAt, Set<Tag> tags) {
		return new Post(id, Title.restore(title), Slug.restore(slug), Body.restore(body), authorId, createdAt, updatedAt, tags);
	}

	public UUID getId() {
		return id;
	}

	public Title getTitle() {
		return title;
	}

	public Slug getSlug() {
		return slug;
	}

	public Body getBody() {
		return body;
	}

	public UUID getAuthorId() {
		return authorId;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public Set<Tag> getTags() {
		return Collections.unmodifiableSet(this.tags);
	}

	public void addTag(Tag tag) {
		if (tag != null && this.tags.add(tag))
			this.updatedAt = Instant.now();
	}

	public void removeTag(Tag tag) {
		if (tag != null && this.tags.remove(tag))
			this.updatedAt = Instant.now();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
