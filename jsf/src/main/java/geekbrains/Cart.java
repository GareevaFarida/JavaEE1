package geekbrains;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Farida Gareeva
 * Created 13.06.2020
 */
public class Cart {
    private List<ItemCart> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(ItemCart itemCart){
        items.add(itemCart);
    }

    public List<ItemCart> getItems() {
        return items;
    }

    public void setItems(List<ItemCart> items) {
        this.items = items;
    }
}
