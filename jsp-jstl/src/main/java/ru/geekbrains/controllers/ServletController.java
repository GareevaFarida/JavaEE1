package ru.geekbrains.controllers;

import ru.geekbrains.Cart;
import ru.geekbrains.ItemCart;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.geekbrains.listener.AppBootstrapListener.PRODUCT_REPO;

@WebServlet(name = "ToDoServlet", urlPatterns = {"", "/"})
public class ServletController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(ServletController.class);

    private ProductRepository repository;
    private Cart cart;

    @Override
    public void init() throws ServletException {
        repository = (ProductRepository) getServletContext().getAttribute(PRODUCT_REPO);
        if (repository == null) {
            throw new ServletException("Product repository not initialized");
        }
        cart = new Cart();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Index toDo page");

        if (req.getServletPath().equals("/") || req.getServletPath().equals("/index")) {
            try {
                req.setAttribute("products", repository.findAll());
                getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
            } catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        } else if (req.getServletPath().equals("/new")) {
            req.setAttribute("prod", new Product());
            getServletContext().getRequestDispatcher("/WEB-INF/views/product.jsp").forward(req, resp);
        } else if (req.getServletPath().equals("/edit")) {
            try {
                Product product = repository.findById(getIdFromRequestAndConvertToLong(req, "id"));
                req.setAttribute("prod", product);
                getServletContext().getRequestDispatcher("/WEB-INF/views/product.jsp").forward(req, resp);
            } catch (SQLException ex) {
                logger.error("Not found product with id = " + req.getAttribute("id"));
            }
        } else if (req.getServletPath().equals("/delete")) {
            try {
                repository.delete(getIdFromRequestAndConvertToLong(req, "id"));
            } catch (SQLException ex) {
                logger.error("Couldn't delete product with id = " + req.getParameter("id"));
            }
            resp.sendRedirect(getServletContext().getContextPath());
        } else if (req.getServletPath().equals("/addToCart")) {
            try {
                Product product = repository.findById(getIdFromRequestAndConvertToLong(req, "id"));
                cart.addItem(new ItemCart(product, 1, product.getPrice()));
                List<ItemCart> items =
                        cart.getItems().stream()
                                .collect(Collectors.groupingBy(ItemCart::getProduct, Collectors.summingInt(ItemCart::getCount)))
                                .entrySet()
                                .stream()
                                .map(entrySet -> new ItemCart(entrySet.getKey(), entrySet.getValue(), entrySet.getKey().getPrice()))
                                .collect(Collectors.toList());
                cart.setItems(items);

                resp.sendRedirect(getServletContext().getContextPath());
            } catch (SQLException ex) {
                logger.error("Not found product with id = " + req.getAttribute("id"));
            }
        } else if (req.getServletPath().equals("/cart")) {
            req.setAttribute("items", cart.getItems());
            req.getRequestDispatcher("WEB-INF/views/cart.jsp").forward(req, resp);
        } else if (req.getServletPath().equals("/cart/delete")) {
            Long deleted_id = getIdFromRequestAndConvertToLong(req, "id");
            List<ItemCart> items = cart.getItems().stream()
                    .filter(itemCart -> !itemCart.getProduct().getId().equals(deleted_id))
                    .collect(Collectors.toList());
            cart.setItems(items);
            resp.sendRedirect(req.getContextPath() + "/cart");
        } else if (req.getServletPath().equals("/order")) {
            req.getRequestDispatcher("/WEB-INF/views/order.jsp").forward(req, resp);
        } else if (req.getServletPath().equals("/about")) {
            req.getRequestDispatcher("/WEB-INF/views/about.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private long getIdFromRequestAndConvertToLong(HttpServletRequest req, String id) {
        return Long.parseLong(req.getParameter(id));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String id_string = req.getParameter("id");
            String name = req.getParameter("name");
            Long price = getIdFromRequestAndConvertToLong(req, "price");

            if (id_string == null || id_string.equals("")) {
                logger.info("Price from form: " + req.getParameter("price"));
                repository.insert(new Product(-1L, name, price));
            } else {
                Long id = Long.parseLong(id_string);
                Product product = repository.findById(id);
                product.setName(name);
                product.setPrice(price);
                repository.update(product);
            }
            resp.sendRedirect(getServletContext().getContextPath());
        } catch (SQLException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
