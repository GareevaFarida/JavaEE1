package geekbrains.persist.repo;

import geekbrains.persist.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;


//@ApplicationScoped
//@Named
@Stateless
public class CategoryRepositoryJPA implements Serializable {
    public static Logger logger = LoggerFactory.getLogger(CategoryRepositoryJPA.class);

    //@PersistenceContext(unitName = "ds", type = PersistenceContextType.EXTENDED)
    @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @RolesAllowed("manager")
    @TransactionAttribute
    public void insert(Category category) {
        em.persist(category);
    }

    @RolesAllowed("manager")
    @TransactionAttribute
    public void update(Category category) {
        em.merge(category);
    }

    @RolesAllowed("manager")
    @TransactionAttribute
    public void delete(long id) {
        Category category = em.find(Category.class, id);
        em.remove(category);

    }

    @PermitAll
    @TransactionAttribute
    public Category findById(long id) {
        return em.find(Category.class, id);
    }

    @PermitAll
    @TransactionAttribute
    public List<Category> findAll() {
        Query query = em.createQuery("SELECT c FROM Category c", Category.class);
        List<Category> list = query.getResultList();
        return list;
    }
}
