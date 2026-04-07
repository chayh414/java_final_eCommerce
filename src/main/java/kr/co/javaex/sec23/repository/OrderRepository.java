package kr.co.javaex.sec23.repository;

import kr.co.javaex.sec23.domain.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private List<Order> orderList = new ArrayList<>();

    // 주문 추가
    public void addOrder(Order order) {
        orderList.add(order);
    }

    // 전체 주문 조회
    public List<Order> getAllOrders() {
        return orderList;
    }

    // 주문 ID로 찾기
    public Order findByOrderId(Long orderId) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    // 특정 사용자의 주문 목록 조회
    public List<Order> findByUserId(String userId) {
        List<Order> userOrderList = new ArrayList<>();

        for (Order order : orderList) {
            if (order.getUserId().equals(userId)) {
                userOrderList.add(order);
            }
        }
        return userOrderList;
    }
}