package geekbrains.service;

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
public class CategoryServiceImpl implements CategoryService{

    @EJB
    CategoryRepositoryJPA categoryRepositoryJPA;

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
        Category category = new Category();
        category.setId(categoryDAO.getId());
        category.setName(categoryDAO.getName());
        categoryRepositoryJPA.insert(category);
    }

    @Override
    public void update(CategoryDAO categoryDAO) {
        Category category = new Category();
        category.setId(categoryDAO.getId());
        category.setName(categoryDAO.getName());
        categoryRepositoryJPA.update(category);
    }

    @Override
    public List<ProductDAO> getProducts(Long categoryId) {
        Category category = categoryRepositoryJPA.findById(categoryId);
        if(category!=null){
            return category.getProducts().stream()
                    .map(product -> new ProductDAO(product))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
