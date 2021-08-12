package vn.nvc.product.model.category;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("category")
public class CategoryRequest {
    private int id;
    private String name;
    private String description;
}
