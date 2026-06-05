package com.joaolive.javanews.post.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.joaolive.javanews.core.BatchFetchAligner;
import com.joaolive.javanews.core.PageResult;
import com.joaolive.javanews.core.PaginationRequest;
import com.joaolive.javanews.post.domain.Post;
import com.joaolive.javanews.post.domain.PostRepository;

@Repository
public class PostRepositoryAdapter implements PostRepository {
	private final PostJpaRepository postRepository;
	private final TagJpaRepository tagRepository;

	public PostRepositoryAdapter(PostJpaRepository postRepository, TagJpaRepository tagRepository) {
		this.postRepository = postRepository;
		this.tagRepository = tagRepository;
	}

	@Override
	public Post save(Post post) {
		Set<String> tagNames = post.getTags().stream().map(x -> x.value()).collect(Collectors.toSet());
		Set<TagEntity> tagEntities = tagRepository.findByNameIn(tagNames);
		PostEntity entity = PostMapper.toEntity(post, tagEntities);
		entity = postRepository.save(entity);
		return PostMapper.toDomain(entity);
	}

	@Override
	public Optional<Post> findById(UUID id) {
		return postRepository.findById(id).map(PostMapper::toDomain);
	}

	@Override
	public Optional<Post> findByAuthorIdAndSlug(UUID authorId, String slug) {
		return postRepository.findByAuthorIdAndSlug(authorId, slug).map(PostMapper::toDomain);
    }

	@Override
	public PageResult<Post> findAll(PaginationRequest request) {
		Sort.Direction direction = Sort.Direction.fromString(request.direction());
		PageRequest pageable = PageRequest.of(
			request.page(),
			request.size(),
			Sort.by(direction, request.sortBy())
		);

		Page<PostIdProjection> pagedIds = postRepository.findPagedIds(pageable);
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
	public void delete(UUID id) {
		postRepository.deleteById(id);
	}

}
