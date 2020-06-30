package geekbrains.persist.repo;

import geekbrains.persist.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

//@Named
//@Dependent
@Stateless
public class OrderItemRepositoryJPA implements Serializable {
    public static Logger logger = LoggerFactory.getLogger(OrderItemRepositoryJPA.class);

   // @PersistenceContext(unitName = "ds", type = PersistenceContextType.EXTENDED)
   @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    //@Transactional(value = Transactional.TxType.MANDATORY)
    @TransactionAttribute
    public void insert(OrderItem item) {
        em.persist(item);
    }

    @TransactionAttribute
    public void update(OrderItem item) {
        em.merge(item);
    }

    @TransactionAttribute
    public void delete(long id) {
        OrderItem item = em.find(OrderItem.class, id);
        em.remove(item);
    }

    @TransactionAttribute
    public OrderItem findById(long id) {
        return em.find(OrderItem.class, id);
    }

}
