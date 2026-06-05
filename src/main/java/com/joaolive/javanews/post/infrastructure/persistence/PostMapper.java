package com.joaolive.javanews.post.infrastructure.persistence;

import java.util.Set;
import java.util.stream.Collectors;

import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.valueobject.Slug;
import com.joaolive.javanews.post.domain.valueobject.Tag;
import com.joaolive.javanews.post.domain.valueobject.Title;

public class PostMapper {
	private PostMapper() {}

	public static PostEntity toEntity(Post domain, Set<TagEntity> tagEntities) {
		if (domain == null || tagEntities == null)
			return null;
		PostEntity entity = new PostEntity(
			domain.getId(),
			domain.getParentId(),
			domain.getType(),
			domain.getTitle().map(Title::value).orElse(null),
			domain.getSlug().map(Slug::value).orElse(null),
			domain.getBody().value(),
			domain.getAuthorId(),
			domain.getCreatedAt(),
			domain.getUpdatedAt()
		);
			tagEntities.forEach(x -> entity.addTag(x));
		return (entity);
	}

	public static Post toDomain(PostEntity entity) {
		if (entity == null)
			return null;
		return Post.reconstitute(
			entity.getId(),
			entity.getParentId(),
			entity.getType(),
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
	}
}
