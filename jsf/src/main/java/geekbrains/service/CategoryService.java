package geekbrains.service;

import geekbrains.controllers.ProductController;
import geekbrains.service.dao.CategoryDAO;
import geekbrains.service.dao.ProductDAO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CategoryService {

    void delete(Long id);

    List<CategoryDAO> findAll();

    CategoryDAO findById(long id);

    void insert(CategoryDAO category);

    void update(CategoryDAO category);

    List<ProductDAO> getProducts(Long id);

}
