package app.DTO;

public class Account {
	private int accountId;
	private String username;
	private String password;
	private String phoneNumber;
	private String email;
	private String fullName; // Họ và Tên
	private int roleId;
	private int status;

	// Constructors
	public Account() {}

	public Account(int accountId, String username, String password, String phoneNumber, String email, String fullName, int roleId, int status) {
		this.accountId = accountId;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.fullName = fullName;
		this.roleId = roleId;
		this.status = status;
	}

	public Account(String username, String password, String phoneNumber, String email, String fullName, int roleId) {
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.fullName = fullName;
		this.roleId = roleId;
	}

	public Account(int accountId, String username, String password, String phoneNumber, String email, String fullName, int roleId) {
		this.accountId = accountId;
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.fullName = fullName;
		this.roleId = roleId;
	}

	// Getters and Setters
	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return 
			"Account{" + 
			"accountId=" + accountId + 
			", username=" + username + 
			", password=" + password + 
			", phoneNumber=" + phoneNumber + 
			", email=" + email + 
			", fullName=" + fullName + 
			", roleId=" + roleId +
			", status=" + status + 
			"}";
	}
}