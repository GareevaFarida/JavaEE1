package geekbrains.service;

import geekbrains.persist.ClientOrder;
import geekbrains.persist.OrderItem;
import geekbrains.persist.repo.OrderRepositoryJPA;
import geekbrains.persist.repo.ProductRepositoryJPA;
import geekbrains.service.dao.OrderDAO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class OrderServiceImpl implements OrderService {

    @EJB
    private OrderRepositoryJPA orderRepositoryJPA;

    @EJB
    private ProductRepositoryJPA productRepositoryJPA;

    @Override
    public void delete(Long id) {
        orderRepositoryJPA.delete(id);
    }

    @Override
    public List<OrderDAO> findAll() {
        return orderRepositoryJPA.findAll()
                .stream()
                .map(OrderDAO::new)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDAO findById(long id) {
        ClientOrder order = orderRepositoryJPA.findById(id);
        if (order != null) {
            return new OrderDAO(order);
        }
        return new OrderDAO();
    }

    @Override
    public void insert(OrderDAO orderDAO) {
        ClientOrder order = new ClientOrder();
        fillOrderByOrderDAO(orderDAO, order);
        orderRepositoryJPA.insert(order);
    }

    private void fillOrderByOrderDAO(OrderDAO orderDAO, ClientOrder order) {
        order.setCreated(orderDAO.getCreated());
        order.setTotal(orderDAO.getTotal());
        order.setPhoneNumber(orderDAO.getPhoneNumber());
        order.setItems(orderDAO.getItems()
                .stream()
                .map(item -> new OrderItem(productRepositoryJPA.findById(item.getProductId()),
                        item.getCount(),
                        item.getPrice(),
                        order))
                .collect(Collectors.toList()));
    }

    @Override
    public void update(OrderDAO orderDAO) {
        ClientOrder order = orderRepositoryJPA.findById(orderDAO.getId());
        if (order == null) throw new IllegalArgumentException("Order with id = " + orderDAO.getId() + " not found");

       // fillOrderByOrderDAO(orderDAO, order);
        order.setPhoneNumber(orderDAO.getPhoneNumber());
        orderRepositoryJPA.update(order);
    }
}
