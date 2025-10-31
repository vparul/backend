package com.ecommerce.product_service.service;

import com.ecommerce.product_service.DTO.ProductRequest;
import com.ecommerce.product_service.DTO.ProductResponse;
import com.ecommerce.product_service.model.Product;
import com.ecommerce.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        productRepository.save(getProductFromProductRequest(product, productRequest));
        return mapProductResponseFromProduct(product);
    }

    public Boolean updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    getProductFromProductRequest(existingProduct, productRequest);
                    productRepository.save(existingProduct);
                    return true;
                })
                .orElseGet(() -> false);
    }

    public List<ProductResponse> fetchAllProducts() {
        return productRepository.findByActiveTrue().stream().map(this::mapProductResponseFromProduct).toList();
    }

    public boolean deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(existingProduct -> {
            existingProduct.setActive(false);
            productRepository.save(existingProduct);
            return true;
        }).orElseGet(() -> false);

    }

    public List<ProductResponse> searchProduct(String keyword) {
        return productRepository.searchProduct(keyword).stream().map(this::mapProductResponseFromProduct).toList();
    }


    private Product getProductFromProductRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
        return product;
    }

    private ProductResponse mapProductResponseFromProduct(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setStockQuantity(product.getStockQuantity());
        productResponse.setCategory(product.getCategory());
        productResponse.setImageUrl(product.getImageUrl());
        productResponse.setActive(product.getActive());
        return productResponse;
    }
}
