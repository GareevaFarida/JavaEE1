package geekbrains.service.dao;

import geekbrains.persist.ClientOrder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDAO implements Serializable {

    private Long id;

    private LocalDateTime created;

    private String phoneNumber;

    private int total;

    private List<OrderItemDAO> items;

    public OrderDAO() {
    }

    public OrderDAO(Long id, LocalDateTime created, String phoneNumber, int total) {
        this.id = id;
        this.created = created;
        this.phoneNumber = phoneNumber;
        this.total = total;
    }

    public OrderDAO(ClientOrder order) {
        this.id = order.getId();
        this.created = order.getCreated();
        this.phoneNumber = order.getPhoneNumber();
        this.total = order.getTotal();
        this.setItems(order.getItems()
                .stream()
                .map(OrderItemDAO::new)
                .collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OrderItemDAO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDAO> items) {
        this.items = items;
    }
}
