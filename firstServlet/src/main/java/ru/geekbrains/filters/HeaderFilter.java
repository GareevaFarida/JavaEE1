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
@WebFilter(urlPatterns = "/*")
public class HeaderFilter implements Filter {
    private transient FilterConfig filterConfig;
    private static Logger logger = LoggerFactory.getLogger(HeaderFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("doFilter in class HeaderFilter");
        HttpServletResponse httpResp = (HttpServletResponse)response;
        httpResp.setHeader("Content-Type", "text/html; charset=utf-8");
        request.getRequestDispatcher("/menu").include(request, response);
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
