package geekbrains.controllers;

import geekbrains.persist.Category;
import geekbrains.persist.Product;
import geekbrains.persist.repo.CategoryRepositoryJPA;
import geekbrains.service.CategoryService;
import geekbrains.service.dao.CategoryDAO;
import geekbrains.service.dao.ProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
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
    private CategoryDAO category;

    @EJB
    private CategoryService categoryService;

    public CategoryDAO getCategory() {
        return category;
    }

    public void setCategory(CategoryDAO category) {
        this.category = category;
    }

    public String createCategory(){
        this.category = new CategoryDAO();
        return "/category.xhtml?faces-redirect=true";
    }

    public List<CategoryDAO> getAllCategories(){
        return categoryService.findAll();
    }

    public String editCategory(CategoryDAO category) {
        this.category = category;
        return "/category.xhtml?faces-redirect=true";
    }

    public void deleteCategory(CategoryDAO category) {
        this.category = category;
        categoryService.delete(category.getId());
        //return "/categories.xhtml?faces-redirect=true";
    }

    public List<ProductDAO> getProducts(CategoryDAO category){
        return categoryService.getProducts(category.getId());
    }

    public String saveCategory() {
        if(category.getId()==null){
            categoryService.insert(category);
        }else{
            categoryService.update(category);
        }
        return "/categories.xhtml?faces-redirect=true";
    }

}
