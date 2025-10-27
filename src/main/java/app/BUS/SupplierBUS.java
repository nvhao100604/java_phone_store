package app.BUS;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

    public Supplier getSupplierById(int supplierId) {
        return dao.getSupplierById(supplierId);
    }

    public String getSupplierNameById(int supplierId) {
        List<Supplier> list = getAllSuppliers();
        for (Supplier s : list) {
            if (s.getIdSupplier() == supplierId) {
                return s.getNameSupplier();
            }
        }
        return null;
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

    public int restoreSupplier(int idSupplier) {
        return dao.restoreSupplier(idSupplier);
    }

    public List<Supplier> searchSuppliers(String keyword) {
        return dao.searchSuppliers(keyword);
    }

    public List<Supplier> searchSuppliers(String keyword, int status) {
        return dao.searchSuppliers(keyword, status);
    }

    public List<Supplier> fillterSuppliers(int status) {
        return dao.fillterSuppliers(status);
    }

    public boolean importDataFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            SupplierBUS supplierBUS = new SupplierBUS();

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                String supplierName = getCellStringValue(row.getCell(0));
                String phoneNumber = getCellStringValue(row.getCell(1));
                String address = getCellStringValue(row.getCell(2));

                if (supplierName.isEmpty()) {
                    System.out.println("Dòng " + (rowIndex + 1) + " bị bỏ qua do thiếu tên nhà cung cấp.");
                    continue;
                }

                Supplier newSupplier = new Supplier(supplierName, phoneNumber, address);
                int supplierId = supplierBUS.addSupplier(newSupplier);
                if (supplierId == -1) {
                    System.out.println("Thêm nhà cung cấp tại dòng " + (rowIndex + 1) + " thất bại.");
                    continue;
                }
            }
            workbook.close();
            return true;
        } catch (IOException e) {
            System.err.println("Lỗi đọc file Excel: " + e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi nhập dữ liệu từ Excel: " + e.getMessage());
            return false;
        }
    }

    private String getCellStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        }

        return "";
    }
}
