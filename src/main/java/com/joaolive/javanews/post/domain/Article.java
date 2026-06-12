package com.joaolive.javanews.post.domain;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.joaolive.javanews.post.domain.valueobject.Body;
import com.joaolive.javanews.post.domain.valueobject.PostType;
import com.joaolive.javanews.post.domain.valueobject.Slug;
import com.joaolive.javanews.post.domain.valueobject.Tag;
import com.joaolive.javanews.post.domain.valueobject.Title;

public final class Article extends Post {
	private Title		title;
	private final Slug	slug;
	private Set<Tag>	tags;

	
	private Article(UUID id, Body body, UUID authorId, String author, Instant createdAt, Instant updatedAt, Title title, Slug slug,
			Set<Tag> tags) {
		super(id, body, authorId, author, createdAt, updatedAt);
		this.title = title;
		this.slug = slug;
		this.tags = new HashSet<>(tags);
	}

	public static Article create(Title title, Slug slug, Body body, UUID authorId, String author, Set<Tag> tags) {
		Instant now = Instant.now();
		return new Article(UUID.randomUUID(), body, authorId, author, now, now, title, slug, tags);
	}

	public static Article reconstitute(UUID id, String title, String slug, String body, UUID authorId, String author, Instant createdAt,
		Instant updatedAt, Set<Tag> tags) {
		return new Article(id, Body.restore(body), authorId, author, createdAt, updatedAt, Title.restore(title), Slug.restore(slug), tags);
	}

	public void update(Title title, Body body, Set<Tag> tags) {
		this.title = title;
		this.body = body;
		this.tags = new HashSet<>(tags);
		this.updatedAt = Instant.now();
	}

	public Title getTitle() {
		return title;
	}

	public Slug getSlug() {
		return slug;
	}

	public Set<Tag> getTags() {
		return Collections.unmodifiableSet(this.tags);
	}

	@Override
	public PostType getType() {
		return PostType.ARTICLE;
	}

	public void addTag(Tag tag) {
		if (tag != null && this.tags.add(tag))
			this.updatedAt = Instant.now();
	}

	public void removeTag(Tag tag) {
		if (tag != null && this.tags.remove(tag))
			this.updatedAt = Instant.now();
	}

}
