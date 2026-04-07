package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Cart;
import kr.co.javaex.sec23.domain.Order;
import kr.co.javaex.sec23.repository.CartRepository;
import kr.co.javaex.sec23.repository.OrderRepository;

import java.util.List;

public class CartService {

    private CartRepository cartRepository;
    private OrderRepository orderRepository;

    public CartService(CartRepository cartRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    // 장바구니 담기
    public void addCart(Cart cart) {
        cartRepository.addCart(cart);
    }

    // 장바구니 조회
    public List<Cart> getUserCart(String userId) {
        return cartRepository.findByUserId(userId);
    }

    // 수량 변경
    public boolean updateCart(Cart cart) {
        return cartRepository.updateCart(cart);
    }

    // 삭제
    public boolean removeCart(Long cartId) {
        return cartRepository.removeCart(cartId);
    }

    // 전체 비우기
    public void clearCart(String userId) {
        cartRepository.clearCartByUserId(userId);
    }

    // 전체 주문
    public void orderAll(String userId) {
        List<Cart> cartList = cartRepository.findByUserId(userId);

        for (Cart cart : cartList) {
            Order order = new Order(
                    System.currentTimeMillis(),
                    cart.getUserId(),
                    cart.getProductId(),
                    cart.getQuantity(),
                    0,
                    "주문완료"
            );

            orderRepository.addOrder(order);
        }

        cartRepository.clearCartByUserId(userId);
    }
}