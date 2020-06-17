package geekbrains.persist.repo;

import geekbrains.persist.Category;
import geekbrains.persist.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class CategoryRepository {

    private Connection conn;
    private static Logger logger = LoggerFactory.getLogger(CategoryRepository.class);
    private List<SelectItem> allCategories;

    @Inject
    private ServletContext sc;

    @PostConstruct
    public void init() {
        String jdbcConnectionString = sc.getInitParameter("jdbcConnectionString");
        String username = sc.getInitParameter("username");
        String password = sc.getInitParameter("password");
        try {
            conn = DriverManager.getConnection(jdbcConnectionString, username, password);
        } catch (SQLException ex) {
            logger.error("", ex);
            throw new RuntimeException(ex);
        }
    }

    public List<SelectItem> getAllCategories() {
        this.allCategories = findAllCategories();
        return allCategories;
    }

    public void setAllCategories(List<SelectItem> allCategories) {
        this.allCategories = allCategories;
    }

    public void insert(Category category) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into categories(name) values (?);")) {
            stmt.setString(1, category.getName());
            stmt.execute();
        }catch (SQLException ex){
            logger.error("",ex);
            throw new RuntimeException(ex);
        }
    }

    public void update(Category category){
        try (PreparedStatement stmt = conn.prepareStatement(
                "update categories set name = ? where id = ?;")) {
            stmt.setString(1, category.getName());
            stmt.setLong(2, category.getId());
            stmt.execute();
        }catch (SQLException ex){
            logger.error("",ex);
            throw new RuntimeException(ex);
        }
    }

    public void delete(long id) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "delete from categories where id = ?;")) {
            stmt.setLong(1, id);
            stmt.execute();
        }catch (SQLException ex){
            logger.error("",ex);
            throw new RuntimeException(ex);
        }
    }

    public Category findById(long id) {
        try (PreparedStatement stmt = conn.prepareStatement(
                "select id, name from categories where id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Category(rs.getLong(1), rs.getString(2));
            }
        }catch (SQLException ex){
        logger.error("",ex);
        throw new RuntimeException(ex);
    }
        return new Category(-1L, "");
    }

    public List<Category> findAll() {
        List<Category> res = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, name from categories");

            while (rs.next()) {
                res.add(new Category(rs.getLong(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            logger.error("", ex);
            throw new RuntimeException(ex);
        }
        return res;
    }


    public List<SelectItem> findAllCategories() {
        List<Category> categories = this.findAll();
        return categories.stream()
                .map(category -> new SelectItem(category, category.getName(), "" + category.getId()))
                .collect(Collectors.toList());
    }
}
