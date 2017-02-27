package master;

import java.io.Serializable;

public class FinanceSummaryBean implements Serializable {
	private String period;
	private Double cashInBankExpense;
	private Double pettyCashExpense;

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Double getCashInBankExpense() {
		return cashInBankExpense;
	}

	public void setCashInBankExpense(Double cashInBankExpense) {
		this.cashInBankExpense = cashInBankExpense;
	}

	public Double getPettyCashExpense() {
		return pettyCashExpense;
	}

	public void setPettyCashExpense(Double pettyCashExpense) {
		this.pettyCashExpense = pettyCashExpense;
	}
}
