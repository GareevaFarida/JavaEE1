package geekbrains.service;

import geekbrains.persist.Product;
import geekbrains.persist.repo.CategoryRepositoryJPA;
import geekbrains.persist.repo.ProductRepositoryJPA;
import geekbrains.service.dao.ProductDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ProductServiceImpl implements ProductService {

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

//    @Override
//    public List<ProductDAO> findAllWithFilter(ProductFilter filter) {
//        return productRepositoryJPA.findAllWithFilter(filter)
//                .stream()
//                .map(ProductDAO::new)
//                .collect(Collectors.toList());
//    }

    @Override
    public ProductDAO findById(long id) {
        return new ProductDAO(productRepositoryJPA.findById(id));
    }

    @Override
    public void insert(ProductDAO productDAO) {
        Product product = new Product();
        product.setName(productDAO.getName());
        product.setPrice(productDAO.getPrice());
        product.setCategory(categoryRepositoryJPA.findById(productDAO.getCategoryId()));
        productRepositoryJPA.insert(product);
    }

    @Override
    public void update(ProductDAO productDAO) {
        Product product = new Product();
        product.setId(productDAO.getId());
        product.setName(productDAO.getName());
        product.setPrice(productDAO.getPrice());
        product.setCategory(categoryRepositoryJPA.findById(productDAO.getCategoryId()));
        productRepositoryJPA.update(product);
    }
}
