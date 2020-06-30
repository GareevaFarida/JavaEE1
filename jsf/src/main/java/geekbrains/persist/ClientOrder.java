package geekbrains.persist;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @Author Farida Gareeva
 * Created 24.06.2020
 */
@Entity
@Data
@Table
public class ClientOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime created;

    @Column
    private String phoneNumber;

    @Column
    private int total;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> items;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", created=" + created +
                ", total=" + total +
                '}';
    }
}
