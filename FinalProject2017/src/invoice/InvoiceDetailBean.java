package invoice;

import java.io.Serializable;

public class InvoiceDetailBean implements Serializable {
	private int transactionInvoiceDetailId;
	private int transactionInvoiceHeaderId;
	private String description;
	private double fee;
	private int employeeId;
	private String employeeName;
	private int manDays;
	private String notes;
	private String createdBy;
	private String createdDate;
	private String changedBy;
	private String changedDate;
	
}
