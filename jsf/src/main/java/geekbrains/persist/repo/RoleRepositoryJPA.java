package geekbrains.persist.repo;

import geekbrains.persist.Role;
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
import java.util.Optional;

@Stateless
public class RoleRepositoryJPA implements Serializable {
    public static Logger logger = LoggerFactory.getLogger(RoleRepositoryJPA.class);

    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @TransactionAttribute
    public void insert(Role role) {
        em.persist(role);
    }

    @TransactionAttribute
    public void update(Role role) {
        em.merge(role);
    }

    @TransactionAttribute
    public void delete(long id) {
        Role role = em.find(Role.class, id);
        em.remove(role);
    }

    @TransactionAttribute
    public Role findById(long id) {
        return em.find(Role.class, id);
    }

    @TransactionAttribute
    public List<Role> findAll() {
        Query query = em.createQuery("SELECT c FROM Role c", Role.class);
        List<Role> list = query.getResultList();
        return list;
    }

}
