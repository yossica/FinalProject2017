package pettyCash;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class PettyCashForm extends ActionForm{
	private String task;
	private List messageList;
	
	private double remainingBalance;
	private List cashFlowCategoryList;
	
	private PettyCashBean pettyCashBean = new PettyCashBean();
	
	//petty cash view
	private List transactionList;
	//petty cash view - filter
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
	public List getCashFlowCategoryList() {
		return cashFlowCategoryList;
	}
	public void setCashFlowCategoryList(List cashFlowCategoryList) {
		this.cashFlowCategoryList = cashFlowCategoryList;
	}
	
	public PettyCashBean getPettyCashBean() {
		return pettyCashBean;
	}
	public void setPettyCashBean(PettyCashBean pettyCashBean) {
		this.pettyCashBean = pettyCashBean;
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
}
