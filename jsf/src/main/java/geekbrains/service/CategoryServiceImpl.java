package geekbrains.service;

import geekbrains.JAX_RS.CategoryServiceRest;
import geekbrains.persist.Category;
import geekbrains.persist.repo.CategoryRepositoryJPA;
import geekbrains.service.dao.CategoryDAO;
import geekbrains.service.dao.ProductDAO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CategoryServiceImpl implements CategoryService, CategoryServiceRest {

    @EJB
    private CategoryRepositoryJPA categoryRepositoryJPA;

    @Override
    public void delete(Long id) {
        categoryRepositoryJPA.delete(id);
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
        categoryRepositoryJPA.insert(category);
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
        categoryRepositoryJPA.update(category);
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
