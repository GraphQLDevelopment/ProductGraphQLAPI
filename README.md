MethodType: POST, URL: http://localhost:8080/graphql
Body: {
    "query": "query { getProductById(id: 1) { id name price } }"
}

Method Type: POST URL: http://localhost:8080/graphql
Body: {
    "query": "query { getAllProducts { id name price } }"
}

MethoType: POST URL: http://localhost:8080/graphql
Body: {
    "query": "mutation { createProduct(name: \"TV\", price: 899.99) { id name price } }"
}

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL
);
