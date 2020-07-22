package geekbrains.service.dao;

import geekbrains.persist.Category;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CategoryDAO implements Serializable {

    private Long id;

    @NotNull(message = "Name of Category shouldn't be empty")
    private String name;

    public CategoryDAO() {
    }

    public CategoryDAO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDAO(Category category){
           this.id = category.getId();
           this.name = category.getName();
    }

}
