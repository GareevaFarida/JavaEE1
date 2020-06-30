package geekbrains.service;

import geekbrains.service.dao.ProductDAO;
import geekbrains.util.fiilers.ProductFilter;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ProductService {

    void delete(Long id);

    List<ProductDAO> findAll();

    // List<ProductDAO> findAllWithFilter(ProductFilter filter);

    ProductDAO findById(long id);

    void insert(ProductDAO product);

    void update(ProductDAO product);

}
