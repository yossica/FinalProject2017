package index;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class IndexForm  extends ActionForm{
	private String taskIndex;

	private List listedRemainderList;
	private List listedTrainingRemainderList;
	private List createdRemainderList;
	private List sentOutsourceRemainderList;
    
	//finance summary
	private double cashInBankBalance;
	private double pettyCashBalance;
	private List financeSummaryList;

	public String getTaskIndex() {
		return taskIndex;
	}

	public void setTaskIndex(String taskIndex) {
		this.taskIndex = taskIndex;
	}
  
	public List getListedRemainderList() {
		return listedRemainderList;
	}

	public void setListedRemainderList(List listedRemainderList) {
		this.listedRemainderList = listedRemainderList;
	}

	public List getCreatedRemainderList() {
		return createdRemainderList;
	}

	public void setCreatedRemainderList(List createdRemainderList) {
		this.createdRemainderList = createdRemainderList;
	}

	public List getSentOutsourceRemainderList() {
		return sentOutsourceRemainderList;
	}

	public void setSentOutsourceRemainderList(List sentOutsourceRemainderList) {
		this.sentOutsourceRemainderList = sentOutsourceRemainderList;
	}

	public List getListedTrainingRemainderList() {
		return listedTrainingRemainderList;
	}

	public void setListedTrainingRemainderList(
			List listedTrainingRemainderList) {
		this.listedTrainingRemainderList = listedTrainingRemainderList;
	}

	public double getCashInBankBalance() {
		return cashInBankBalance;
	}

	public void setCashInBankBalance(double cashInBankBalance) {
		this.cashInBankBalance = cashInBankBalance;
	}

	public double getPettyCashBalance() {
		return pettyCashBalance;
	}

	public void setPettyCashBalance(double pettyCashBalance) {
		this.pettyCashBalance = pettyCashBalance;
	}

	public List getFinanceSummaryList() {
		return financeSummaryList;
	}

	public void setFinanceSummaryList(List financeSummaryList) {
		this.financeSummaryList = financeSummaryList;
	}

}
