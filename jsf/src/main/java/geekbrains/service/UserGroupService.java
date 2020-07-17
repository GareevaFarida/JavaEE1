package geekbrains.service;

import geekbrains.service.dao.UserGroupDAO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserGroupService {

    void delete(Long id);

    List<UserGroupDAO> findAll();

    UserGroupDAO findById(long id);

    void insert(UserGroupDAO group);

    void update(UserGroupDAO group);

}
