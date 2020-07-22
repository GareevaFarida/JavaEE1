package geekbrains.service.dao;

import geekbrains.persist.ClientOrder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
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

}
