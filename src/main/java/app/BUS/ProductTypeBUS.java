package app.BUS;

import java.util.List;

import app.DAO.ProductTypeDAO;
import app.DTO.ProductType;

public class ProductTypeBUS {
    private ProductTypeDAO dao;

    public ProductTypeBUS() {
        dao = new ProductTypeDAO();
    }

    public List<ProductType> getAll() {
        return dao.getAll();
    }

    public ProductType getProductTypeById(int productTypeId) {
        return dao.getProductTypeById(productTypeId);
    }

    public int addProductType(ProductType productType) {
        return dao.addProductType(productType);
    }

    public int updateProductType(ProductType productType) {
        return dao.updateProductType(productType);
    }

    public int deleteProductType(int productTypeId) {
        return dao.deleteProductType(productTypeId);
    }

    public static void main(String[] args) {
        ProductTypeBUS bus = new ProductTypeBUS();
        List<ProductType> list = bus.getAll();
        for (ProductType productType : list) {
            System.out.println("Product Type check: " + productType.toString());
        }
    }
}