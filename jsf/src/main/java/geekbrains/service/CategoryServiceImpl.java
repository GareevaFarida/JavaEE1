package geekbrains.service;

import geekbrains.JAX_RS.CategoryServiceRest;
import geekbrains.persist.Category;
import geekbrains.persist.repo.CategoryRepositoryJPA;
import geekbrains.service.dao.CategoryDAO;
import geekbrains.service.dao.ProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CategoryServiceImpl implements CategoryService, CategoryServiceRest {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @EJB
    private CategoryRepositoryJPA categoryRepositoryJPA;

    @Override
    public void delete(Long id) {
        try {
            categoryRepositoryJPA.delete(id);
        } catch (Exception ex) {
            logger.error("Couldn't delete category id = " + id, ex);
        }
    }

    @Override
    public List<CategoryDAO> findAll() {
        return categoryRepositoryJPA.findAll()
                .stream()
                .map(category -> new CategoryDAO(category))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDAO findById(long id) {
        return new CategoryDAO(categoryRepositoryJPA.findById(id));
    }

    @Override
    public void insert(CategoryDAO categoryDAO) {
        checkFillingFields(categoryDAO);

        Category category = new Category();
        category.setId(categoryDAO.getId());
        category.setName(categoryDAO.getName());
        try {
            categoryRepositoryJPA.insert(category);
        } catch (Exception ex) {
            logger.error("Couldn't insert category name = " + categoryDAO.getName(), ex);
        }
    }

    private void checkFillingFields(CategoryDAO categoryDAO) {
        if (categoryDAO.getName().isEmpty())
            throw new IllegalArgumentException("The name of category couldn't be empty!");
    }

    @Override
    public void update(CategoryDAO categoryDAO) {
        if (categoryDAO == null)
            throw new IllegalArgumentException("Category couldn't be update, because it equals null!");
        checkFillingFields(categoryDAO);

        Category category = new Category();
        category.setId(categoryDAO.getId());
        category.setName(categoryDAO.getName());
        try {
            categoryRepositoryJPA.update(category);
        } catch (Exception ex) {
            logger.error("Couldn't update category id = " + category.getId(), ex);
        }
    }

    @Override
    public List<ProductDAO> getProducts(Long categoryId) {
        if(categoryId==null){
            return new ArrayList<>();
        }
        Category category = categoryRepositoryJPA.findById(categoryId);
        if(category!=null){
            return category.getProducts().stream()
                    .map(product -> new ProductDAO(product))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
