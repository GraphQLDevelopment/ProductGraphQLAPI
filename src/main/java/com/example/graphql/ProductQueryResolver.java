package com.example.graphql;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ProductQueryResolver {

    private final ProductRepository productRepository;
    private final ProductService  productService;

    public ProductQueryResolver(ProductRepository productRepository,ProductService productService) {
        this.productRepository = productRepository;
        this.productService=productService;
    }


    @QueryMapping
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
        /*return productRepository.findAllProducts()
            .doOnNext(product -> System.out.println("Fetched product: " + product))
            .doOnError(error -> System.err.println("Error fetching products: " + error.getMessage()));*/
    }


    @QueryMapping
    public Mono<Product> getProductById(@Argument("id") Long id) {  // ✅ Explicitly specify GraphQL argument
        return productRepository.findById(id);
    }


    @QueryMapping
    public Mono<String> healthCheck() {
        return productRepository.count()
            .map(count -> "Database Connected Successfully! Total Products: " + count)
            .onErrorReturn("Database Connection Failed!");
    }
    @QueryMapping
    public Mono<ProductPagination> getProductsWithPagination(@Argument int first, @Argument String after) {
        return productService.getProductsWithPagination(first, after);
    }

}
