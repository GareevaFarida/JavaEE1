package geekbrains.controllers;

import geekbrains.Cart;
import geekbrains.ItemCart;
import geekbrains.persist.repo.ProductRepositoryJPA;
import geekbrains.service.ProductService;
import geekbrains.service.dao.ProductDAO;
import geekbrains.util.fiilers.ProductFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Farida Gareeva
 * Created 16.06.2020
 */
@Named
@SessionScoped
public class ProductController implements Serializable {

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @EJB
    private ProductService productService;

    private ProductDAO product;

    @EJB
    private Cart cart;

    private ProductFilter filter;

    @PostConstruct
    public void init() {
        this.filter = new ProductFilter();
    }

    public ProductController() {
        // this.cart = new Cart();
    }

    public Cart getCart() {
        return cart;
    }

    public ProductFilter getFilter() {
        return filter;
    }

    public void setFilter(ProductFilter filter) {
        this.filter = filter;
    }

    public ProductDAO getProduct() {
        return product;
    }

    public void setProduct(ProductDAO product) {
        this.product = product;
    }

    public String createProduct() {
        this.product = new ProductDAO();
        return "/product.xhtml?faces-redirect=true";
    }

    public List<ProductDAO> getAllProducts() {
        return productService.findAll();
        //  return productService.findAllWithFilter(filter);
    }

    public String editProduct(ProductDAO product) {
        this.product = product;
        return "/product.xhtml?faces-redirect=true";
    }

    public void deleteProduct(ProductDAO product) {
        this.product = product;
        if (product != null) {
            productService.delete(product.getId());
        }
        //return "/index.xhtml?faces-redirect=true";
    }

    public String useFilter() {
        logger.info("Filter settings: category =" + filter.getCategoryId()
                + ", price min = "
                + filter.getMinPrice()
                + ", price max = " + filter.getMaxPrice());

        return "/index.xhtml?faces-redirect=true";
    }

    public void addToCart(ProductDAO product) {
        cart.addItem(new ItemCart(product, 1, product.getPrice()));
        List<ItemCart> items =
                cart.getItems().stream()
                        .collect(Collectors.groupingBy(ItemCart::getProduct, Collectors.summingInt(ItemCart::getCount)))
                        .entrySet()
                        .stream()
                        .map(entrySet -> new ItemCart(entrySet.getKey(), entrySet.getValue(), entrySet.getKey().getPrice()))
                        .collect(Collectors.toList());
        cart.setItems(items);
    }

    public List<ItemCart> getItemsFromCart() {
        return cart.getItems();
    }

    public String saveProduct() {
        logger.info("Try to save product " + product);
        if (product.getId() == null) {
            productService.insert(product);
        } else {
            productService.update(product);
        }
        return "/index.xhtml?faces-redirect=true";
    }

}
