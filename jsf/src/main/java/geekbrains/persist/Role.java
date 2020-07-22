package geekbrains.persist;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author Farida Gareeva
 * Created 13.07.2020
 * v1.0
 */
@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;
}
