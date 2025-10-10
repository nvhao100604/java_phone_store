package app.BUS;

import java.util.List;

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

    public List<Company> searchCompanies(String keyword) {
        return dao.searchCompanies(keyword);
    }

    public List<Company> fillterCompanies(int status) {
        return dao.fillterCompanies(status);
    }
    
    public static void main(String[] args) {
        CompanyBUS bus = new CompanyBUS();
        List<Company> list = bus.getAll();
        for (Company company : list) {
            System.out.println("Company check: " + company.toString());
        }
    }
}