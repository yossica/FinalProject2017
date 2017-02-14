package index;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class IndexForm  extends ActionForm{
	private String taskIndex;
	
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
