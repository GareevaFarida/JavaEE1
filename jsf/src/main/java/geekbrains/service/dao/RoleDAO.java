package geekbrains.service.dao;

import geekbrains.persist.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author Farida Gareeva
 * Created 13.07.2020
 * v1.0
 */
@Data
@NoArgsConstructor
public class RoleDAO implements Serializable {

    private Long id;

    @NotNull(message = "Name of role should't be empty!")
    private String name;

    public RoleDAO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    @Override
    public String toString() {
        return name;
    }
}
