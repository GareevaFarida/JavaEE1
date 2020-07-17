package geekbrains.service;

import geekbrains.service.dao.RoleDAO;
import geekbrains.service.dao.UserDAO;
import geekbrains.service.dao.UserGroupDAO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface RoleService {

    void delete(Long id);

    List<RoleDAO> findAll();

    RoleDAO findById(long id);

    void insert(RoleDAO group);

    void update(RoleDAO group);

}
