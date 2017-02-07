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
}
