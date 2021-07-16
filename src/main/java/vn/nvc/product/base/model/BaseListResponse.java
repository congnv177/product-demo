package vn.nvc.product.base.model;

public class BaseListResponse extends BaseIgnoreRootJsonModel {
    private Metadata metadata;

    public BaseListResponse() {
    }

    public Metadata getMetadata() {
        return this.metadata;
    }

    public void setMetadata(final Metadata metadata) {
        this.metadata = metadata;
    }
}
