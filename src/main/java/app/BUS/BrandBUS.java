package app.BUS;

import app.DAO.BrandDAO;
import app.DTO.Brand;

public class BrandBUS {

    private final BrandDAO dao;

    public BrandBUS() {
        dao = new BrandDAO();
    }

    public java.util.List<Brand> getAllBrands() {
        return dao.getAllBrands();
    }
}
