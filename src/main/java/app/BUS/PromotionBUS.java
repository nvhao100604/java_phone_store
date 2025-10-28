package app.BUS;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import app.DAO.PromotionDAO;
import app.DTO.Promotion;

public class PromotionBUS {

    private final PromotionDAO dao;

    public PromotionBUS() {
        this.dao = new PromotionDAO();
    }

    public List<Promotion> getAllPromotions() {
        return dao.getAllPromotions();
    }

    public Promotion getPromotionById(int promotionId) {
        return dao.getPromotionById(promotionId);
    }

    public List<Promotion> getActivePromotions() {
        return dao.getActivePromotions();
    }

    public List<Promotion> getValidPromotions(int brandId, int categoryId, Date currentDate) {
        return dao.getValidPromotions(brandId, categoryId, currentDate);
    }

    public int addPromotion(Promotion promotion) {
        return dao.addPromotion(promotion);
    }

    public int updatePromotion(Promotion promotion) {
        return dao.updatePromotion(promotion);
    }

    public int updatePromotionQuantity(int quantity, int promotionId) {
        return dao.updatePromotionQuantity(promotionId, quantity);
    }

    public int deactivatePromotion(int promotionId) {
        return dao.deactivatePromotion(promotionId);
    }

    public int activatePromotion(int promotionId) {
        return dao.activatePromotion(promotionId);
    }

    public int deletePromotion(int promotionId) {
        return dao.deletePromotion(promotionId);
    }

    public Promotion getPromotionByCode(String code) {
        return dao.getPromotionByCode(code);
    }

    public List<Promotion> searchPromotions(String keyword) {
        return dao.searchPromotions(keyword);
    }

    public List<Promotion> filterPromotions(int brandId, int categoryId, int status) {
        return dao.filterPromotions(brandId, categoryId, status);
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

                Promotion promotion = new Promotion();

                promotion.setCode(row.getCell(0).toString());
                promotion.setDescription(row.getCell(1).toString());
                int isPercentValue = (int) row.getCell(2).getNumericCellValue();
                boolean isPercent = (isPercentValue == 1);
                promotion.setIsPercent(isPercent);
                promotion.setValue(BigDecimal.valueOf(row.getCell(3).getNumericCellValue()));
                promotion.setPercent((int) row.getCell(4).getNumericCellValue());
                promotion.setQuantity((int) row.getCell(5).getNumericCellValue());
                if (row.getCell(6).getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(row.getCell(6))) {
                    promotion.setStartDate(row.getCell(6).getDateCellValue());
                } else {
                    promotion.setStartDate(java.sql.Date.valueOf(row.getCell(6).toString()));
                }
                if (row.getCell(7).getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(row.getCell(7))) {
                    promotion.setExpirationDate(row.getCell(7).getDateCellValue());
                } else {
                    promotion.setExpirationDate(java.sql.Date.valueOf(row.getCell(7).toString()));
                }
                promotion.setBrandId((int) row.getCell(8).getNumericCellValue());
                promotion.setCategoryId((int) row.getCell(9).getNumericCellValue());
                promotion.setStatus((int) row.getCell(10).getNumericCellValue());

                int result = dao.addPromotion(promotion);
                System.out.println("Đã thêm khuyến mãi: " + promotion.getCode() + " (Kết quả: " + result + ")");
            }
            return true;
        } catch (IOException e) {
            System.err.println("Lỗi đọc file Excel: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi xử lý dữ liệu Excel: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}