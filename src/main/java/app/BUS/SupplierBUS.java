package app.BUS;

import java.util.List;
import app.DAO.SupplierDAO;
import app.DTO.Supplier;

public class SupplierBUS {

    private final SupplierDAO dao;

    public SupplierBUS() {
        this.dao = new SupplierDAO();
    }

    public List<Supplier> getAllSuppliers() {
        return dao.getAllSuppliers();
    }

    public List<Supplier> getActiveSuppliers() {
        return dao.getActiveSuppliers();
    }

    public int addSupplier(Supplier supplier) {
        return dao.addSupplier(supplier);
    }

    public int updateSupplier(Supplier supplier) {
        return dao.updateSupplier(supplier);
    }

    public int deleteSupplier(int idSupplier) {
        return dao.deleteSupplier(idSupplier);
    }

    public int softDeleteSupplier(int idSupplier) {
        return dao.softDeleteSupplier(idSupplier);
    }

    public List<Supplier> searchSuppliers(String keyword) {
        return dao.searchSuppliers(keyword);
    }

}
