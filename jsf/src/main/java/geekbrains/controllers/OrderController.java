package geekbrains.controllers;

import geekbrains.Cart;
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
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Farida Gareeva
 * Created 24.06.2020
 */
@Named
@SessionScoped
public class OrderController implements Serializable {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);
    private Order order;
    private Cart cart;

    @Inject
    private OrderRepositoryJPA orderRepositoryJPA;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String createOrder(Cart cart){
        this.cart = cart;
        this.order = new Order();
        List<OrderItem> orderItems = cart.getItems().stream()
                .map(itemCart -> new OrderItem(itemCart.getProduct(),itemCart.getCount(),itemCart.getPrice(),this.order))
                .collect(Collectors.toList());
        int total = orderItems.stream().mapToInt(OrderItem::getTotal).sum();
        this.order.setTotal(total);
        this.order.setItems(orderItems);
        return "/order.xhtml?faces-redirect=true";
    }

    public String delete(Order order) {
        orderRepositoryJPA.delete(order.getId());
        return "/orders.xhtml?faces-redirect=true";
    }

    public String edit(Order order) {
        this.order = order;
        return "/order.xhtml?faces-redirect=true";
    }

    public List<OrderItem> getItems(Order order){
        return order.getItems();
    }

    @Transactional
    public String saveOrder(Order order) {
        this.order = order;
        if(order.getId()==null){
            orderRepositoryJPA.insert(order);
        }else{
            orderRepositoryJPA.update(order);
        }
        clearCart();
        return "/orders.xhtml?faces-redirect=true";
    }

    private void clearCart() {
        this.cart.setItems(new ArrayList<>());
    }

    public List<Order> getAllOrders(){
        return orderRepositoryJPA.findAll();
    }

}
