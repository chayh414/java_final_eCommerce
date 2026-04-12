package kr.co.javaex.sec23.repository;

import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private List<Product> productList = new ArrayList<>();
    private JsonUtil jsonUtil = new JsonUtil();
    private String fileName = "products.json";

    // 상품 등록
    public void addProduct(Product product) {
        productList.add(product);
    }

    // 전체 상품 조회
    public List<Product> getAllProducts() {
        return productList;
    }

    // 상품 ID로 찾기
    public Product findByProductId(Long productId) {
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    // 상품 수정
    public boolean updateProduct(Product updatedProduct) {
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);

            if (product.getProductId().equals(updatedProduct.getProductId())) {
                productList.set(i, updatedProduct);
                return true;
            }
        }
        return false;
    }

    // 상품 삭제
    public boolean removeProduct(Long productId) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductId().equals(productId)) {
                productList.remove(i);
                return true;
            }
        }
        return false;
    }
}