package geekbrains.controllers;

import geekbrains.persist.Category;
import geekbrains.persist.Order;
import geekbrains.persist.OrderItem;
import geekbrains.persist.Product;
import geekbrains.persist.repo.CategoryRepositoryJPA;
import geekbrains.persist.repo.OrderRepositoryJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 24.06.2020
 */
@Named
@SessionScoped
public class OrderController implements Serializable {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);
    private Order order;

    @Inject
    private OrderRepositoryJPA orderRepositoryJPA;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String delete(Order order) {
        orderRepositoryJPA.delete(order.getId());
        return "/orders.xhtml?faces-redirect=true";
    }

    public List<OrderItem> getItems(Order order){
        return order.getItems();
    }

    public String saveOrder() {
        if(order.getId()==null){
            orderRepositoryJPA.insert(order);
        }else{
            orderRepositoryJPA.update(order);
        }
        return "/orders.xhtml?faces-redirect=true";
    }

}
