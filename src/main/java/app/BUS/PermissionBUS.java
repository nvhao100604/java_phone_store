package app.BUS;

import java.util.List;

import app.DAO.PermissionDAO;
import app.DTO.Permission;

public class PermissionBUS {
    private PermissionDAO dao;    

    public PermissionBUS() {
        dao = new PermissionDAO();
    }

    public List<Permission> getAll() {
        return dao.getAll();
    }

    public List<Permission> getRole() {
        return dao.getRole();
    }

    public boolean checkPermission(int roleId, int functionId) {
        return dao.checkPermission(roleId, functionId);
    }

    public int addPermission(int roleId, int functionId) {
        return dao.addPermission(roleId, functionId);
    }

    public int removePermission(int roleId, int functionId) {
        return dao.removePermission(roleId, functionId);
    }

    public int addNewRole(Permission permission) {
        return dao.addNewRole(permission);
    }

    public int updateRole(Permission permission) {
        return dao.updateRole(permission);
    }
}
