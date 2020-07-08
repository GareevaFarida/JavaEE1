package geekbrains.service.dao;

import geekbrains.persist.Product;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ProductDAO {

    private Long id;

    @NotEmpty(message = "Name of product should't be empty!")
    private String name;

    private int price;

   // @NotEmpty(message = "Category id should't be empty!")
    private Long categoryId;

    private String categoryName;

    public ProductDAO() {
    }

    public ProductDAO(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.categoryId = product.getCategory().getId();
        this.categoryName = product.getCategory().getName();
    }

    public ProductDAO(Long id, String name, int price, Long categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ProductDAO product = (ProductDAO) o;
//        return id.equals(product.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
}
