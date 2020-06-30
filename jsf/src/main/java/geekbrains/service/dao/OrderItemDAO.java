package geekbrains.service.dao;

import geekbrains.ItemCart;
import geekbrains.persist.OrderItem;

import java.io.Serializable;

public class OrderItemDAO implements Serializable {
    private Long id;

    private Long productId;

    private String productName;

    private int price;

    private int count;

    private int total;

    private Long orderId;

    public OrderItemDAO() {
    }

    public OrderItemDAO(ItemCart item,Long orderId){
        this.productId = item.getProduct().getId();
        this.productName = item.getProduct().getName();
        this.price = item.getPrice();
        this.count = item.getCount();
        this.orderId = orderId;
        this.total = item.getPrice()*item.getCount();
    }

    public OrderItemDAO(OrderItem item){
        this.productId = item.getProduct().getId();
        this.productName = item.getProduct().getName();
        this.price = item.getPrice();
        this.count = item.getCount();
        this.orderId = item.getOrder().getId();
        this.total = item.getTotal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderItemDAO(Long id, Long productId, String productName, int price, int count, int total, Long orderId) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.count = count;
        this.total = total;
        this.orderId = orderId;
    }
}
