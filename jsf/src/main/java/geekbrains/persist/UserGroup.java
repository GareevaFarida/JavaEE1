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
@Table(name = "user_groups")
@Data
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

//    @ManyToMany
//    @JoinTable(name = "usergroups_roles",
//            joinColumns = @JoinColumn(name = "usergroup_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private List<Role> roles;

    @OneToMany(mappedBy = "group")
    private List<User> users;
}
