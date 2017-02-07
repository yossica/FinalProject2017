package pettyCash;

import java.io.Serializable;

public class PettyCashBean implements Serializable {
	private int transactionPettyCashId;
	private String cashFlowCategoryId;
	private double amount;
	private double balance;
	private String description;
	private String transactionDate;
	private int isDebit;
	private String createdBy;
	private String createDate;
}
