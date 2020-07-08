package geekbrains.JAX_WS;

import geekbrains.service.dao.ProductDAO;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 06.07.2020
 * v1.0
 */

@WebService
public interface ProductServiceWS {

    @WebMethod
    List<ProductDAO> getProducts();

    @WebMethod
    ProductDAO getProductById(Long id);



}
