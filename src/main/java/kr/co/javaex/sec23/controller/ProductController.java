package kr.co.javaex.sec23.controller;

import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.service.ProductService;
import kr.co.javaex.sec23.service.UserService;

import java.util.List;
import java.util.Scanner;

public class ProductController {

    private ProductService productService;
    private UserService userService;
    private Scanner scanner;

    public ProductController(ProductService productService, UserService userService, Scanner scanner) {
        this.productService = productService;
        this.userService = userService;
        this.scanner = scanner;
    }

    // 상품 메뉴
    public void showMenu() {
        while (true) {
            System.out.println("\n===== 상품 관리 =====");
            System.out.println("1. 전체 상품 조회");
            System.out.println("2. 상품 상세 조회");

            // 관리자만 보이는 메뉴
            if (userService.isLogin() && userService.getLoginUser().isAdmin()) {
                System.out.println("3. 상품 등록");
                System.out.println("4. 상품 수정");
                System.out.println("5. 상품 삭제");
                System.out.println("6. 재고 수정");
            }

            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                showAllProducts();
            } else if (input.equals("2")) {
                showProductDetail();
            } else if (input.equals("3")) {
                addProduct();
            } else if (input.equals("4")) {
                updateProduct();
            } else if (input.equals("5")) {
                removeProduct();
            } else if (input.equals("6")) {
                updateStock();
            } else if (input.equals("0")) {
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 전체 상품 조회
    public void showAllProducts() {
        System.out.println("\n===== 전체 상품 목록 =====");

        List<Product> productList = productService.getAllProducts();

        if (productList.isEmpty()) {
            System.out.println("등록된 상품이 없습니다.");
            return;
        }

        for (Product product : productList) {
            System.out.println("ID: " + product.getProductId()
                    + " | " + product.getProductName()
                    + " | " + product.getPrice() + "원"
                    + " | 재고: " + product.getStockQuantity()
                    + " | 상태: " + product.getStatus());
        }
    }

    // 상품 상세 조회
    private void showProductDetail() {
        System.out.println("\n===== 상품 상세 조회 =====");
        showAllProducts();

        System.out.print("조회할 상품ID: ");
        Long productId = Long.parseLong(scanner.nextLine());

        Product product = productService.findProduct(productId);

        if (product == null) {
            System.out.println("해당 상품을 찾을 수 없습니다.");
            return;
        }

        System.out.println("\n----- 상품 상세 정보 -----");
        System.out.println("상품ID: " + product.getProductId());
        System.out.println("상품명: " + product.getProductName());
        System.out.println("설명: " + product.getDescription());
        System.out.println("가격: " + product.getPrice() + "원");
        System.out.println("재고: " + product.getStockQuantity());
        System.out.println("상태: " + product.getStatus());
        System.out.println("카테고리ID: " + product.getCategoryId());
    }

    // 상품 등록 (관리자만)
    private void addProduct() {
        if (!userService.isLogin() || !userService.getLoginUser().isAdmin()) {
            System.out.println("관리자만 접근할 수 있습니다.");
            return;
        }

        System.out.println("\n===== 상품 등록 =====");

        System.out.print("상품ID: ");
        Long productId = Long.parseLong(scanner.nextLine());

        System.out.print("상품명: ");
        String productName = scanner.nextLine();

        System.out.print("상품 설명: ");
        String description = scanner.nextLine();

        System.out.print("가격: ");
        int price = Integer.parseInt(scanner.nextLine());

        System.out.print("재고수량: ");
        int stockQuantity = Integer.parseInt(scanner.nextLine());

        System.out.print("카테고리ID: ");
        Long categoryId = Long.parseLong(scanner.nextLine());

        Product product = new Product(productId, productName, description, price, stockQuantity, "정상", categoryId);
        productService.addProduct(product);

        System.out.println("상품이 등록되었습니다.");
    }

    // 상품 수정 (관리자만)
    private void updateProduct() {
        if (!userService.isLogin() || !userService.getLoginUser().isAdmin()) {
            System.out.println("관리자만 접근할 수 있습니다.");
            return;
        }

        System.out.println("\n===== 상품 수정 =====");
        showAllProducts();

        System.out.print("수정할 상품ID: ");
        Long productId = Long.parseLong(scanner.nextLine());

        Product product = productService.findProduct(productId);

        if (product == null) {
            System.out.println("해당 상품을 찾을 수 없습니다.");
            return;
        }

        System.out.print("새 상품명 (변경 없으면 엔터): ");
        String productName = scanner.nextLine();

        System.out.print("새 설명 (변경 없으면 엔터): ");
        String description = scanner.nextLine();

        System.out.print("새 가격 (변경 없으면 엔터): ");
        String priceInput = scanner.nextLine();

        System.out.print("상태 (1.정상 / 2.판매중지 / 변경 없으면 엔터): ");
        String statusInput = scanner.nextLine();

        if (!productName.isEmpty()) product.setProductName(productName);
        if (!description.isEmpty()) product.setDescription(description);
        if (!priceInput.isEmpty()) product.setPrice(Integer.parseInt(priceInput));
        if (statusInput.equals("1")) product.setStatus("정상");
        if (statusInput.equals("2")) product.setStatus("판매중지");

        productService.updateProduct(product);
        System.out.println("상품이 수정되었습니다.");
    }

    // 상품 삭제 (관리자만)
    private void removeProduct() {
        if (!userService.isLogin() || !userService.getLoginUser().isAdmin()) {
            System.out.println("관리자만 접근할 수 있습니다.");
            return;
        }

        System.out.println("\n===== 상품 삭제 =====");
        showAllProducts();

        System.out.print("삭제할 상품ID: ");
        Long productId = Long.parseLong(scanner.nextLine());

        boolean result = productService.removeProduct(productId);

        if (result) {
            System.out.println("상품이 삭제되었습니다.");
        } else {
            System.out.println("해당 상품을 찾을 수 없습니다.");
        }
    }

    // 재고 수정 (관리자만)
    private void updateStock() {
        if (!userService.isLogin() || !userService.getLoginUser().isAdmin()) {
            System.out.println("관리자만 접근할 수 있습니다.");
            return;
        }

        System.out.println("\n===== 재고 수정 =====");
        showAllProducts();

        System.out.print("재고 수정할 상품ID: ");
        Long productId = Long.parseLong(scanner.nextLine());

        Product product = productService.findProduct(productId);

        if (product == null) {
            System.out.println("해당 상품을 찾을 수 없습니다.");
            return;
        }

        System.out.println("현재 재고: " + product.getStockQuantity());
        System.out.print("새 재고수량: ");
        int stockQuantity = Integer.parseInt(scanner.nextLine());

        product.setStockQuantity(stockQuantity);

        // 재고가 0이면 품절 처리
        if (stockQuantity == 0) {
            product.setStatus("품절");
            System.out.println("재고가 0이므로 품절 처리되었습니다.");
        }

        productService.updateProduct(product);
        System.out.println("재고가 수정되었습니다.");
    }
}