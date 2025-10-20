package app.DTO;

import java.math.BigDecimal;

public class Permission {

	private int permissionId;
	private String permissionName;
	private BigDecimal salary;
	private int status;

	// Constructors
	public Permission() {
	}

	public Permission(int permissionId, String permissionName, BigDecimal salary, int status) {
		this.permissionId = permissionId;
		this.permissionName = permissionName;
		this.salary = salary;
		this.status = status;
	}

	public Permission(int permissionId, String permissionName) {
		this.permissionId = permissionId;
		this.permissionName = permissionName;
	}

	// Getters and Setters
	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return permissionName;
	}
}