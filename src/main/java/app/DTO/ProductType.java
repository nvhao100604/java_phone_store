package app.DTO;

public class ProductType {
    private int productTypeId;
    private String productTypeName;
    private String productTypeStatus;

    public ProductType() {}
 
    public ProductType(int productTypeId, String productTypeName, String productTypeStatus) {
        this.productTypeId = productTypeId;
        this.productTypeName = productTypeName;
        this.productTypeStatus = productTypeStatus;
    }

    public ProductType(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getProductTypeStatus() {
        return productTypeStatus;
    }

    public void setProductTypeStatus(String productTypeStatus) {
        this.productTypeStatus = productTypeStatus;
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "productTypeId=" + productTypeId +
                ", productTypeName=" + productTypeName +
                ", productTypeStatus=" + productTypeStatus +
                '}';
    }
}
