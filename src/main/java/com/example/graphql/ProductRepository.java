package com.example.graphql;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    Flux<Product> findByNameContaining(String name);
    @Query("SELECT * FROM products")
    Flux<Product> findAllProducts();

    @Query("SELECT table_rows FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = 'products'")
        //like select count(*) from products;
    Mono<Long> countTotalProducts();

    @Query("SELECT * FROM products LIMIT :limit OFFSET :offset")
    Flux<Product> findProductsPaged(int limit, int offset);

    @Query("SELECT * FROM products WHERE id > :cursor ORDER BY id ASC LIMIT :limit")
    Flux<Product> findProductsByPagination(@Param("cursor") Long cursor, @Param("limit") int limit);
}
