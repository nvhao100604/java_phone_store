package app.DTO;

public class Brand {

    private int id;
    private String brandName;
    private boolean status;

    public Brand(int id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }

    public Brand(int id) {
        this.id = id;
    }

    public Brand(String brandName) {
        this.brandName = brandName;
    }

    public int getId() {
        return id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getBrandNameById(int id) {
        if (this.id == id) {
            return this.brandName;
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Brand brand = (Brand) o;
        return id == brand.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return brandName;
    }
}
