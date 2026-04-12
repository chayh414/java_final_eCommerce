package kr.co.javaex.sec23.repository;

import kr.co.javaex.sec23.domain.Cart;
import kr.co.javaex.sec23.util.JsonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CartRepository {

    private List<Cart> cartList = new ArrayList<>();
    private JsonUtil jsonUtil = new JsonUtil();
    private String fileName = "carts.json";

    // 🔥 추가: 생성자 (파일 읽기)
    public CartRepository() {
        Cart[] arr = jsonUtil.load(fileName, Cart[].class);
        if (arr != null) {
            cartList = new ArrayList<>(Arrays.asList(arr));
        }
    }

    // 장바구니 추가
    public void addCart(Cart cart) {
        cartList.add(cart);
        jsonUtil.save(fileName, cartList); // 🔥 추가
    }

    // 전체 장바구니 조회
    public List<Cart> getAllCarts() {
        return cartList;
    }

    // 장바구니 ID로 찾기
    public Cart findByCartId(Long cartId) {
        for (Cart cart : cartList) {
            if (cart.getCartId().equals(cartId)) {
                return cart;
            }
        }
        return null;
    }

    // 특정 사용자 장바구니 목록 조회
    public List<Cart> findByUserId(String userId) {
        List<Cart> userCartList = new ArrayList<>();

        for (Cart cart : cartList) {
            if (cart.getUserId().equals(userId)) {
                userCartList.add(cart);
            }
        }
        return userCartList;
    }

    // 장바구니 수정(수량 변경용)
    public boolean updateCart(Cart updatedCart) {
        for (int i = 0; i < cartList.size(); i++) {
            Cart cart = cartList.get(i);

            if (cart.getCartId().equals(updatedCart.getCartId())) {
                cartList.set(i, updatedCart);
                jsonUtil.save(fileName, cartList); // 🔥 추가
                return true;
            }
        }
        return false;
    }

    // 장바구니 한 건 삭제
    public boolean removeCart(Long cartId) {
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getCartId().equals(cartId)) {
                cartList.remove(i);
                jsonUtil.save(fileName, cartList); // 🔥 추가
                return true;
            }
        }
        return false;
    }

    // 특정 사용자 장바구니 전체 비우기
    public boolean clearCartByUserId(String userId) {
        boolean removed = false;

        for (int i = cartList.size() - 1; i >= 0; i--) {
            if (cartList.get(i).getUserId().equals(userId)) {
                cartList.remove(i);
                removed = true;
            }
        }

        if (removed) {
            jsonUtil.save(fileName, cartList); //
        }

        return removed;
    }
}