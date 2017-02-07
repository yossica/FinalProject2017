package outsource;

import java.io.Serializable;

public class OutsourceBean implements Serializable {
	private int transactionOutsourceId;
	private int clientId;
	private int employeeId;
	private String startDate;
	private String endDate;
	private int isGross;
	private double fee;
	private String createdBy;
	private String createDate;
	private String changedBy;
	private String changedDate;
	public int getTransactionOutsourceId() {
		return transactionOutsourceId;
	}
	public void setTransactionOutsourceId(int transactionOutsourceId) {
		this.transactionOutsourceId = transactionOutsourceId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getIsGross() {
		return isGross;
	}
	public void setIsGross(int isGross) {
		this.isGross = isGross;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
