package invoice;

import java.io.Serializable;

public class InvoiceDetailBean implements Serializable {
	private int transactionInvoiceDetailId;
	private int transactionInvoiceHeaderId;
	private String description;
	private Integer numb;
	private Double fee;
	private Double totalFee;
	private Double unitPrice;
	private Integer employeeId;
	private String employeeName;
	private int workDays;
	private int manDays;
	private String notes;
	private String createdBy;
	private String createdDate;
	private String changedBy;
	private String changedDate;
	
	public int getTransactionInvoiceDetailId() {
		return transactionInvoiceDetailId;
	}
	public void setTransactionInvoiceDetailId(int transactionInvoiceDetailId) {
		this.transactionInvoiceDetailId = transactionInvoiceDetailId;
	}
	public int getTransactionInvoiceHeaderId() {
		return transactionInvoiceHeaderId;
	}
	public void setTransactionInvoiceHeaderId(int transactionInvoiceHeaderId) {
		this.transactionInvoiceHeaderId = transactionInvoiceHeaderId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public int getManDays() {
		return manDays;
	}
	public void setManDays(int manDays) {
		this.manDays = manDays;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}
	public String getChangedDate() {
		return changedDate;
	}
	public void setChangedDate(String changedDate) {
		this.changedDate = changedDate;
	}
	public int getWorkDays() {
		return workDays;
	}
	public void setWorkDays(int workDays) {
		this.workDays = workDays;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Integer getNumb() {
		return numb;
	}
	public void setNumb(Integer numb) {
		this.numb = numb;
	}
}
