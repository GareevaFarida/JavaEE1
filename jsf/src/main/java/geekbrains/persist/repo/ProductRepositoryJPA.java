package geekbrains.persist.repo;

import geekbrains.persist.Product;
import geekbrains.persist.Product_;
import geekbrains.util.fiilers.ProductFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.List;

//@Named
//@ApplicationScoped
@Stateless
public class ProductRepositoryJPA implements Serializable {
    public static Logger logger = LoggerFactory.getLogger(ProductRepositoryJPA.class);

   // @PersistenceContext(unitName = "ds", type = PersistenceContextType.EXTENDED)
   @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @TransactionAttribute
    public void insert(Product product) {
        em.persist(product);
    }

    @TransactionAttribute
    public void update(Product product) {
        em.merge(product);
    }

    @TransactionAttribute
    public void delete(long id) {
        Product product = em.find(Product.class, id);
        if (product != null)
            em.remove(product);
        else throw new IllegalArgumentException("Product with id = " + id + " not found.");
    }

    @TransactionAttribute
    public Product findById(long id) {
        return em.find(Product.class, id);
    }

    @TransactionAttribute
    public List<Product> findByName(String name) {
        Query query = em.createQuery("SELECT c from Product c where name like '%" + name+"%'");
        return query.getResultList();
    }

    @TransactionAttribute
    public List<Product> findAll() {
        Query query = em.createQuery("SELECT c FROM Product c", Product.class);
        List<Product> list = query.getResultList();
        return list;
    }

//    @TransactionAttribute
//    public List<Product> findAllWithFilter(ProductFilter filter) {
//
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Product> query = cb.createQuery(Product.class);
//        Root<Product> c = query.from(Product.class);
//
//        ParameterExpression<Integer> priceMin = cb.parameter(Integer.class);
//        ParameterExpression<Integer> priceMax = cb.parameter(Integer.class);
//        ParameterExpression<Long> categoryId = cb.parameter(Long.class);
//
//        Predicate condition_min = cb.greaterThanOrEqualTo(c.get(String.valueOf(Product_.price)), priceMin);
//        Predicate condition_max = cb.lessThanOrEqualTo(c.get(String.valueOf(Product_.price)), priceMax);
//        Predicate conditionCategoryId = cb.equal(c.get(String.valueOf(Product_.category)).get("id"), categoryId);
//
//        Predicate condition;
//        if (filter.getCategoryId() == null || filter.getCategoryId() == -1L) {
//            if (filter.getMaxPrice() == 0) {
//                condition = cb.and(condition_min);
//            } else {
//                condition = cb.and(condition_min, condition_max);
//            }
//        } else {
//            if (filter.getMaxPrice() == 0) {
//                condition = cb.and(condition_min, conditionCategoryId);
//            } else {
//                condition = cb.and(condition_min, condition_max, conditionCategoryId);
//            }
//        }
//
//        query.select(c).where(condition);
//        TypedQuery<Product> q = em.createQuery(query);
//
//        q.setParameter(priceMin, filter.getMinPrice());
//        if (filter.getMaxPrice() != 0) {
//            q.setParameter(priceMax, filter.getMaxPrice());
//        }
//        if (filter.getCategoryId() != null && filter.getCategoryId() != -1L) {
//            q.setParameter(categoryId, filter.getCategoryId());
//        }
//
//        List<Product> products = q.getResultList();
//
//        return products;
//
//    }
}
