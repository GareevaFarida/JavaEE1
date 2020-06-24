package geekbrains.persist.repo;

import geekbrains.persist.Category;
import geekbrains.persist.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@Dependent
public class ProductRepositoryJPA implements Serializable {
    public static Logger logger = LoggerFactory.getLogger(ProductRepositoryJPA.class);

    @PersistenceContext(unitName = "ds", type = PersistenceContextType.EXTENDED)
    protected EntityManager em;

    @Inject
    protected UserTransaction utx;

    @Transactional
    public void insert(Product product) {
        em.persist(product);
    }

    @Transactional
    public void update(Product product) {
        em.merge(product);
    }


    public void delete(long id) {
        try {
            utx.begin();
            Product product = findById(id);
            if (product != null) {
                em.remove(product);
            }
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public Product findById(long id) {
        return em.find(Product.class,id);
    }

    @Transactional
    public List<Product> findAll() {
        Query query = em.createQuery("SELECT c FROM Product c", Product.class);
        List<Product> list = query.getResultList();
        return list;
//        return new ArrayList<Product>();
    }
}
