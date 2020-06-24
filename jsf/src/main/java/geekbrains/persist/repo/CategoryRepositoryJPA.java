package geekbrains.persist.repo;

import geekbrains.persist.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@Dependent
public class CategoryRepositoryJPA implements Serializable {
    public static Logger logger = LoggerFactory.getLogger(CategoryRepositoryJPA.class);

    @PersistenceContext(unitName = "ds", type = PersistenceContextType.EXTENDED)
    protected EntityManager em;

    @Inject
    protected UserTransaction utx;

    @Transactional
    public void insert(Category category) {
        em.persist(category);
    }

    @Transactional
    public void update(Category category) {
        em.merge(category);
    }


    public void delete(long id) {
        try {
            utx.begin();
            Category category = findById(id);
            if (category != null) {
                em.remove(category);
            }
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public Category findById(long id) {
        return em.find(Category.class,id);
    }

    @Transactional
    public List<Category> findAll() {
        Query query = em.createQuery("SELECT c FROM Category c", Category.class);
        List<Category> list = query.getResultList();
        return list;
//        return new ArrayList<Category>();
    }
}
