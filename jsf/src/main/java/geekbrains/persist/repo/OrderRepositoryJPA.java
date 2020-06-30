package geekbrains.persist.repo;

import geekbrains.persist.ClientOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//@Named
//@Dependent
@Stateless
public class OrderRepositoryJPA implements Serializable {
    public static Logger logger = LoggerFactory.getLogger(OrderRepositoryJPA.class);

   // @PersistenceContext(unitName = "ds", type = PersistenceContextType.EXTENDED)
   @PersistenceContext(unitName = "ds")
    protected EntityManager em;

    @TransactionAttribute
    public void insert(ClientOrder order) {
        logger.info("ORDER ID = "+order.getId()+", DATA CREATED = "+order.getCreated()+", TOTAL SUM = "+order.getTotal());
        order.getItems().forEach(item -> {
            logger.info("ITEM=====PRODUCT = "+item.getProduct()+", COUNT = "+item.getCount()+", PRICE = "+item.getPrice()
                    +", TOTAL SUM = "+item.getTotal()+", ORDER ="+item.getOrder());
        });
        //order.setItems(new ArrayList<>());
        em.persist(order);
    }

    @TransactionAttribute
    public void update(ClientOrder order) {
        em.merge(order);
    }

    @TransactionAttribute
    public void delete(long id) {
        ClientOrder order = em.find(ClientOrder.class, id);
        em.remove(order);
    }

    @TransactionAttribute
    public ClientOrder findById(long id) {
        return em.find(ClientOrder.class, id);
    }

    @TransactionAttribute
    public List<ClientOrder> findAll() {
        Query query = em.createQuery("FROM ClientOrder", ClientOrder.class);
        List<ClientOrder> list = query.getResultList();
        return list;
    }
}
