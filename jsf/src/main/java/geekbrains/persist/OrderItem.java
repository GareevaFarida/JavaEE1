package geekbrains.persist;

import geekbrains.persist.Product;
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
    private Long price;

    @Column(nullable = false)
    private int count;

    @Column
    private int total;

    @ManyToOne
    private Order order;

}
