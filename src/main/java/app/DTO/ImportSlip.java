package app.DTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ImportSlip {

	private int importSlipId;
	private int supplierId;
	private Date importDate;
	private BigDecimal totalAmount;
	private int profit;
	private int status;
	private List<ImportSlipDetail> details;

	// Constructors
	public ImportSlip() {
		this.details = new ArrayList<>();
	}

	public ImportSlip(int importSlipId, int supplierId, Date importDate, BigDecimal totalAmount, int profit,
			int status) {
		this.importSlipId = importSlipId;
		this.supplierId = supplierId;
		this.importDate = importDate;
		this.totalAmount = totalAmount;
		this.profit = profit;
		this.status = status;
		this.details = new ArrayList<>();
	}

	// Getters and Setters
	public int getImportSlipId() {
		return importSlipId;
	}

	public void setImportSlipId(int importSlipId) {
		this.importSlipId = importSlipId;
	}

	public int getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<ImportSlipDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ImportSlipDetail> details) {
		this.details = details;
	}
}