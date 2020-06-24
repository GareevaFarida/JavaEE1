//package geekbrains.listener;
//
//import geekbrains.persist.repo.CategoryRepository;
//import geekbrains.persist.repo.ProductRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//@WebListener
//public class AppBootstrapListener implements ServletContextListener {
//
//    public static final String DB_CONNECTION = "dbConnection";
//    public static final String PRODUCT_REPO = "productRepo";
//    public static final String CATEGORY_REPO = "categoryRepo";
//
//    private static final Logger logger = LoggerFactory.getLogger(AppBootstrapListener.class);
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        logger.info("Initializing application");
//
//        ServletContext sc = sce.getServletContext();
//        String jdbcConnectionString = sc.getInitParameter("jdbcConnectionString");
//        String username = sc.getInitParameter("username");
//        String password = sc.getInitParameter("password");
//
//        try {
//            Connection conn = DriverManager.getConnection(jdbcConnectionString, username, password);
//            sc.setAttribute(DB_CONNECTION, conn);
//
//            ProductRepository productRepository = new ProductRepository(conn);
//            sc.setAttribute(PRODUCT_REPO, productRepository);
//
//            CategoryRepository categoryRepository = new CategoryRepository(conn);
//            sc.setAttribute(CATEGORY_REPO, categoryRepository);
//
////            if (productRepository.findAll().isEmpty()) {
////                for (int i = 0; i < 10; i++) {
////                    productRepository.insert(new Product(-1L,"Product"+i,100L,null));
////                }
////
////            }
//        } catch (SQLException ex) {
//            logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            logger.error("Connection is failed", ex);
//            logger.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        }
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//        ServletContext sc = sce.getServletContext();
//        Connection conn = (Connection) sc.getAttribute(DB_CONNECTION);
//        try {
//            if (conn != null && conn.isClosed()) {
//                conn.close();
//            }
//        } catch (SQLException ex) {
//            logger.error("", ex);
//        }
//    }
//}
