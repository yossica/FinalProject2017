package cashInBank;

import java.io.Serializable;

public class CashInBankBean implements Serializable{
	private int transactionCashInBankId;
	private String cashFlowCategoryId;
	private double amount;
	private double balance;
	private String description;
	private String transactionDate;
	private int isDebit;
	private String createdBy;
	private String createDate;
	public int getTransactionCashInBankId() {
		return transactionCashInBankId;
	}
	public void setTransactionCashInBankId(int transactionCashInBankId) {
		this.transactionCashInBankId = transactionCashInBankId;
	}
	public String getCashFlowCategoryId() {
		return cashFlowCategoryId;
	}
	public void setCashFlowCategoryId(String cashFlowCategoryId) {
		this.cashFlowCategoryId = cashFlowCategoryId;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
