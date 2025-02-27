package com.example.graphql;

import java.util.List;

public record ProductPagination(
    List<Product> products,
    boolean hasNextPage,
    String endCursor,
    long total
) {}
