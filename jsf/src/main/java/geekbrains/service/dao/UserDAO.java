package geekbrains.service.dao;

import geekbrains.persist.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Farida Gareeva
 * Created 13.07.2020
 * v1.0
 */
@Data
public class UserDAO implements Serializable {

    private Long id;

    @NotNull(message = "Login shouldn't be empty")
    private String login;

    private Long userGroupId;

    private String userGroupName;

    private String password;

    private String password_match;

    private List<RoleDAO> roles;

    public UserDAO() {
        this.roles = new ArrayList<>();
    }

    public UserDAO(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        if (user.getGroup() != null) {
            this.userGroupId = user.getGroup().getId();
            this.userGroupName = user.getGroup().getName();
        }
        this.setRoles(user.getRoles().stream()
                .map(RoleDAO::new)
                .collect(Collectors.toList()));
    }
}
