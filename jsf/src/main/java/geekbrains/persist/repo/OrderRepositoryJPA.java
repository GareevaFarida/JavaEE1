package geekbrains.persist.repo;

import geekbrains.persist.Order;
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
import java.util.List;

@Named
@Dependent
public class OrderRepositoryJPA implements Serializable {
    public static Logger logger = LoggerFactory.getLogger(OrderRepositoryJPA.class);

    @PersistenceContext(unitName = "ds", type = PersistenceContextType.EXTENDED)
    protected EntityManager em;

    @Inject
    protected UserTransaction utx;

    @Transactional
    public void insert(Order order) {
        em.persist(order);
    }

    @Transactional
    public void update(Order order) {
        em.merge(order);
    }

    @Transactional
    public void delete(long id) {
        Order order = em.find(Order.class, id);
        em.remove(order);
    }

    @Transactional
    public Order findById(long id) {
        return em.find(Order.class, id);
    }

    @Transactional
    public List<Order> findAll() {
        Query query = em.createQuery("FROM Order", Order.class);
        List<Order> list = query.getResultList();
        return list;
    }
}
