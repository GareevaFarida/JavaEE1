package geekbrains.service;

import geekbrains.service.dao.OrderDAO;
import geekbrains.service.dao.ProductDAO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface OrderService {

    void delete(Long id);

    List<OrderDAO> findAll();

    OrderDAO findById(long id);

    void insert(OrderDAO order);

    void update(OrderDAO order);

}
