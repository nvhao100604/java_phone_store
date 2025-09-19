package app.BUS;

import java.util.List;

import app.DAO.CustomerDAO;
import app.DTO.Customer;

public class CustomerBUS {
    private CustomerDAO dao;    

    public CustomerBUS() {
        dao = new CustomerDAO();
    }

    public List<Customer> getAll() {
        return dao.getAll();
    }

    public Customer getCustomerById(int customerId) {
        return dao.getCustomerById(customerId);
    }
    
    public int addCustomer(Customer customer) {
        return dao.addCustomer(customer);
    }

    public int updateCustomer(Customer customer) {
        return dao.updateCustomer(customer);
    }

    public int deleteCustomer(int customerId) {
        return dao.deleteCustomer(customerId);
    }

    public static void main(String[] args) {
        CustomerBUS bus = new CustomerBUS();
        List<Customer> list = bus.getAll();
        for (Customer cust : list) {
            System.out.println("Customer check: " + cust.toString());
        }
    }
}
