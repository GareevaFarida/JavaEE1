package geekbrains.service;

import geekbrains.persist.User;
import geekbrains.persist.repo.RoleRepositoryJPA;
import geekbrains.persist.repo.UserGroupRepositoryJPA;
import geekbrains.persist.repo.UserRepositoryJPA;
import geekbrains.service.dao.UserDAO;
import org.hibernate.exception.ConstraintViolationException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Farida Gareeva
 * Created 15.07.2020
 * v1.0
 */
@Stateless
public class UserServiceImpl implements UserService {

    @EJB
    private UserRepositoryJPA userRepositoryJPA;

    @EJB
    private UserGroupRepositoryJPA userGroupRepositoryJPA;

    @EJB
    private RoleRepositoryJPA roleRepositoryJPA;

    @Override
    public void delete(Long id) {
        userRepositoryJPA.delete(id);
    }

    @Override
    public List<UserDAO> findAll() {
        return userRepositoryJPA.findAll()
                .stream()
                .map(UserDAO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDAO findById(long id) {
        User user = userRepositoryJPA.findById(id);
        if (user == null)
            return null;

        return new UserDAO(user);
    }

    @Override
    public void insert(UserDAO userDAO) {
        if (userDAO == null)
            throw new IllegalArgumentException("Impossible to insert user, because it's equals NULL!");

        User user = new User();
        fillUserByUserDAO(user, userDAO);
        try {
            userRepositoryJPA.insert(user);
        } catch (ConstraintViolationException ex) {
            throw new IllegalArgumentException("Impossible to insert user with name = '"
                    + userDAO.getName() + "' because user with the same name already exists.");
        }
    }

    private void fillUserByUserDAO(User user, UserDAO userDAO) {
        user.setName(userDAO.getName());
        user.setPassword(userDAO.getPassword());
        user.setGroup(userDAO.getUserGroupId() == null ? null : userGroupRepositoryJPA.findById(userDAO.getUserGroupId()));
        user.setRoles(userDAO.getRoles().stream()
                .map(roleDAO -> roleRepositoryJPA.findById(roleDAO.getId()))
                .collect(Collectors.toList()));
    }

    @Override
    public void update(UserDAO userDAO) {
        if (userDAO == null)
            throw new IllegalArgumentException("Impossible to update user, because it equals NULL!");

        User user = userRepositoryJPA.findById(userDAO.getId());
        if (user == null)
            throw new IllegalArgumentException("Impossible to update user, because user with id = '"
                    + userDAO.getId() + "' doesn't exist!");

        fillUserByUserDAO(user, userDAO);
        try {
            userRepositoryJPA.update(user);
        } catch (ConstraintViolationException ex) {
            throw new IllegalArgumentException("Impossible to change name of user to '"
                    + userDAO.getName() + "' because user with the same name already exists.");
        }
    }

}
