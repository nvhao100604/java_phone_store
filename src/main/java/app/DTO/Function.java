package app.DTO;

import app.GUI.StatisticGUI;
import app.GUI.ProductGUI.ProductGUI;
import app.GUI.ProductTypeGUI.ProductTypeGUI;
import app.GUI.SupplierGUI.SupplierGUI;
import app.GUI.qlkho_phieunhap;
import app.GUI.qlkhuyenmai;
import app.GUI.EmployeeGUI.quanlynhanvien;
import app.GUI.ImportSlipGUI.ImportSlipGUI;
import app.GUI.PermissionGUI.PermissionGUI;
import app.GUI.CompanyGUI.CompanyGUI;

public class Function {

	private int functionId;
	private String functionName;
	private String icon;
	private int status;

	// Constructors
	public Function() {
	}

	public Function(int functionId, String functionName, String icon, int status) {
		this.functionId = functionId;
		this.functionName = functionName;
		this.icon = icon;
		this.status = status;
	}

	public Function(int functionId, String functionName) {
		this.functionId = functionId;
		this.functionName = functionName;
	}

	// Getters and Setters
	public int getFunctionId() {
		return functionId;
	}

	public void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Class<?> getGuiClass() {
		switch (functionId) {
			case 1:
				return quanlynhanvien.class;
			case 2:
				return PermissionGUI.class;
			case 3:
				return ProductGUI.class;
			case 4:
				return ProductTypeGUI.class;
			case 5:
				return SupplierGUI.class;
			case 6:
				return CompanyGUI.class;
			case 8:
				return ImportSlipGUI.class;
			case 9:
				return qlkhuyenmai.class;
			case 10:
				return StatisticGUI.class;
			default:
				return ProductGUI.class;
		}
	}
}
