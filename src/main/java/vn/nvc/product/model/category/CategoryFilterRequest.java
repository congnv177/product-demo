package vn.nvc.product.model.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryFilterRequest {
    private int page = 1;
    private int limit = 5;

    public int getLimit() {
        if (this.limit <= 0) {
            return 5;
        }
        return limit;
    }

    public int getPage() {
        return this.page <= 0 ? 1 : this.page;
    }
}
