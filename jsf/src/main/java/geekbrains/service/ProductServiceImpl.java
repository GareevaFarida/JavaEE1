package geekbrains.service;

import geekbrains.JAX_RS.ProductServiceRest;
import geekbrains.persist.Category;
import geekbrains.persist.Product;
import geekbrains.persist.repo.CategoryRepositoryJPA;
import geekbrains.persist.repo.ProductRepositoryJPA;
import geekbrains.service.dao.ProductDAO;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ProductServiceImpl implements ProductService, ProductServiceRest {

    @Inject
    private ProductRepositoryJPA productRepositoryJPA;


    @Inject
    private CategoryRepositoryJPA categoryRepositoryJPA;

    @Override
    public void delete(Long id) {
        productRepositoryJPA.delete(id);
    }

    @Override
    public List<ProductDAO> findAll() {
        return productRepositoryJPA.findAll()
                .stream()
                .map(ProductDAO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDAO findById(Long id) {
        Product product = productRepositoryJPA.findById(id);
        if (product != null) {
            return new ProductDAO(productRepositoryJPA.findById(id));
        }

        return null;
    }

    @Override
    public List<ProductDAO> findByName(String name) {
        return productRepositoryJPA.findByName(name)
                .stream()
                .map(ProductDAO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDAO> findByCategoryId(Long id) {
        Category category = categoryRepositoryJPA.findById(id);
        if (category == null)
            return new ArrayList<ProductDAO>();
        return category.getProducts()
                .stream()
                .map(ProductDAO::new)
                .collect(Collectors.toList());
    }
    //    @Override
//    public List<ProductDAO> findAllWithFilter(ProductFilter filter) {
//        return productRepositoryJPA.findAllWithFilter(filter)
//                .stream()
//                .map(ProductDAO::new)
//                .collect(Collectors.toList());
//    }

    @TransactionAttribute
    @Override
    public void insert(ProductDAO productDAO) {
        checkFillingFields(productDAO);

        Product product = new Product();
        product.setName(productDAO.getName());
        product.setPrice(productDAO.getPrice());
        product.setCategory(categoryRepositoryJPA.findById(productDAO.getCategoryId()));
        productRepositoryJPA.insert(product);
    }

    private void checkFillingFields(ProductDAO productDAO) {
        if (productDAO.getName().isEmpty())
            throw new IllegalArgumentException("Название товара не может быть пустым!");
        if (productDAO.getCategoryId().equals("") || productDAO.getCategoryId() == null)
            throw new IllegalArgumentException("Не указан categoryId товара!");
    }

    @Override
    public void update(ProductDAO productDAO) {
        checkFillingFields(productDAO);

        Product product = new Product();
        product.setId(productDAO.getId());
        product.setName(productDAO.getName());
        product.setPrice(productDAO.getPrice());
        product.setCategory(categoryRepositoryJPA.findById(productDAO.getCategoryId()));
        productRepositoryJPA.update(product);
    }
}
