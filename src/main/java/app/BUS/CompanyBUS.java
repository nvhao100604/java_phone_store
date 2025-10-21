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

import app.DAO.CompanyDAO;
import app.DTO.Company;

public class CompanyBUS {
    private CompanyDAO dao;

    public CompanyBUS() {
        dao = new CompanyDAO();
    }

    public List<Company> getAll() {
        return dao.getAll();
    }

    public Company getCompanyById(int companyId) {
        return dao.getCompanyById(companyId);
    }   

    public int addCompany(Company company) {
        return dao.addCompany(company);
    }

    public int updateCompany(Company company) {
        return dao.updateCompany(company);
    }

    public int deleteCompany(int companyId) {
        return dao.deleteCompany(companyId);
    }

    public int softDeleteCompany(int companyId) {
        return dao.softDeleteCompany(companyId);
    }

    public int restoreCompany(int companyId) {
        return dao.restoreCompany(companyId);
    }

    public List<Company> searchCompanies(String keyword) {
        return dao.searchCompanies(keyword);
    }

    public List<Company> searchCompanies(String keyword, int status) {
        return dao.searchCompanies(keyword, status);
    }

    public List<Company> fillterCompanies(int status) {
        return dao.fillterCompanies(status);
    }
    
    public boolean importDataFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            CompanyBUS companyBUS = new CompanyBUS();

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                String companyName = getCellStringValue(row.getCell(0));

                if (companyName.isEmpty()) {
                    System.out.println("Dòng " + (rowIndex + 1) + " bị bỏ qua do thiếu tên nhà sản xuất.");
                    continue;
                }

                Company newCompany = new Company(companyName);
                int companyId = companyBUS.addCompany(newCompany);
                if (companyId == -1) {
                    System.out.println("Thêm nhà sản xuất tại dòng " + (rowIndex + 1) + " thất bại.");
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