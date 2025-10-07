package app.DTO;

import app.GUI.PermissionGUI;
import app.GUI.ProductGUI;
import app.GUI.qlkho_phieunhap;
import app.GUI.qltaikhoan;

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
			case 2:
				return ProductGUI.class;
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
				return PermissionGUI.class;
			case 8:
			case 9:
			case 10:
			case 11:
				return qlkho_phieunhap.class;
			default:
				return ProductGUI.class; // or a default class
		}
	}
}
