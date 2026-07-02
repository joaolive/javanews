package com.joaolive.javanews.post.infrastructure.persistence;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.joaolive.javanews.common.BatchFetchAligner;
import com.joaolive.javanews.common.PageResult;
import com.joaolive.javanews.common.PaginationRequest;
import com.joaolive.javanews.post.domain.Article;
import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.PostRepository;
import com.joaolive.javanews.post.domain.valueobject.PostType;

@Repository
public class PostRepositoryAdapter implements PostRepository {
	private final PostJpaRepository postRepository;
	private final TagJpaRepository tagRepository;

	public PostRepositoryAdapter(PostJpaRepository postRepository, TagJpaRepository tagRepository) {
		this.postRepository = postRepository;
		this.tagRepository = tagRepository;
	}

	@Override
	public Optional<Post> findById(UUID id) {
		return postRepository.findById(id).map(PostMapper::toDomain);
	}

	@Override
	public Optional<Article> findByAuthorIdAndSlug(UUID authorId, String slug) {
		return postRepository.findByAuthorIdAndSlugAndType(authorId, slug, PostType.ARTICLE).map(PostMapper::toDomain).map(x -> (Article)x);
	}

	@Override
	public PageResult<Article> findAllArticles(PaginationRequest request) {
		Sort.Direction direction = Sort.Direction.fromString(request.direction());
		PageRequest pageable = PageRequest.of(
			request.page(),
			request.size(),
			Sort.by(direction, request.sortBy())
		);

		Page<PostIdProjection> pagedIds = postRepository.findPagedIds(PostType.ARTICLE, pageable);
		if (pagedIds.isEmpty()) {
			return new PageResult<>(
				List.of(),
				pagedIds.getTotalPages(),
				pagedIds.getTotalElements(),
				pagedIds.getNumber(),
				pagedIds.getSize()
			);
		}

		List<UUID> idsToFetch = pagedIds.stream()
			.map(x -> x.getId())
			.toList();
		List<PostEntity> unorderedEntities = postRepository.findWithTagsByIds(idsToFetch);
		List<PostEntity> orderedEntities = BatchFetchAligner.align(pagedIds.getContent(), unorderedEntities);

		PageResult<PostEntity> entityPaged = new PageResult<>(
			orderedEntities,
			pagedIds.getTotalPages(),
			pagedIds.getTotalElements(),
			pagedIds.getNumber(),
			pagedIds.getSize()
		);
		return entityPaged.map(PostMapper::toDomain).map(x -> (Article)x);
	}

	@Override
	public PageResult<Post> findByAuthorId(UUID id, PaginationRequest request) {
		Sort.Direction direction = Sort.Direction.fromString(request.direction());
		PageRequest pageable = PageRequest.of(
			request.page(),
			request.size(),
			Sort.by(direction, request.sortBy())
		);

		Page<PostIdProjection> pagedIds = postRepository.findPagedIdsByAuthorId(id, pageable);
		if (pagedIds.isEmpty()) {
			return new PageResult<>(
				List.of(),
				pagedIds.getTotalPages(),
				pagedIds.getTotalElements(),
				pagedIds.getNumber(),
				pagedIds.getSize()
			);
		}

		List<UUID> idsToFetch = pagedIds.stream()
			.map(x -> x.getId())
			.toList();
		List<PostEntity> unorderedEntities = postRepository.findWithTagsByIds(idsToFetch);
		List<PostEntity> orderedEntities = BatchFetchAligner.align(pagedIds.getContent(), unorderedEntities);
		List<Post> domainPosts = orderedEntities.stream()
			.map(PostMapper::toDomain)
			.toList();
		
		return new PageResult<>(
			domainPosts,
			pagedIds.getTotalPages(),
			pagedIds.getTotalElements(),
			pagedIds.getNumber(),
			pagedIds.getSize()
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Post> T save(T post) {
		Set<TagEntity> tagEntities = new HashSet<>();
		if (post instanceof Article article && !article.getTags().isEmpty()) {
			Set<String> tagNames = article.getTags().stream().map(x -> x.value()).collect(Collectors.toSet());
			tagEntities = tagRepository.findByNameIn(tagNames);
		}
		PostEntity entity = PostMapper.toEntity(post, tagEntities);
		entity = postRepository.save(entity);
		return (T)PostMapper.toDomain(entity);
	}

	@Override
	public void updateAuthorUsername(UUID authorId, String newUsername) {
		postRepository.updateAuthorUsername(authorId, newUsername);
	}

	@Override
	public void delete(UUID id) {
		postRepository.deleteById(id);
	}

	@Override
	public boolean existsById(UUID id) {
		return postRepository.existsById(id);
	}

}
