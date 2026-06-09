package com.joaolive.javanews.core;

import java.util.List;
import java.util.function.Function;

public record PageResult<T>(
	List<T> data,
	int totalPages,
	long totalElements,
	int currentPage,
	int pageSize
) {
	public <R> PageResult<R> map(Function<T, R> mapper) {
		List<R> mappedData = this.data.stream().map(mapper).toList();
		return new PageResult<>(
			mappedData,
			this.totalPages,
			this.totalElements,
			this.currentPage,
			this.pageSize
		);
	}
}
