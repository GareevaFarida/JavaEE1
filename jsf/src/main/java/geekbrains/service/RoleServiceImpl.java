package geekbrains.service;

import geekbrains.persist.Role;
import geekbrains.persist.repo.RoleRepositoryJPA;
import geekbrains.persist.repo.UserRepositoryJPA;
import geekbrains.service.dao.RoleDAO;
import org.hibernate.exception.ConstraintViolationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Farida Gareeva
 * Created 14.07.2020
 * v1.0
 */
@Stateless
public class RoleServiceImpl implements RoleService {

    @Inject
    public RoleRepositoryJPA roleRepositoryJPA;

    @Inject
    UserRepositoryJPA userRepositoryJPA;


    @Override
    public void delete(Long id) {
        roleRepositoryJPA.delete(id);
    }

    @Override
    public List<RoleDAO> findAll() {
        return roleRepositoryJPA.findAll()
                .stream()
                .map(RoleDAO::new)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDAO findById(long id) {
        RoleDAO roleDAO = null;
        Role role = roleRepositoryJPA.findById(id);
        if (role != null) {
            roleDAO = new RoleDAO(role);
        }
        return roleDAO;
    }

    @Override
    public void insert(RoleDAO roleDAO) {
        if (roleDAO == null)
            throw new IllegalArgumentException("Impossible to insert role, because it equals NULL!");
        Role role = new Role();
        role.setId(roleDAO.getId());
        role.setName(roleDAO.getName());
        try {
            roleRepositoryJPA.insert(role);
        } catch (ConstraintViolationException ex) {
            throw new IllegalArgumentException("Impossible to insert role with name '"
                    + roleDAO.getName() + "' because role with the same name already exists.");
        }
    }

    @Override
    public void update(RoleDAO roleDAO) {
        if (roleDAO == null)
            throw new IllegalArgumentException("Impossible to update role, because it equals NULL!");
        Role role = roleRepositoryJPA.findById(roleDAO.getId());
        if (role == null) throw new IllegalArgumentException("impossible to update role, because role with id ='"
                + roleDAO.getId() + "', doesn't exist!");
        role.setId(roleDAO.getId());
        role.setName(roleDAO.getName());

        try {
            roleRepositoryJPA.update(role);
        } catch (ConstraintViolationException ex) {
            throw new IllegalArgumentException("Impossible to change name of role to '"
                    + roleDAO.getName() + "' because role with the same name already exists.");
        }
    }

}
