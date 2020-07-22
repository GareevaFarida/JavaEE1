package geekbrains.service.dao;

import geekbrains.persist.UserGroup;
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
public class UserGroupDAO implements Serializable {
    private Long id;

    @NotNull(message = "Name of user group shouldn't be empty")
    private String name;

    public UserGroupDAO(UserGroup userGroup) {
        this.id = userGroup.getId();
        this.name = userGroup.getName();
    }
}
