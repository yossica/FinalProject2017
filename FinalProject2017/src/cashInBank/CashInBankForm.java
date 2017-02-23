package cashInBank;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class CashInBankForm extends ActionForm{
	private String task;
	private List messageList;
	
	private double remainingBalance;
	private double pettyCashBalance;
	private List cashFlowCategoryList;
	
	private CashInBankBean cashInBankBean = new CashInBankBean();
	
	//cash in bank view
	private List transactionList;
	//cash in bank view - filter
	private String categoryId;
	private String filterStartDate;
	private String filterEndDate;
		
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
	public double getPettyCashBalance() {
		return pettyCashBalance;
	}
	public void setPettyCashBalance(double pettyCashBalance) {
		this.pettyCashBalance = pettyCashBalance;
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
	
	public CashInBankBean getCashInBankBean() {
		return cashInBankBean;
	}
	public void setCashInBankBean(CashInBankBean cashInBankBean) {
		this.cashInBankBean = cashInBankBean;
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
}
