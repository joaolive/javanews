package com.joaolive.javanews.post.infrastructure.adapter.out.persistence;

import java.util.Set;
import java.util.stream.Collectors;

import com.joaolive.javanews.post.domain.model.Post;
import com.joaolive.javanews.post.domain.valueobject.Tag;

public class PostMapper {
	private PostMapper() {}

	public static PostEntity toEntity(Post domain, Set<TagEntity> tagEntities) {
		if (domain == null || tagEntities == null)
			return null;
		PostEntity entity = new PostEntity(
			domain.getId(),
			domain.getTitle().value(),
			domain.getSlug().value(),
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
