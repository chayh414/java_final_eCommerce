package kr.co.javaex.sec23.repository;

import kr.co.javaex.sec23.domain.Order;
import kr.co.javaex.sec23.util.JsonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderRepository {

    private List<Order> orderList = new ArrayList<>();
    private JsonUtil jsonUtil = new JsonUtil();
    private String fileName = "orders.json";

    public OrderRepository() {
        Order[] arr = jsonUtil.load(fileName, Order[].class);
        if (arr != null) {
            orderList = new ArrayList<>(Arrays.asList(arr));
        }
    }

    public void addOrder(Order order) {
        orderList.add(order);
        jsonUtil.save(fileName, orderList);
    }

    public List<Order> getAllOrders() {
        return orderList;
    }

    public Order findByOrderId(Long orderId) {
        for (Order order : orderList) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

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