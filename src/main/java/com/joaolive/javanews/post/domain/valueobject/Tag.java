package com.joaolive.javanews.post.domain.valueobject;

import java.text.Normalizer;
import java.util.regex.Pattern;

import com.joaolive.javanews.post.domain.exception.PostDomainValidationException;

public record Tag(String value) {
	public Tag {
		if (value == null || value.isBlank())
			throw new PostDomainValidationException("Tag cannot be null or blank");
		if (value.length() > 32)
			throw new PostDomainValidationException("Tag must be between 1 and 32 characters");
	}

	public static Tag create(String value) {
		String normalized = Normalizer.normalize(value, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		String tagValue = pattern.matcher(normalized)
				.replaceAll("")
				.toLowerCase()
				.replaceAll("[^a-z0-9]+", "-")
				.replaceAll("^-|-$", "");
		
		return new Tag(tagValue);
	}

	public static Tag restore(String value) {
		return new Tag(value);
	}
}
