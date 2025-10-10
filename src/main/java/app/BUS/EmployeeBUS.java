package app.BUS;

import java.util.List;

import app.DAO.EmployeeDAO;
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

    public int deleteEmployee(int employeeId) {
        return dao.deleteEmployee(employeeId);
    }

    public int updateEmployee(Employee employee) {
        return dao.updateEmployee(employee);
    }

    public List<Employee> searchEmployees(String keyword) {
        return dao.searchEmployees(keyword);
    }

    public List<Employee> fillterEmployeesByStatus(int status) {
        return dao.fillterEmployeesByStatus(status);
    }

    public static void main(String[] args) {
        EmployeeBUS bus = new EmployeeBUS();
        List<Employee> list = bus.getAll();
        for (Employee emp : list) {
            System.out.println("Employee check: " + emp.toString());
        }
    }
}