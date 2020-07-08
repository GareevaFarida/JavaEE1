import geekbrains.jax_ws.ProductService;
import geekbrains.jax_ws.ProductServiceWS;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author Farida Gareeva
 * Created 06.07.2020
 * v1.0
 */

public class Client {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8080/jsf/ProductService/ProductServiceWSImpl?wsdl");
        ProductService productService = new ProductService(url);

        ProductServiceWS productServiceWS = productService.getProductServiceWSImplPort();
        productServiceWS.getProducts()
        .forEach(prod -> System.out.println(prod.getId()+" "+prod.getName()))
        ;

        System.out.println(productServiceWS.getProductById(9l));

    }
}
