package com.example.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class ProductMutationResolver {

    private final ProductRepository productRepository;

    public ProductMutationResolver(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @MutationMapping
    public Mono<Product> createProduct(
        @Argument("name") String name,
        @Argument("price") Double price
    ) {
        Product product = new Product(null, name, price);
        return productRepository.save(product);
    }
}
