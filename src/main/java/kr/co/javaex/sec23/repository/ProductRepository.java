package kr.co.javaex.sec23.repository;

import kr.co.javaex.sec23.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    // 1. Product 객체들을 저장할 리스트
    private List<Product> productList = new ArrayList<>();

    // 2. 상품 등록
    public void addProduct(Product product) {

        productList.add(product);
    }

    // 3. 전체 상품 조회(상품 리스트 전체 보기)
    public List<Product> getAllProducts() {

        return productList;
    }

    // 4. 상품 찾기(특정 상품)
    public Product findByProductId(Long productId) {
        for(Product product : productList) {
            if(product.getProductId().equals(productId)){
                return product;
            }
        }
        return null;  // -> 못 찾으면 null 반환
    }

    // 5. 상품 삭제
    public boolean removeProduct(Long productId) {
        for(Product product : productList) {
            if (product.getProductId().equals(productId)) {
                productList.remove(product);
                return true;
            }
        }
        return false;
    }
}
