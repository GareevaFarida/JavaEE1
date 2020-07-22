package geekbrains.persist.repo;

import geekbrains.persist.UserGroup;
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
public class UserGroupRepositoryJPA implements Serializable {
    public static Logger logger = LoggerFactory.getLogger(UserGroupRepositoryJPA.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @TransactionAttribute
    public void insert(UserGroup group) {
        em.persist(group);
    }

    @TransactionAttribute
    public void update(UserGroup group) {
        em.merge(group);
    }

    @TransactionAttribute
    public void delete(long id) {
        UserGroup group = em.find(UserGroup.class, id);
        em.remove(group);
    }

    @TransactionAttribute
    public UserGroup findById(long id) {
        return em.find(UserGroup.class, id);
    }

    @TransactionAttribute
    public List<UserGroup> findAll() {
        Query query = em.createQuery("SELECT c FROM UserGroup c", UserGroup.class);
        List<UserGroup> list = query.getResultList();
        return list;
    }
}
