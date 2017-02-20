package invoice;

import java.io.Serializable;

public class InvoiceDetailBean implements Serializable {
	private Integer transactionInvoiceDetailId;
	private Integer transactionInvoiceHeaderId;
	private Integer numb;
	private String description;
	private Double fee;
	private Double totalFee;
	private Double unitPrice;
	private Double total;
	private Integer employeeId;
	private String employeeName;
	private Integer workDays;
	private Integer manDays;
	private String notes;
	private String createdBy;
	private String createdDate;
	private String changedBy;
	private String changedDate;
	public Integer getTransactionInvoiceDetailId() {
		return transactionInvoiceDetailId;
	}
	public void setTransactionInvoiceDetailId(Integer transactionInvoiceDetailId) {
		this.transactionInvoiceDetailId = transactionInvoiceDetailId;
	}
	public Integer getTransactionInvoiceHeaderId() {
		return transactionInvoiceHeaderId;
	}
	public void setTransactionInvoiceHeaderId(Integer transactionInvoiceHeaderId) {
		this.transactionInvoiceHeaderId = transactionInvoiceHeaderId;
	}
	public Integer getNumb() {
		return numb;
	}
	public void setNumb(Integer numb) {
		this.numb = numb;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
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
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Integer getWorkDays() {
		return workDays;
	}
	public void setWorkDays(Integer workDays) {
		this.workDays = workDays;
	}
	public Integer getManDays() {
		return manDays;
	}
	public void setManDays(Integer manDays) {
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
}
