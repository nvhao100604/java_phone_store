package app.BUS;

import java.util.List;

import app.DAO.FunctionDAO;
import app.DTO.Function;

public class FunctionBUS {
    private FunctionDAO DAO;

    public FunctionBUS() {
        DAO = new FunctionDAO();
    }

    public List<Function> getAll() {
        return DAO.getAll();
    }

    public List<Function> getFunction() {
        return DAO.getFunction();
    } 
}
