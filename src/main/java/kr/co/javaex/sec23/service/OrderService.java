package kr.co.javaex.sec23.service;

import kr.co.javaex.sec23.domain.Order;
import kr.co.javaex.sec23.repository.OrderRepository;

import java.util.List;

public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // 주문 추가
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    // 전체 주문 조회
    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    // 사용자 주문 조회
    public List<Order> getUserOrders(String userId) {
        return orderRepository.findByUserId(userId);
    }
}