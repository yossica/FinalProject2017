package pettyCash;

import java.io.Serializable;

public class PettyCashBean implements Serializable {
	private int transactionPettyCashId;
	private String cashFlowCategoryId;
	private String cashFlowCategoryName;
	private double amount;
	private double balance;
	private String description;
	private String transactionDate;
	private int isDebit;
	private String createdBy;
	private String createdDate;
	public int getTransactionPettyCashId() {
		return transactionPettyCashId;
	}
	public void setTransactionPettyCashId(int transactionPettyCashId) {
		this.transactionPettyCashId = transactionPettyCashId;
	}
	public String getCashFlowCategoryId() {
		return cashFlowCategoryId;
	}
	public void setCashFlowCategoryId(String cashFlowCategoryId) {
		this.cashFlowCategoryId = cashFlowCategoryId;
	}
	public String getCashFlowCategoryName() {
		return cashFlowCategoryName;
	}
	public void setCashFlowCategoryName(String cashFlowCategoryName) {
		this.cashFlowCategoryName = cashFlowCategoryName;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public int getIsDebit() {
		return isDebit;
	}
	public void setIsDebit(int isDebit) {
		this.isDebit = isDebit;
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
	
}
