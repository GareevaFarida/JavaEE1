package ru.geekbrains.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Farida Gareeva
 * Created 02.06.2020
 */
@WebServlet(name = "MenuServlet", urlPatterns = "menu")
public class MenuServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(MenuServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Get request in Menu servlet");
        resp.getWriter().println("<ul><li><a href = '"+req.getContextPath()+"/main'>Main page Главная страница</a></li>");
        resp.getWriter().println("<li><a href = '"+req.getContextPath()+"/catalog'>Catalog Каталог товаров</a></li>");
        resp.getWriter().println("<li><a href = '"+req.getContextPath()+"/product'>Product Страница товара</a></li>");
        resp.getWriter().println("<li><a href = '"+req.getContextPath()+"/order'>Order Страница заказа</a></li>");
        resp.getWriter().println("<li><a href = '"+req.getContextPath()+"/cart'>Cart Корзина</a></li></ul>");
    }
}
