package com.joaolive.javanews.post.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.joaolive.javanews.post.application.port.out.PostRepositoryPort;
import com.joaolive.javanews.post.domain.model.Post;

@Repository
public class PostRepositoryAdapter implements PostRepositoryPort {
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
	public List<Post> findPostsByAuthorId(UUID id) {
		// TODO Unimplemented method 'findPostsByAuthorId'
		return null;
	}

	@Override
	public void delete(UUID id) {
		postRepository.deleteById(id);
	}

}
