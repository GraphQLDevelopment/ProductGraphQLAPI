package com.example.graphql;

import java.util.List;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

  public Mono<ProductPagination> getProductsWithPagination(int first, String after) {
    Long cursor = (after != null) ? Long.parseLong(after) : 0L;

    Mono<Long> totalCount = productRepository.countTotalProducts();
    Mono<List<Product>> paginatedProducts = productRepository.findProductsByPagination(cursor, first)
        .collectList();

    return Mono.zip(totalCount, paginatedProducts)
        .flatMap(tuple -> {
          long total = tuple.getT1();
          List<Product> products = tuple.getT2();

          if (products.isEmpty()) {
            return Mono.just(new ProductPagination(products, false, null, total));
          }

          Long lastProductId = products.get(products.size() - 1).getId();

          return productRepository.findProductsByPagination(lastProductId, 1)
              .hasElements()
              .map(hasNextPage -> new ProductPagination(products, hasNextPage, String.valueOf(lastProductId), total));
        });
  }

}
