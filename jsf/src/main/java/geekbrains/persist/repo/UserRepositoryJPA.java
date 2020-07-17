package geekbrains.persist.repo;

import geekbrains.persist.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

@Stateless
public class UserRepositoryJPA implements Serializable {
    public static Logger logger = LoggerFactory.getLogger(UserRepositoryJPA.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @TransactionAttribute
    public void insert(User user) {
        em.persist(user);
    }

    @TransactionAttribute
    public User update(User user) {
        return em.merge(user);
    }

    @TransactionAttribute
    public void delete(long id) {
        User user = em.find(User.class, id);
        em.remove(user);
    }

    @TransactionAttribute
    public User findById(long id) {
        return em.find(User.class, id);
    }

    @TransactionAttribute
    public List<User> findAll() {
        Query query = em.createQuery("SELECT c FROM User c", User.class);
        List<User> list = query.getResultList();
        return list;
    }

}
