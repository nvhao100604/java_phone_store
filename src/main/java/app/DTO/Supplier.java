package app.DTO;

public class Supplier {
	private int idSupplier;
	private String nameSupplier;
	private String phoneNumber;
	private String address;
	private int status;

	// Constructors
	public Supplier() {
	}

	public Supplier(int idSupplier, String nameSupplier, String phoneNumber, String address, int status) {
		this.idSupplier = idSupplier;
		this.nameSupplier = nameSupplier;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.status = status;
	}

	// Getters and Setters
	public int getIdSupplier() {
		return idSupplier;
	}

	public void setIdSupplier(int idSupplier) {
		this.idSupplier = idSupplier;
	}

	public String getNameSupplier() {
		return nameSupplier;
	}

	public void setNameSupplier(String nameSupplier) {
		this.nameSupplier = nameSupplier;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Supplier{" + "idSupplier=" + idSupplier + ", nameSupplier='" + nameSupplier + '\'' + ", phoneNumber='"
				+ phoneNumber + '\'' + ", address='" + address + '\'' + ", status=" + status + '}';
	}
}
