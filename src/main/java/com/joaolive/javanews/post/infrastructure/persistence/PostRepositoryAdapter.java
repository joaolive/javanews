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
		postRepository.save(entity);
		return PostMapper.toDomain(entity);
	}

	@Override
	public Optional<Post> findById(UUID id) {
		return postRepository.findById(id).map(PostMapper::toDomain);
	}

	@Override
	public PageResult<Post> findAll(PaginationRequest request) {
		Sort.Direction direction = Sort.Direction.fromString(request.direction());
		PageRequest pageable = PageRequest.of(
			request.page(),
			request.size(),
			Sort.by(direction, request.sortBy())
		);
		Page<PostEntity> entityPage = postRepository.findAll(pageable);
		List<Post> domainPosts = entityPage.getContent().stream()
			.map(PostMapper::toDomain)
			.toList();
		return new PageResult<>(
			domainPosts,
			entityPage.getTotalPages(),
			entityPage.getTotalElements(),
			entityPage.getNumber(),
			entityPage.getSize()
		);
	}

	@Override
	public List<Post> findPostsByAuthorId(UUID id) {
		// TODO Unimplemented method 'findPostsByAuthorId'
		return null;
	}

	@Override
	public void delete(UUID id) {
		postRepository.deleteById(id);
	}

}
