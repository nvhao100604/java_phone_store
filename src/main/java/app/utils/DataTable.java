package app.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataTable {
    public static final String directoryPath = "src/main/resources/excel";

    public static boolean createExcelFolder() {
        Path excel_path = Paths.get(directoryPath);
        Path exp_path = Paths.get(directoryPath + "/export");
        try {
            Files.createDirectories(excel_path);
            Files.createDirectories(exp_path);
            System.out.println("Tạo thư mục thành công hoặc thư mục đã tồn tại: " + directoryPath);
            return true;
        } catch (IOException e) {
            System.err.println("Lỗi khi tạo thư mục: " + e.getMessage());
            return false;
        }
    }

    public static Object[] dataFromTable(int selectedRow, DefaultTableModel tableModel) {
        int columnCount = tableModel.getColumnCount();
        Object[] rowData = new Object[columnCount];
        for (int i = 0; i < columnCount; i++) {
            rowData[i] = tableModel.getValueAt(selectedRow, i);
        }
        return rowData;
    }

    public static void exportDataToExcel(String filePath, JTable table) {
        TableModel model = table.getModel();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Dữ liệu Bảng");

        Row headerRow = sheet.createRow(0);
        for (int col = 0; col < model.getColumnCount(); col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(model.getColumnName(col));
        }

        for (int row = 0; row < model.getRowCount(); row++) {
            Row excelRow = sheet.createRow(row + 1);

            for (int col = 0; col < model.getColumnCount(); col++) {
                Cell excelCell = excelRow.createCell(col);

                Object value = model.getValueAt(row, col);

                if (value != null) {
                    if (value instanceof Number) {
                        excelCell.setCellValue(((Number) value).doubleValue());
                    } else {
                        excelCell.setCellValue(value.toString());
                    }
                } else {
                    excelCell.setCellValue("");
                }
            }
        }
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
            workbook.close();
            System.out.println("Xuất dữ liệu thành công ra file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi ghi file Excel: " + e.getMessage());
        }
    }
}
