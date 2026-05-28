package com.joaolive.javanews.post.domain.valueobject;

import java.text.Normalizer;
import java.util.regex.Pattern;

import com.joaolive.javanews.post.domain.exception.PostDomainValidationException;

public record Slug(String value) {
	public Slug {
		if (value == null || value.isBlank())
			throw new PostDomainValidationException("Slug cannot be null or blank");
	}

	public static Slug create(String value) {
		String normalized = Normalizer.normalize(value, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		String slugValue = pattern.matcher(normalized)
				.replaceAll("")
				.toLowerCase()
				.replaceAll("[^a-z0-9]+", "-")
				.replaceAll("^-|-$", "");
		
		return new Slug(slugValue);
	}

	public static Slug restore(String value) {
		return new Slug(value);
	}
}
