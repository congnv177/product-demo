package vn.nvc.product.model.category;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("category")
public class CategoryResponse {
    private int id;
    private String name;
    private String description;

    public CategoryResponse() {

    }

    public CategoryResponse(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
