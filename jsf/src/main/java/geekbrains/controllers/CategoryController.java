package geekbrains.controllers;

import geekbrains.persist.Category;
import geekbrains.persist.Product;
import geekbrains.persist.repo.CategoryRepositoryJPA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 17.06.2020
 */
@Named
@SessionScoped
public class CategoryController implements Serializable {

    private Logger logger = LoggerFactory.getLogger(CategoryController.class);
    private Category category;

    @Inject
    private CategoryRepositoryJPA categoryRepository;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String createCategory(){
        category = new Category();
        return "/category.xhtml?faces-redirect=true";
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public String editCategory(Category category) {
        this.category = category;
        return "/category.xhtml?faces-redirect=true";
    }

    public String deleteCategory(Category category) {
        this.category = category;
        categoryRepository.delete(category.getId());
        return "/categories.xhtml?faces-redirect=true";
    }

    public List<Product> getProducts(Category category){
        return category.getProducts();
    }

    public String saveCategory() {
        if(category.getId()==null){
            categoryRepository.insert(category);
        }else{
            categoryRepository.update(category);
        }
        return "/categories.xhtml?faces-redirect=true";
    }

}
