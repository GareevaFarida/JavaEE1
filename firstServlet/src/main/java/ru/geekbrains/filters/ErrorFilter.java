package ru.geekbrains.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Farida Gareeva
 * Created 02.06.2020
 */

@WebFilter(urlPatterns = "/*",dispatcherTypes = {DispatcherType.ERROR})
public class ErrorFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        logger.info("Status code "+httpResponse.getStatus());
        request.getServletContext().setAttribute("statusCode",httpResponse.getStatus());
        chain.doFilter(request,response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
