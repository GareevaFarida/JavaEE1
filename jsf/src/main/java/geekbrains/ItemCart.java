package geekbrains;


import geekbrains.persist.Product;
import geekbrains.service.dao.ProductDAO;

/**
 * @Author Farida Gareeva
 * Created 13.06.2020
 */
public class ItemCart {
    private ProductDAO product;
    private int price;
    private int count;

    public ItemCart(ProductDAO product, int count, int price) {
        this.product = product;
        this.count = count;
        this.price = price;
    }

    public ProductDAO getProduct() {
        return product;
    }

    public void setProduct(ProductDAO product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
