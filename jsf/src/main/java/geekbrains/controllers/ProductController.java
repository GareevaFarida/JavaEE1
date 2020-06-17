package geekbrains.controllers;

import geekbrains.Cart;
import geekbrains.ItemCart;
import geekbrains.persist.Category;
import geekbrains.persist.Product;
import geekbrains.persist.repo.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
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

    @Inject
    private ProductRepository productRepository;

    private Product product;
    private ProductRepr productRepr;
    private Cart cart;

    public class ProductRepr{
        private Long id;
        private String name;
        private Long price;
        private SelectItem selectedCategory;

        public ProductRepr() {
        }

        public ProductRepr(Long id, String name, Long price, SelectItem selectedCategory) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.selectedCategory = selectedCategory;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Long getPrice() {
            return price;
        }

        public SelectItem getSelectedCategory() {
            return selectedCategory;
        }
    }
    public ProductController() {
        this.cart = new Cart();
        this.productRepr = new ProductRepr();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductRepr getProductRepr() {
        return productRepr;
    }

    public void setProductRepr(ProductRepr productRepr) {
        this.productRepr = productRepr;
    }

    public String createProduct(){
        product = new Product();
        return "/product.xhtml?faces-redirect=true";
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public String editProduct(Product product) {
        this.product = product;
        this.productRepr = new ProductRepr(product.getId(),
                product.getName(),
                product.getPrice(),
                new SelectItem(product.getCategory(),product.getCategory().getName()));
//        productRepository.update(product);
        return "/product.xhtml?faces-redirect=true";
    }

    public String deleteProduct(Product product) {
        this.product = product;
        productRepository.delete(product.getId());
        return "/index.xhtml?faces-redirect=true";
    }

    public void addToCart(Product product){
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

    public String saveProduct(ProductRepr productRepr) {
        product = new Product(productRepr.getId(),
                productRepr.getName(),
                productRepr.getPrice(),
                (Category) productRepr.getSelectedCategory().getValue());
        logger.info("Try to save product "+product);
        if(productRepr.getId()==null){
            productRepository.insert(product);
        }else{
            productRepository.update(product);
        }
        return "/index.xhtml?faces-redirect=true";
    }

}
