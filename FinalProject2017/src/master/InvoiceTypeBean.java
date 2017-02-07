package master;

import java.io.Serializable;

public class InvoiceTypeBean implements Serializable {
	private int invoiceTypeId;
	private String name;
	private int isEnabled;
	private String createdBy;
	private String createDate;
	private String changedBy;
	private String changedDate;	
}
