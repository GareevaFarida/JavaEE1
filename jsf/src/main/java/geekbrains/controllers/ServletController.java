//package geekbrains.controllers;
//
//import geekbrains.Cart;
//import geekbrains.ItemCart;
//import geekbrains.persist.Category;
//import geekbrains.persist.Product;
//import geekbrains.persist.repo.CategoryRepository;
//import geekbrains.persist.repo.ProductRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static geekbrains.listener.AppBootstrapListener.CATEGORY_REPO;
//import static geekbrains.listener.AppBootstrapListener.PRODUCT_REPO;
//
//@WebServlet(name = "ToDoServlet", urlPatterns = {"", "/"})
//public class ServletController extends HttpServlet {
//
//    private static final Logger logger = LoggerFactory.getLogger(ServletController.class);
//
//    private ProductRepository productRepository;
//    private CategoryRepository categoryRepository;
//    private Cart cart;
//
//    @Override
//    public void init() throws ServletException {
//
//        productRepository = (ProductRepository) getServletContext().getAttribute(PRODUCT_REPO);
//        if (productRepository == null) {
//            throw new ServletException("Product repository not initialized");
//        }
//
//        categoryRepository = (CategoryRepository) getServletContext().getAttribute(CATEGORY_REPO);
//        if (categoryRepository == null) {
//            throw new ServletException("Category repository not initialized");
//        }
//
//        cart = new Cart();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        logger.info("Index toDo page");
//
//        if (req.getServletPath().equals("/") ||
//                req.getServletPath().equals("/index") ||
//                req.getServletPath().equals("/products")) {
//            try {
//                req.setAttribute("products", productRepository.findAll());
//                getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
//            } catch (SQLException ex) {
//                throw new IllegalStateException(ex);
//            }
//        } else if (req.getServletPath().equals("/product/new")) {
//            req.setAttribute("prod", new Product());
//            getServletContext().getRequestDispatcher("/WEB-INF/views/product.jsp").forward(req, resp);
//        } else if (req.getServletPath().equals("/product/edit")) {
//            try {
//                Product product = productRepository.findById(getIdFromRequestAndConvertToLong(req, "id"));
//                req.setAttribute("prod", product);
//                getServletContext().getRequestDispatcher("/WEB-INF/views/product.jsp").forward(req, resp);
//            } catch (SQLException ex) {
//                logger.error("Not found product with id = " + req.getAttribute("id"));
//            }
//        } else if (req.getServletPath().equals("/product/delete")) {
//            try {
//                productRepository.delete(getIdFromRequestAndConvertToLong(req, "id"));
//            } catch (SQLException ex) {
//                logger.error("Couldn't delete product with id = " + req.getParameter("id"));
//            }
//            resp.sendRedirect(getServletContext().getContextPath());
//        } else if (req.getServletPath().equals("/categories")) {
//            try {
//                req.setAttribute("categories", categoryRepository.findAll());
//                req.getRequestDispatcher("WEB-INF/views/categories.jsp").forward(req, resp);
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        } else if (req.getServletPath().equals("/category/new")) {
//            req.setAttribute("category", new Category());
//            getServletContext().getRequestDispatcher("/WEB-INF/views/category.jsp").forward(req, resp);
//        } else if (req.getServletPath().equals("/category/edit")) {
//            try {
//                Category category = categoryRepository.findById(getIdFromRequestAndConvertToLong(req, "id"));
//                req.setAttribute("category", category);
//                getServletContext().getRequestDispatcher("/WEB-INF/views/category.jsp").forward(req, resp);
//            } catch (SQLException ex) {
//                logger.error("Not found category with id = " + req.getAttribute("id"));
//            }
//        }else if (req.getServletPath().equals("/category/delete")) {
//            try {
//                categoryRepository.delete(getIdFromRequestAndConvertToLong(req, "id"));
//            } catch (SQLException ex) {
//                logger.error("Couldn't delete category with id = " + req.getParameter("id")+"/n"+ex.getStackTrace());
//            }
//            resp.sendRedirect(getServletContext().getContextPath()+"/categories");
//        } else if (req.getServletPath().equals("/addToCart")) {
//            try {
//                Product product = productRepository.findById(getIdFromRequestAndConvertToLong(req, "id"));
//                cart.addItem(new ItemCart(product, 1, product.getPrice()));
//                List<ItemCart> items =
//                        cart.getItems().stream()
//                                .collect(Collectors.groupingBy(ItemCart::getProduct, Collectors.summingInt(ItemCart::getCount)))
//                                .entrySet()
//                                .stream()
//                                .map(entrySet -> new ItemCart(entrySet.getKey(), entrySet.getValue(), entrySet.getKey().getPrice()))
//                                .collect(Collectors.toList());
//                cart.setItems(items);
//
//                resp.sendRedirect(getServletContext().getContextPath());
//            } catch (SQLException ex) {
//                logger.error("Not found product with id = " + req.getAttribute("id"));
//            }
//        } else if (req.getServletPath().equals("/cart")) {
//            req.setAttribute("items", cart.getItems());
//            req.getRequestDispatcher("WEB-INF/views/cart.jsp").forward(req, resp);
//        } else if (req.getServletPath().equals("/cart/delete")) {
//            Long deleted_id = getIdFromRequestAndConvertToLong(req, "id");
//            List<ItemCart> items = cart.getItems().stream()
//                    .filter(itemCart -> !itemCart.getProduct().getId().equals(deleted_id))
//                    .collect(Collectors.toList());
//            cart.setItems(items);
//            resp.sendRedirect(req.getContextPath() + "/cart");
//        } else if (req.getServletPath().equals("/order")) {
//            req.getRequestDispatcher("/WEB-INF/views/order.jsp").forward(req, resp);
//        } else if (req.getServletPath().equals("/about")) {
//            req.getRequestDispatcher("/WEB-INF/views/about.jsp").forward(req, resp);
//        } else {
//            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
//        }
//    }
//
//    private long getIdFromRequestAndConvertToLong(HttpServletRequest req, String id) {
//        return Long.parseLong(req.getParameter(id));
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        if (req.getServletPath().equals("/product/new")) {
//            try {
//                String id_string = req.getParameter("id");
//                String name = req.getParameter("name");
//                Long price = getIdFromRequestAndConvertToLong(req, "price");
//
//                if (id_string == null || id_string.equals("")) {
//                    logger.info("Price from form: " + req.getParameter("price"));
//                    productRepository.insert(new Product(-1L, name, price, null));
//                } else {
//                    Long id = Long.parseLong(id_string);
//                    Product product = productRepository.findById(id);
//                    product.setName(name);
//                    product.setPrice(price);
//                    productRepository.update(product);
//                }
//                resp.sendRedirect(getServletContext().getContextPath());
//            } catch (SQLException ex) {
//                throw new IllegalStateException(ex);
//            }
//        } else if (req.getServletPath().equals("/category/new")) {
//            try {
//                String id_string = req.getParameter("id");
//                String name = req.getParameter("name");
//
//                if (id_string == null || id_string.equals("")) {
//                    categoryRepository.insert(new Category(-1L, name));
//                } else {
//                    Long id = Long.parseLong(id_string);
//                    Category category = categoryRepository.findById(id);
//                    category.setName(name);
//                    categoryRepository.update(category);
//                }
//                resp.sendRedirect(getServletContext().getContextPath() + "/categories");
//            } catch (SQLException ex) {
//                throw new IllegalStateException(ex);
//            }
//        }
//    }
//}
