package geekbrains.persist;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 13.07.2020
 * v1.0
 */
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    private UserGroup group;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
}
