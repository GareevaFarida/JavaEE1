package geekbrains.persist.repo;

import geekbrains.persist.Order;
import geekbrains.persist.OrderItem;
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
public class OrderItemRepositoryJPA implements Serializable {
    public static Logger logger = LoggerFactory.getLogger(OrderItemRepositoryJPA.class);

    @PersistenceContext(unitName = "ds", type = PersistenceContextType.EXTENDED)
    protected EntityManager em;

    @Inject
    protected UserTransaction utx;

    @Transactional(value = Transactional.TxType.MANDATORY)
    public void insert(OrderItem item) {
        em.persist(item);
    }

    @Transactional
    public void update(OrderItem item) {
        em.merge(item);
    }

    @Transactional
    public void delete(long id) {
        OrderItem item = em.find(OrderItem.class, id);
        em.remove(item);
    }

    @Transactional
    public OrderItem findById(long id) {
        return em.find(OrderItem.class, id);
    }

}
