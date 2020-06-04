package ru.geekbrains.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @Author Farida Gareeva
 * Created 02.06.2020
 */
@WebServlet(name = "CartServlet",urlPatterns = {"cart"})
public class CartServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(CartServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("New Get request in "+this.getClass().getSimpleName());
        //заменено на фильтр
        //getServletContext().getRequestDispatcher("/menu").include(req,resp);
        resp.getWriter().println("<h1>Cart Корзина</h1>"+ LocalDateTime.now());
    }

}
