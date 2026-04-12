package kr.co.javaex.sec23.domain;

public class Order {

    private Long orderId;         // 주문
    private String userId;
    private Long productId;
    private int quantity;
    private int orderPrice;
    private String orderStatus;   // 주문 상태

    public Order() {
    }

    public Order(Long orderId, String userId, Long productId, int quantity, int orderPrice, String orderStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId='" + userId + '\'' +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", orderPrice=" + orderPrice +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}