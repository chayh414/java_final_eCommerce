package kr.co.javaex.sec23;

import kr.co.javaex.sec23.controller.*;
import kr.co.javaex.sec23.domain.*;
import kr.co.javaex.sec23.repository.*;
import kr.co.javaex.sec23.service.*;
import kr.co.javaex.sec23.util.JsonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Ecommerce {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        JsonUtil jsonUtil = new JsonUtil();

        // Repository 생성
        UserRepository userRepository = new UserRepository();
        CategoryRepository categoryRepository = new CategoryRepository();
        ProductRepository productRepository = new ProductRepository();
        CartRepository cartRepository = new CartRepository();
        OrderRepository orderRepository = new OrderRepository();

        // JSON 파일에서 데이터 불러오기
        User[] users = jsonUtil.load("use.json", User[].class);
        if (users != null) {
            for (User user : users) {
                userRepository.addUser(user);
            }
        }

        Category[] categories = jsonUtil.load("categories.json", Category[].class);
        if (categories != null) {
            for (Category category : categories) {
                categoryRepository.addCategory(category);
            }
        }

        Product[] products = jsonUtil.load("products.json", Product[].class);
        if (products != null) {
            for (Product product : products) {
                productRepository.addProduct(product);
            }
        }

        Cart[] carts = jsonUtil.load("carts.json", Cart[].class);
        if (carts != null) {
            for (Cart cart : carts) {
                cartRepository.addCart(cart);
            }
        }

        Order[] orders = jsonUtil.load("orders.json", Order[].class);
        if (orders != null) {
            for (Order order : orders) {
                orderRepository.addOrder(order);
            }
        }

        // Service 생성
        UserService userService = new UserService(userRepository);
        CategoryService categoryService = new CategoryService(categoryRepository);
        ProductService productService = new ProductService(productRepository);
        CartService cartService = new CartService(cartRepository, orderRepository);
        OrderService orderService = new OrderService(orderRepository);

        // Controller 생성
        UserController userController = new UserController(userService, sc);
        CategoryController categoryController = new CategoryController(categoryService, userService, sc);
        ProductController productController = new ProductController(productService, userService, sc);
        CartController cartController = new CartController(cartService, productService, userService, sc);
        OrderController orderController = new OrderController(orderService, productService, userService, sc);

        // 메인 메뉴
        while (true) {
            System.out.println("\n===== 쇼핑몰 프로그램 =====");

            // 로그인 상태 표시
            if (userService.isLogin()) {
                System.out.println("[ " + userService.getLoginUser().getUserName() + "님 로그인 중 ]");
            }

            System.out.println("1. 회원 메뉴");
            System.out.println("2. 상품 보기");
            System.out.println("3. 장바구니");
            System.out.println("4. 주문");

            // 관리자만 보이는 메뉴
            if (userService.isLogin() && userService.getLoginUser().isAdmin()) {
                System.out.println("5. 카테고리 관리 (관리자)");
            }

            System.out.println("0. 종료");
            System.out.print("선택: ");

            String input = sc.nextLine();

            if (input.equals("1")) {
                userController.showMenu();

            } else if (input.equals("2")) {
                productController.showMenu();

            } else if (input.equals("3")) {
                cartController.showMenu();

            } else if (input.equals("4")) {
                orderController.showMenu();

            } else if (input.equals("5")) {
                categoryController.showMenu();

            } else if (input.equals("0")) {
                // 종료 전 데이터 저장
                jsonUtil.save("use.json", userRepository.getAllUsers());
                jsonUtil.save("categories.json", categoryRepository.getAllCategories());
                jsonUtil.save("products.json", productRepository.getAllProducts());
                jsonUtil.save("carts.json", cartRepository.getAllCarts());
                jsonUtil.save("orders.json", orderRepository.getAllOrders());

                System.out.println("데이터를 저장하고 종료합니다.");
                break;

            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }

        sc.close();
    }
}