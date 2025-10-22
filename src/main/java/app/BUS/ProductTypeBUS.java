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

    public int softDeleteProductType(int productTypeId) {
        return dao.softDeleteProductType(productTypeId);
    }

    public int restoreProductType(int productTypeId) {
        return dao.restoreProductType(productTypeId);
    }

    public List<ProductType> searchProductTypes(String keyword) {
        return dao.searchProductTypes(keyword);
    }

    public List<ProductType> searchProductTypes(String keyword, int status) {
        return dao.searchProductTypes(keyword, status);
    }

    public List<ProductType> fillterProductTypes(int status) {
        return dao.fillterProductTypes(status);
    }

    public boolean importDataFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            ProductTypeBUS productTypeBUS = new ProductTypeBUS();

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                String productTypeName = getCellStringValue(row.getCell(0));

                if (productTypeName.isEmpty()) {
                    System.out.println("Dòng " + (rowIndex + 1) + " bị bỏ qua do thiếu tên loại sản phẩm.");
                    continue;
                }

                ProductType newProductType = new ProductType(productTypeName);
                int productTypeId = productTypeBUS.addProductType(newProductType);
                if (productTypeId == -1) {
                    System.out.println("Thêm loại sản phẩm tại dòng " + (rowIndex + 1) + " thất bại.");
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