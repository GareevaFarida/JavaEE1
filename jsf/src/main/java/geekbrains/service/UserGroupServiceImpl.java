package geekbrains.service;

import geekbrains.persist.UserGroup;
import geekbrains.persist.repo.UserGroupRepositoryJPA;
import geekbrains.service.dao.UserDAO;
import geekbrains.service.dao.UserGroupDAO;
import org.hibernate.exception.ConstraintViolationException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Farida Gareeva
 * Created 15.07.2020
 * v1.0
 */
@Stateless
public class UserGroupServiceImpl implements UserGroupService {

    @Inject
    private UserGroupRepositoryJPA userGroupRepositoryJPA;

    @Override
    public void delete(Long id) {
        userGroupRepositoryJPA.delete(id);
    }

    @Override
    public List<UserGroupDAO> findAll() {
        return userGroupRepositoryJPA.findAll()
                .stream()
                .map(UserGroupDAO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserGroupDAO findById(long id) {
        UserGroup group = userGroupRepositoryJPA.findById(id);
        return group == null ? null : new UserGroupDAO(group);
    }

    @Override
    public void insert(UserGroupDAO groupDAO) {
        if (groupDAO == null)
            throw new IllegalArgumentException("Impossible to insert user group, because it equals NULL!");

        UserGroup group = new UserGroup();
        group.setName(groupDAO.getName());
        try {
            userGroupRepositoryJPA.insert(group);
        } catch (ConstraintViolationException ex) {
            throw new IllegalArgumentException("Impossible to insert user group with name = '"
                    + groupDAO.getName() + "' because user group with the same name already exists.");
        }
    }

    @Override
    public void update(UserGroupDAO groupDAO) {
        if (groupDAO == null)
            throw new IllegalArgumentException("Impossible to update user group, because it equals NULL!");

        UserGroup group = userGroupRepositoryJPA.findById(groupDAO.getId());
        if (group == null)
            throw new IllegalArgumentException("Impossible to update user group, because user group with id = '" +
                    groupDAO.getId() + "' doesn't exist!");

        group.setName(groupDAO.getName());
        try {
            userGroupRepositoryJPA.update(group);
        } catch (ConstraintViolationException ex) {
            throw new IllegalArgumentException("Impossible to change name of user group to '"
                    + groupDAO.getName() + "' because user group with the same name already exists.");
        }
    }

}
