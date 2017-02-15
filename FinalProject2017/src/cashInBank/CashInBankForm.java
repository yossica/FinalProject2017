package cashInBank;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class CashInBankForm extends ActionForm{
	private String task;
	private List messageList;
	
	private double remainingBalance;
	private List cashFlowCategoryList;
	
	//cash in bank view
	private List transactionList;
	//cash in bank view - filter
	private String categoryId;
	private String filterStartDate;
	private String filterEndDate;
	
	//cash in bank form
	private Integer isDebit;
	private String transactionDate;
	private String cashFlowCategoryId;
	private Double amount;
	private String description;
	
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public List getMessageList() {
		if(messageList == null)
			messageList = new ArrayList();
		return messageList;
	}
	public void setMessageList(List messageList) {
		this.messageList = messageList;
	}
	public double getRemainingBalance() {
		return remainingBalance;
	}
	public void setRemainingBalance(double remainingBalance) {
		this.remainingBalance = remainingBalance;
	}
	public List getCashFlowCategoryList() {
		return cashFlowCategoryList;
	}
	public void setCashFlowCategoryList(List cashFlowCategoryList) {
		this.cashFlowCategoryList = cashFlowCategoryList;
	}
	public List getTransactionList() {
		return transactionList;
	}
	public void setTransactionList(List transactionList) {
		this.transactionList = transactionList;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getFilterStartDate() {
		return filterStartDate;
	}
	public void setFilterStartDate(String filterStartDate) {
		this.filterStartDate = filterStartDate;
	}
	public String getFilterEndDate() {
		return filterEndDate;
	}
	public void setFilterEndDate(String filterEndDate) {
		this.filterEndDate = filterEndDate;
	}
	public Integer getIsDebit() {
		return isDebit;
	}
	public void setIsDebit(Integer isDebit) {
		this.isDebit = isDebit;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getCashFlowCategoryId() {
		return cashFlowCategoryId;
	}
	public void setCashFlowCategoryId(String cashFlowCategoryId) {
		this.cashFlowCategoryId = cashFlowCategoryId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
		
}
