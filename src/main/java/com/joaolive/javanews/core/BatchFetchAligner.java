package com.joaolive.javanews.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class BatchFetchAligner {
	private BatchFetchAligner() {}
	public static <ID, T extends Identifiable<ID>> List<T> align(
		List<? extends Identifiable<ID>> orderedReference,
		List<T> unorderedItems
	) {
		Map<ID, T> itemMap = unorderedItems.stream()
			.collect(Collectors.toMap(Identifiable::getId, x -> x));
		return orderedReference.stream()
			.map(x -> itemMap.get(x.getId()))
			.filter(Objects::nonNull)
			.toList();
	}
}
