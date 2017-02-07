package invoice;

import java.io.Serializable;
import java.util.List;

public class InvoiceBean implements Serializable {
	private int transactionInvoiceHeaderId;
	private String invoiceNumber;
	private String invoiceDate;
	private int periodMonth;
	private int periodYear;
	private int statusInvoiceId;
	private String statusInvoiceName;
	
	private int invoiceTypeId;	
	private String invoiceTypeName;
	
	private int clientId;
	private String clientName;
	
	private double totalNet;
	private float ppnPercentage;
	private int isGross;
	private double totalGross;
	private String notes;
	private String createdBy;
	private String createDate;
	private String changedBy;
	private String changedDate;
	
	private List<InvoiceDetailBean> detailList;
}
	