package com.joaolive.javanews.common;

public record PaginationRequest(
	int page,
	int size,
	String sortBy,
	String direction
) {
	public PaginationRequest {
		page = Math.max(0, page);
		size = (size > 50) ? 50 : Math.max(1, size);
		sortBy = (sortBy == null || sortBy.isBlank()) ? "createdAt" : sortBy;
		direction = "asc".equalsIgnoreCase(direction) ? "asc" : "desc";
	}
}
