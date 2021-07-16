package vn.nvc.product.base.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("metadata")
public class Metadata {
    private int total;
    private int page;
    private int limit;

    public Metadata(int total, int page, int limit) {
        this.total = total;
        this.page = page;
        this.limit = limit;
    }

    public Metadata() {
    }
}
