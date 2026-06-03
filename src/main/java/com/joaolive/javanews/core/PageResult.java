package com.joaolive.javanews.core;

import java.util.List;

public record PageResult<T>(
	List<T> data,
	int totalPages,
	long totalElements,
	int currentPage,
	int pageSize
) {}
