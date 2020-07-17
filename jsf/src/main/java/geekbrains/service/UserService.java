package geekbrains.service;

import geekbrains.service.dao.UserDAO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserService {

    void delete(Long id);

    List<UserDAO> findAll();

    UserDAO findById(long id);

    void insert(UserDAO user);

    void update(UserDAO user);

}
