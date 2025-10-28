package app.BUS;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import app.DAO.ImportSlipDAO;
import app.DTO.ImportSlip;

public class ImportSlipBUS {
    private ImportSlipDAO dao;
    
    public ImportSlipBUS() {
        dao = new ImportSlipDAO();
    }

    public List<ImportSlip> getAllImportSlips() {
        return dao.getAllImportSlips();
    }

    public List<ImportSlip> getAllActiveImportSlips() {
        return dao.getAllActiveImportSlips();
    }

    public int addImportSlip(ImportSlip importSlip) {
        return dao.addImportSlip(importSlip);
    }

    public int updateImportSlip(ImportSlip importSlip) {
        return dao.updateImportSlip(importSlip);
    }

    public int deleteImportSlip(int importSlipId) {
        return dao.deleteImportSlip(importSlipId);
    }

    public int softDeleteImportSlip(int importSlipId) {
        return dao.softDeleteImportSlip(importSlipId);
    }

    public int restoreImportSlip(int importSlipId) {
        return dao.restoreImportSlip(importSlipId);
    }

    public boolean importDataFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                int supplierId = (int) row.getCell(0).getNumericCellValue();
                Date importDate = new java.sql.Date(row.getCell(1).getDateCellValue().getTime());
                BigDecimal totalAmount = new BigDecimal(row.getCell(2).getNumericCellValue());
                int profit = (int) row.getCell(3).getNumericCellValue();
                int status = 1;

                ImportSlip newSlip = new ImportSlip(
                        supplierId,
                        importDate,
                        totalAmount,
                        profit);
                dao.addImportSlip(newSlip);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Lỗi đọc file Excel: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi xử lý dữ liệu: " + e.getMessage());
            return false;
        }
    }

    public List<ImportSlip> searchImportSlips(String keyword) {
        return dao.searchImportSlips(keyword);
    }

    public List<ImportSlip> filterImportSlips(Date startDate, Date endDate) {
        return dao.filterImportSlipsByDate(startDate, endDate);
    }

    public List<ImportSlip> sortImportSlips(String sortBy, boolean ascending) {
        return dao.sortImportSlips(sortBy, ascending);
    }
 // ==================== SEARCH FUNCTION ====================
    public List<ImportSlip> searchImportSlipsAdvanced(String keyword) {
        keyword = keyword.toLowerCase().trim();
        List<ImportSlip> results = new java.util.ArrayList<>();

        List<ImportSlip> allSlips = getAllImportSlips(); // Lấy toàn bộ danh sách phiếu nhập từ DAO

        for (ImportSlip slip : allSlips) {
            boolean matchSupplier = String.valueOf(slip.getSupplierId()).contains(keyword);
            boolean matchTotal = slip.getTotalAmount() != null &&
                                 slip.getTotalAmount().toPlainString().toLowerCase().contains(keyword);
            boolean matchDate = slip.getImportDate() != null &&
                                slip.getImportDate().toString().contains(keyword);

            if (matchSupplier || matchTotal || matchDate) {
                results.add(slip);
            }
        }

        return results;
    }
    public List<ImportSlip> filterImportSlipstotal(BigDecimal minTotal, BigDecimal maxTotal) {
        List<ImportSlip> all = getAllActiveImportSlips();
        return all.stream().filter(slip -> {
            boolean match = true;

            // Lọc theo "Thành tiền"
            if (minTotal != null && slip.getTotalAmount().compareTo(minTotal) < 0) match = false;
            if (maxTotal != null && slip.getTotalAmount().compareTo(maxTotal) > 0) match = false;

            return match;
        }).collect(Collectors.toList());
    }
}
