package geekbrains.persist.repo;

import geekbrains.Cart;
import geekbrains.persist.Category;
import geekbrains.persist.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ApplicationScoped
public class ProductRepository {

    private Connection conn;
    private static Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    @Inject
    private ServletContext sc;

    @Inject
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        String jdbcConnectionString = sc.getInitParameter("jdbcConnectionString");
        String username = sc.getInitParameter("username");
        String password = sc.getInitParameter("password");
        try {
            conn = DriverManager.getConnection(jdbcConnectionString, username, password);
            createTablesIfNotExists(conn);

            if (this.findAll().isEmpty()) {
                Category category = new Category(1L, "Products");
                categoryRepository.insert(category);
                for (int i = 0; i < 10; i++) {
                    this.insert(new Product(-1L, "Product" + i, 100L, category));
                }

            }
        } catch (SQLException ex) {
            logger.error("", ex);
            throw new RuntimeException(ex);
        }
    }

    public void insert(Product product) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into products(name, price, category_id) values (?, ?, ?);")) {
            stmt.setString(1, product.getName());
            stmt.setLong(2, product.getPrice());
            stmt.setLong(3, product.getCategory().getId());
            stmt.execute();
            logger.info("Inserted product "+product);
        }catch (SQLException ex){
            logger.error("",ex);
            throw new RuntimeException(ex);
        }
    }

    public void update(Product product)  {
        try (PreparedStatement stmt = conn.prepareStatement(
                "update products set name = ?, category_id = ?, price = ? where id = ?;")) {
            stmt.setString(1, product.getName());
            stmt.setLong(2, product.getCategory().getId());
//            stmt.setLong(2, 1L);
            stmt.setLong(3, product.getPrice());
            stmt.setLong(4, product.getId());
            stmt.execute();
            logger.info("Updated product "+product);
        }catch (SQLException ex){
            logger.error("",ex);
            throw new RuntimeException(ex);
        }
    }

    public void delete(long id) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "delete from products where id = ?;")) {
            stmt.setLong(1, id);
            stmt.execute();
        } catch (SQLException ex){
            logger.error("",ex);
            throw new RuntimeException(ex);
        }
    }

    public Product findById(long id) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select products.id, products.name, products.price, products.category_id, categories.name as category_name \n" +
                        "from products as where id = ?\n" +
                        "left join categories\n" +
                        "on products.category_id = categories.id")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(rs.getLong(1), rs.getString(2), rs.getLong(3),
                        new Category(rs.getLong(4), rs.getString(5)));
            }
        }catch (SQLException ex){
            logger.error("",ex);
            throw new RuntimeException(ex);
        }
        return new Product(-1L, "", 0L, null);
    }

    public List<Product> findAll() {
        List<Product> res = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select prod.id, prod.name, prod.price, prod.category_id, cat.name as category_name from products as prod\n" +
                    "left join categories as cat\n" +
                    "on prod.category_id = cat.id");

            Map<Long, Category> categoryMap = new HashMap<>();
            while (rs.next()) {
                Long category_id = rs.getLong(4);
                Category category = categoryMap.get(category_id);
                if (category == null) {
                    category = new Category(category_id, rs.getString(5));
                    categoryMap.put(category_id, category);
                }
                res.add(new Product(rs.getLong(1),
                        rs.getString(2),
                        rs.getLong(3),
                        category));
            }
        } catch (SQLException ex){
            logger.error("",ex);
        }
        return res;
    }

    private void createTablesIfNotExists(Connection conn){
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("create table if not exists categories (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    name varchar(50)\n" +
                    ");");

            stmt.execute("create table if not exists products (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    name varchar(50),\n" +
                    "    price int, \n" +
                    "    category_id int, \n" +
                    "    foreign key (category_id) references categories(id)\n" +
                    ");");
        }catch (SQLException ex){
            logger.error("",ex);
        }
    }
}
