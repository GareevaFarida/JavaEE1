package ru.geekbrains.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Farida Gareeva
 * Created 04.06.2020
 */
@WebServlet(name = "ErrorHandler", urlPatterns = "/error")
public class ErrorHandler extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //этот код не отрабатывает, сразу идет на страницу ошибки errorPage.jsp
//        resp.getWriter().println("<h1>Ошибочка вышла</h1>");
//        Integer statusCode = (Integer) getServletContext().getAttribute("javax.servlet.error.status_code");
//        String servletName = (String) req.getAttribute("javax.servlet.error.servlet_name");
//        logger.info("ErrorHandler "+ servletName+" код ошибки "+statusCode);
//        getServletContext().setAttribute("statusCode",statusCode);
//        getServletContext().setAttribute("servletName",servletName);
//        resp.getWriter().println("<h4>"+servletName+" код ошибки "+statusCode+"</h4>");
    }
}
