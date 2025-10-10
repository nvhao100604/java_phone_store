package app.BUS;

import app.DAO.CategoryDAO;

public class CategoryBUS {

    private CategoryDAO dao;

    public CategoryBUS() {
        dao = new CategoryDAO();
    }

    public java.util.List<app.DTO.Category> getAllCategories() {
        return dao.getAllCategories();
    }
}
