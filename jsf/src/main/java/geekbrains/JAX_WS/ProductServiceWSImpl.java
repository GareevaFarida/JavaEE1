package geekbrains.JAX_WS;

import geekbrains.service.ProductService;
import geekbrains.service.dao.ProductDAO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 06.07.2020
 * v1.0
 */
@Stateless
@WebService(endpointInterface = "geekbrains.JAX_WS.ProductServiceWS", serviceName = "ProductService")
public class ProductServiceWSImpl implements ProductServiceWS{

    @EJB
    public ProductService productService;

    @Override
    public List<ProductDAO> getProducts() {
        return productService.findAll();
    }

    @Override
    public ProductDAO getProductById(Long id) {
        return productService.findById(id);
    }
}
