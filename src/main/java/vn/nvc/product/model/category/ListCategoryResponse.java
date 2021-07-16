package vn.nvc.product.model.category;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;
import vn.nvc.product.base.model.BaseListResponse;
import vn.nvc.product.base.model.Metadata;

import java.util.List;

@Getter
@Setter
@JsonRootName(value = "categories")
public class ListCategoryResponse extends BaseListResponse {
    List<CategoryResponse> categories;

    public ListCategoryResponse() {
    }

    public ListCategoryResponse(List<CategoryResponse> categories, Metadata metadata) {
        this.setCategories(categories);
        this.setMetadata(metadata);
    }
}
