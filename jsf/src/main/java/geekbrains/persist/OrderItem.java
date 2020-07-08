package geekbrains.persist;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int count;

    @Column
    private int total;

    @ManyToOne
    private ClientOrder order;

    public OrderItem(Product product, int count, int price, ClientOrder order) {
        this.product = product;
        this.count = count;
        this.price = price;
        this.total = count*price;
        this.order = order;
    }
}
