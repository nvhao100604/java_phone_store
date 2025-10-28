package app.DTO;

public enum PaymentMethod {
	CASH("Tiền mặt"),
	BANK_TRANSFER("Chuyển khoản");

	private final String displayName;

	PaymentMethod(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}

}
