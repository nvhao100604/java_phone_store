package app.BUS;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import app.DAO.EmployeeDAO;
import app.DTO.Account;
import app.DTO.Employee;

public class EmployeeBUS {
    private EmployeeDAO dao;

    public EmployeeBUS() {
        dao = new EmployeeDAO();
    }

    public List<Employee> getAll() {
        return dao.getAll();
    }

    public int addEmployee(Employee employee) {
        return dao.addEmployee(employee);
    }

    public Employee getEmployeeById(int employeeId) {
        return dao.getEmployeeById(employeeId);
    }

    public List<Employee> getEmployeeGenderList() {
        return dao.getEmployeeGenderList();
    }

    public int deleteEmployee(int employeeId) {
        return dao.deleteEmployee(employeeId);
    }

    public int softDeleteEmployee(int employeeId) {
        return dao.softDeleteEmployee(employeeId);
    }

    public int restoreEmployee(int employeeId) {
        return dao.restoreEmployee(employeeId);
    }

    public int updateEmployee(Employee employee) {
        return dao.updateEmployee(employee);
    }

    public List<Employee> searchEmployees(String keyword) {
        return dao.searchEmployees(keyword);
    }

    public List<Employee> searchEmployees(String keyword, int status) {
        return dao.searchEmployees(keyword, status);
    }

    public List<Employee> fillterEmployeesByStatus(int status) {
        return dao.fillterEmployeesByStatus(status);
    }

    public boolean importDataFromExcel(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            AccountBUS accountBUS = new AccountBUS();
            EmployeeBUS employeeBUS = new EmployeeBUS();

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                String username = getCellStringValue(row.getCell(0));
                String password = getCellStringValue(row.getCell(1));
                String fullName = getCellStringValue(row.getCell(2));
                String phone = getCellStringValue(row.getCell(3));
                String email = getCellStringValue(row.getCell(4));
                String gender = getCellStringValue(row.getCell(5));
                Cell dobCell = row.getCell(6);
                String address = getCellStringValue(row.getCell(7));

                if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
                    System.err.println("Dòng " + (rowIndex + 1) + " bị bỏ qua: thiếu thông tin bắt buộc.");
                    continue;
                }

                int permissionId = 1;

                Account newAccount = new Account(username, password, phone, email, fullName, permissionId);
                int accountId = accountBUS.addAccount(newAccount);
                if (accountId <= 0) {
                    System.err.println("Lỗi thêm tài khoản tại dòng " + (rowIndex + 1));
                    continue;
                }

                Date dob = null;
                try {
                    if (dobCell != null) {
                        if (dobCell.getCellType() == CellType.NUMERIC) {
                            java.util.Date excelDate = DateUtil.getJavaDate(dobCell.getNumericCellValue());
                            dob = new java.sql.Date(excelDate.getTime());
                        } else {
                            String dobStr = dobCell.getStringCellValue().trim();
                            if (!dobStr.isEmpty()) {
                                java.util.Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(dobStr);
                                dob = new java.sql.Date(parsedDate.getTime());
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Lỗi định dạng ngày sinh tại dòng " + (rowIndex + 1));
                }

                if (dob == null) {
                    System.err.println("Không thể đọc ngày sinh tại dòng " + (rowIndex + 1));
                    continue;
                }

                Employee newEmployee = new Employee(accountId, gender, dob, address);
                int employeeId = employeeBUS.addEmployee(newEmployee);
                if (employeeId > 0) {
                    System.out.println("Đã nhập nhân viên (ID: " + employeeId + ")");
                } else {
                    System.err.println("Lỗi thêm nhân viên tại dòng " + (rowIndex + 1));
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
        if (cell == null)
            return "";
        if (cell.getCellType() == CellType.STRING)
            return cell.getStringCellValue().trim();
        if (cell.getCellType() == CellType.NUMERIC) {
            double value = cell.getNumericCellValue();
            if (DateUtil.isCellDateFormatted(cell)) {
                java.util.Date date = DateUtil.getJavaDate(value);
                return new SimpleDateFormat("yyyy-MM-dd").format(date);
            } else {
                return new DecimalFormat("#").format(value);
            }
        }
        return "";
    }

    public String getEmployeeNameById(int employeeId) {
        return dao.getEmployeeNameById(employeeId);
    }
}