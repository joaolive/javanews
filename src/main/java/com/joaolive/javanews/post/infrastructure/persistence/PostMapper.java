package com.joaolive.javanews.post.infrastructure.persistence;

import java.util.Set;
import java.util.stream.Collectors;

import com.joaolive.javanews.post.domain.Article;
import com.joaolive.javanews.post.domain.Comment;
import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.valueobject.Tag;

public class PostMapper {
	private PostMapper() {}

public static PostEntity toEntity(Post domain, Set<TagEntity> tagEntities) {
		if (domain == null)
			return null;
		PostEntity entity = new PostEntity();
		entity.setId(domain.getId());
		entity.setBody(domain.getBody().value());
		entity.setAuthorId(domain.getAuthorId());
		entity.setCreatedAt(domain.getCreatedAt());
		entity.setUpdatedAt(domain.getUpdatedAt());
		entity.setType(domain.getType());

		switch (domain) {
			case Article article -> {
				entity.setTitle(article.getTitle().value());
				entity.setSlug(article.getSlug().value());
				tagEntities.forEach(entity::addTag);
			}
			case Comment comment -> {
				entity.setParentId(comment.getParentId());
			}
		}
		return entity;
	}

	public static Post toDomain(PostEntity entity) {
		if (entity == null)
			return null;
		return switch (entity.getType()) {
			case ARTICLE -> Article.reconstitute(
				entity.getId(),
				entity.getTitle(),
				entity.getSlug(),
				entity.getBody(),
				entity.getAuthorId(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.getTags().stream()
					.map(x -> Tag.restore(x.getName()))
					.collect(Collectors.toSet())
			);
			case COMMENT -> Comment.reconstitute(
				entity.getId(),
				entity.getBody(),
				entity.getAuthorId(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.getParentId()
			);
		};
	}
}
