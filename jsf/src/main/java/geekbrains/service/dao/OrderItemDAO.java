package geekbrains.service.dao;

import geekbrains.ItemCart;
import geekbrains.persist.OrderItem;
import lombok.Data;

import java.io.Serializable;

@Data
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

}
