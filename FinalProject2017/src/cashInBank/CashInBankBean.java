package cashInBank;

import java.io.Serializable;

public class CashInBankBean implements Serializable {
	private int transactionCashInBankId;
	private String cashFlowCategoryId;
	private String cashFlowCategoryName;

	private Double amount;
	private Double balance;
	private String description;
	private String transactionDate;
	private int isDebit;
	private String createdBy;
	private String createdDate;

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

	public String getCashFlowCategoryName() {
		return cashFlowCategoryName;
	}

	public void setCashFlowCategoryName(String cashFlowCategoryName) {
		this.cashFlowCategoryName = cashFlowCategoryName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
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
