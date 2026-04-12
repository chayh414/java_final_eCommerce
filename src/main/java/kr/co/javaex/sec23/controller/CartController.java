package kr.co.javaex.sec23.controller;

import kr.co.javaex.sec23.domain.Cart;
import kr.co.javaex.sec23.domain.Product;
import kr.co.javaex.sec23.service.CartService;
import kr.co.javaex.sec23.service.ProductService;
import kr.co.javaex.sec23.service.UserService;

import java.util.List;
import java.util.Scanner;

public class CartController {

    private CartService cartService;
    private ProductService productService;
    private UserService userService;
    private Scanner scanner;

    public CartController(CartService cartService, ProductService productService, UserService userService, Scanner scanner) {
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
        this.scanner = scanner;
    }

    // 장바구니 메뉴
    public void showMenu() {
        if (!userService.isLogin()) {
            System.out.println("로그인이 필요합니다.");
            return;
        }

        while (true) {
            System.out.println("\n===== 장바구니 =====");
            System.out.println("1. 장바구니 조회");
            System.out.println("2. 상품 담기");
            System.out.println("3. 수량 변경");
            System.out.println("4. 상품 삭제");
            System.out.println("5. 전체 주문");
            System.out.println("6. 장바구니 비우기");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            String input = scanner.nextLine();

            if (input.equals("1")) {
                showCart();
            } else if (input.equals("2")) {
                addCart();
            } else if (input.equals("3")) {
                updateCart();
            } else if (input.equals("4")) {
                removeCart();
            } else if (input.equals("5")) {
                orderAll();
            } else if (input.equals("6")) {
                clearCart();
            } else if (input.equals("0")) {
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }

    // 장바구니 조회
    public void showCart() {
        String userId = userService.getLoginUser().getUserId();
        List<Cart> cartList = cartService.getUserCart(userId);

        System.out.println("\n===== 내 장바구니 =====");

        if (cartList.isEmpty()) {
            System.out.println("장바구니가 비어있습니다.");
            return;
        }

        for (Cart cart : cartList) {
            Product product = productService.findProduct(cart.getProductId());
            if (product != null) {
                System.out.println("장바구니ID: " + cart.getCartId()
                        + " | 상품명: " + product.getProductName()
                        + " | 가격: " + product.getPrice() + "원"
                        + " | 수량: " + cart.getQuantity()
                        + " | 합계: " + (product.getPrice() * cart.getQuantity()) + "원");
            }
        }
    }

    // 상품 담기
    private void addCart() {
        System.out.println("\n===== 상품 담기 =====");

        // 상품 목록 출력
        List<Product> productList = productService.getAllProducts();
        for (Product product : productList) {
            System.out.println("ID: " + product.getProductId()
                    + " | " + product.getProductName()
                    + " | " + product.getPrice() + "원"
                    + " | 재고: " + product.getStockQuantity());
        }

        System.out.print("담을 상품ID: ");
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

        String userId = userService.getLoginUser().getUserId();
        Long cartId = System.currentTimeMillis();

        Cart cart = new Cart(cartId, userId, productId, quantity);
        cartService.addCart(cart);

        System.out.println("장바구니에 담았습니다.");
    }

    // 수량 변경
    private void updateCart() {
        showCart();

        String userId = userService.getLoginUser().getUserId();
        List<Cart> cartList = cartService.getUserCart(userId);

        if (cartList.isEmpty()) return;

        System.out.print("수정할 장바구니ID: ");
        Long cartId = Long.parseLong(scanner.nextLine());

        System.out.print("새 수량: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        // 해당 장바구니 항목 찾아서 수량 변경
        for (Cart cart : cartList) {
            if (cart.getCartId().equals(cartId)) {
                cart.setQuantity(quantity);
                cartService.updateCart(cart);
                System.out.println("수량이 변경되었습니다.");
                return;
            }
        }

        System.out.println("해당 장바구니 항목을 찾을 수 없습니다.");
    }

    // 상품 삭제
    private void removeCart() {
        showCart();

        String userId = userService.getLoginUser().getUserId();
        List<Cart> cartList = cartService.getUserCart(userId);

        if (cartList.isEmpty()) return;

        System.out.print("삭제할 장바구니ID: ");
        Long cartId = Long.parseLong(scanner.nextLine());

        boolean result = cartService.removeCart(cartId);

        if (result) {
            System.out.println("상품이 삭제되었습니다.");
        } else {
            System.out.println("해당 항목을 찾을 수 없습니다.");
        }
    }

    // 전체 주문
    private void orderAll() {
        String userId = userService.getLoginUser().getUserId();
        List<Cart> cartList = cartService.getUserCart(userId);

        if (cartList.isEmpty()) {
            System.out.println("장바구니가 비어있습니다.");
            return;
        }

        showCart();

        System.out.print("장바구니 전체 상품을 주문하시겠습니까? (1.예 / 2.아니오): ");
        String input = scanner.nextLine();

        if (input.equals("1")) {
            cartService.orderAll(userId);
            System.out.println("주문이 완료되었습니다.");
        } else {
            System.out.println("주문이 취소되었습니다.");
        }
    }

    // 장바구니 비우기
    private void clearCart() {
        String userId = userService.getLoginUser().getUserId();

        System.out.print("장바구니를 비우시겠습니까? (1.예 / 2.아니오): ");
        String input = scanner.nextLine();

        if (input.equals("1")) {
            cartService.clearCart(userId);
            System.out.println("장바구니가 비워졌습니다.");
        } else {
            System.out.println("취소되었습니다.");
        }
    }
}