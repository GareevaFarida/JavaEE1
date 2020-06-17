package geekbrains;

import geekbrains.persist.Product;

/**
 * @Author Farida Gareeva
 * Created 13.06.2020
 */
public class ItemCart {
    private Product product;
    private Long price;
    private int count;

    public ItemCart(Product product, int count, Long price) {
        this.product = product;
        this.count = count;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
