package geekbrains.controllers;

import geekbrains.Cart;
import geekbrains.persist.ClientOrder;
import geekbrains.service.OrderService;
import geekbrains.service.dao.OrderDAO;
import geekbrains.service.dao.OrderItemDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
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
    private OrderDAO order;

    @EJB
    private Cart cart;

    @EJB
    private OrderService orderService;

    public OrderDAO getOrder() {
        return order;
    }

    public void setOrder(OrderDAO order) {
        this.order = order;
    }

    public OrderController() {
        this.order = new OrderDAO();
    }

    //    public Cart getCart() {
//        return this.cart;
//    }
//
//    public void setCart(Cart cart) {
//        this.cart = cart;
//    }

    //  public String createOrder(Cart cart){
    public String createOrder() {
        //       this.cart = cart;

        //если корзина является бином, то в данной точке она оказывается без элементов.
        // Этот метод вызывается из фронта cart.xhtml
        logger.info("CART CONTAINS " + cart.getItems().size() + " ELEMENTS");
        this.order = new OrderDAO();
        List<OrderItemDAO> orderItems = cart.getItems().stream()
                .map(itemCart -> new OrderItemDAO(itemCart, null))
                .collect(Collectors.toList());
        this.order.setItems(orderItems);
        int total = orderItems.stream().mapToInt(OrderItemDAO::getTotal).sum();
        this.order.setTotal(total);
        return "/order.xhtml?faces-redirect=true";
    }

    public String delete(ClientOrder order) {
        orderService.delete(order.getId());
        return "/orders.xhtml?faces-redirect=true";
    }

    public String edit(OrderDAO order) {
        this.order = order;
        return "/order.xhtml?faces-redirect=true";
    }

    public List<OrderItemDAO> getItems(OrderDAO order) {
        return order.getItems();
    }

    @Transactional
    public String saveOrder(OrderDAO orderDAO) {
        this.order = orderDAO;
        if (order.getId() == null) {
            orderService.insert(order);
        } else {
            orderService.update(order);
        }
        clearCart();
        return "/orders.xhtml?faces-redirect=true";
    }

    private void clearCart() {
        //      this.cart = new Cart();
        if (cart != null)
            this.cart.setItems(new ArrayList<>());
    }

    public List<OrderDAO> getAllOrders() {
        return orderService.findAll();
    }

}
