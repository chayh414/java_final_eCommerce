package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.repository.ProductRepository;

import java.util.List;

public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 상품 추가
    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    // 전체 상품 조회
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    // 상품 찾기
    public Product findProduct(Long productId) {
        return productRepository.findByProductId(productId);
    }

    // 상품 수정
    public boolean updateProduct(Product product) {
        return productRepository.updateProduct(product);
    }

    // 상품 삭제
    public boolean removeProduct(Long productId) {
        return productRepository.removeProduct(productId);
    }
}