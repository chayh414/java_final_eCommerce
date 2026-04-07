package kr.co.javaex.sec23.controller;

import kr.co.javaex.sec23.domain.Order;
import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.service.OrderService;
import kr.co.javaex.sec23.service.ProductService;
import kr.co.javaex.sec23.service.UserService;

import java.util.List;
import java.util.Scanner;

public class OrderController {

    private OrderService orderService;
    private ProductService productService;
    private UserService userService;
    private Scanner scanner;

    public OrderController(OrderService orderService, ProductService productService, UserService userService, Scanner scanner) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
        this.scanner = scanner;
    }

    // 주문 메뉴
    public void showMenu() {
        if (!userService.isLogin()) {
            System.out.println("로그인이 필요합니다.");
            return;
        }

        while (true) {
            System.out.println("\n===== 주문 메뉴 =====");
            System.out.println("1. 내 주문 목록 조회");
            System.out.println("2. 상품 바로 주문");

            // 관리자만 보이는 메뉴
            if (userService.getLoginUser().isAdmin()) {
                System.out.println("3. 전체 주문 조회 (관리자)");
            }

            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                showMyOrders();
            } else if (input.equals("2")) {
                orderProduct();
            } else if (input.equals("3")) {
                showAllOrders();
            } else if (input.equals("0")) {
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 내 주문 목록 조회
    private void showMyOrders() {
        String userId = userService.getLoginUser().getUserId();
        List<Order> orderList = orderService.getUserOrders(userId);

        System.out.println("\n===== 내 주문 목록 =====");

        if (orderList.isEmpty()) {
            System.out.println("주문 내역이 없습니다.");
            return;
        }

        for (Order order : orderList) {
            Product product = productService.findProduct(order.getProductId());
            String productName = product != null ? product.getProductName() : "상품정보없음";

            System.out.println("주문ID: " + order.getOrderId()
                    + " | 상품명: " + productName
                    + " | 수량: " + order.getQuantity()
                    + " | 주문금액: " + order.getOrderPrice() + "원"
                    + " | 상태: " + order.getOrderStatus());
        }
    }

    // 상품 바로 주문
    private void orderProduct() {
        System.out.println("\n===== 상품 바로 주문 =====");

        // 상품 목록 출력
        List<Product> productList = productService.getAllProducts();
        for (Product product : productList) {
            System.out.println("ID: " + product.getProductId()
                    + " | " + product.getProductName()
                    + " | " + product.getPrice() + "원"
                    + " | 재고: " + product.getStockQuantity());
        }

        System.out.print("주문할 상품ID: ");
        Long productId = Long.parseLong(scanner.nextLine());

        Product product = productService.findProduct(productId);

        if (product == null) {
            System.out.println("해당 상품을 찾을 수 없습니다.");
            return;
        }

        if (product.getStatus().equals("판매중지") || product.getStatus().equals("품절")) {
            System.out.println("판매중지 또는 품절된 상품입니다.");
            return;
        }

        System.out.print("수량: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        if (quantity > product.getStockQuantity()) {
            System.out.println("재고가 부족합니다. 현재 재고: " + product.getStockQuantity());
            return;
        }

        int orderPrice = product.getPrice() * quantity;

        System.out.println("주문 상품: " + product.getProductName());
        System.out.println("주문 금액: " + orderPrice + "원");
        System.out.print("주문하시겠습니까? (1.예 / 2.아니오): ");
        String input = scanner.nextLine();

        if (input.equals("1")) {
            String userId = userService.getLoginUser().getUserId();
            Long orderId = System.currentTimeMillis();

            Order order = new Order(orderId, userId, productId, quantity, orderPrice, "주문완료");
            orderService.addOrder(order);

            // 재고 감소
            product.setStockQuantity(product.getStockQuantity() - quantity);
            if (product.getStockQuantity() == 0) {
                product.setStatus("품절");
            }
            productService.updateProduct(product);

            System.out.println("주문이 완료되었습니다.");
        } else {
            System.out.println("주문이 취소되었습니다.");
        }
    }

    // 전체 주문 조회 (관리자만)
    private void showAllOrders() {
        if (!userService.getLoginUser().isAdmin()) {
            System.out.println("관리자만 접근할 수 있습니다.");
            return;
        }

        List<Order> orderList = orderService.getAllOrders();

        System.out.println("\n===== 전체 주문 목록 =====");

        if (orderList.isEmpty()) {
            System.out.println("주문 내역이 없습니다.");
            return;
        }

        for (Order order : orderList) {
            Product product = productService.findProduct(order.getProductId());
            String productName = product != null ? product.getProductName() : "상품정보없음";

            System.out.println("주문ID: " + order.getOrderId()
                    + " | 회원ID: " + order.getUserId()
                    + " | 상품명: " + productName
                    + " | 수량: " + order.getQuantity()
                    + " | 주문금액: " + order.getOrderPrice() + "원"
                    + " | 상태: " + order.getOrderStatus());
        }
    }
}