package app.BUS;

import java.math.BigDecimal;
import java.util.List;

import app.DAO.ProductDAO;
import app.DTO.Product;

public class ProductBUS {
    private ProductDAO dao;

    public ProductBUS() {
        dao = new ProductDAO();
    }

    public List<Product> getAll() {
        return dao.getAll();
    }

    public int AddProduct(Product product) {
        return dao.addProduct(product);
    }

    public static void main(String[] args) {
        ProductBUS bus = new ProductBUS();
        // List<Product> list = bus.getAll();
        // for (Product product : list) {
        // System.out.println("Product check: " + product.getProductName());
        // }

        Product myProduct = new Product(
                "Smartphone XYZ",
                4,
                new BigDecimal("500.00"),
                4,
                "https://example.com/images/xyz.jpg",
                "A high-end smartphone with a powerful processor and a stunning display.",
                new BigDecimal("750.00"),
                1);

        if (bus.AddProduct(myProduct) > 0) {
            System.out.println("Tạo thành công product!");
        } else {
            System.out.println("Lỗi mày");
        }
        ;
    }
}
