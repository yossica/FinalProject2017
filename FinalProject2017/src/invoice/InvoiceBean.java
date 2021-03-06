package invoice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InvoiceBean implements Serializable {
	private int transactionInvoiceHeaderId;
	private String invoiceNumber;
	private String invoiceDate;
	private int periodMonth;
	private String periodMonthName;
	private int periodYear;
	private int statusInvoiceId;
	private String statusInvoiceName;
	private int invoiceTypeId;
	private String invoiceTypeName;
	private int clientId;
	private String clientName;
	private Double totalNet;
	private Double ppnPercentage;
	private int isGross;
	private Double totalGross;
	private String notes;
	private String createdBy;
	private String createdDate;
	private String changedBy;
	private String changedDate;
	private Double totalPpn;
	private String detailSize;
	private String settlementInvoiceId;
	private String paidDate;
	private List<InvoiceDetailBean> detailList;

	public String getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}

	public int getTransactionInvoiceHeaderId() {
		return transactionInvoiceHeaderId;
	}

	public void setTransactionInvoiceHeaderId(int transactionInvoiceHeaderId) {
		this.transactionInvoiceHeaderId = transactionInvoiceHeaderId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public int getPeriodMonth() {
		return periodMonth;
	}

	public void setPeriodMonth(int periodMonth) {
		this.periodMonth = periodMonth;
	}

	public int getPeriodYear() {
		return periodYear;
	}

	public String getPeriodMonthName() {
		return periodMonthName;
	}

	public void setPeriodMonthName(String periodMonthName) {
		this.periodMonthName = periodMonthName;
	}

	public void setPeriodYear(int periodYear) {
		this.periodYear = periodYear;
	}

	public int getStatusInvoiceId() {
		return statusInvoiceId;
	}

	public void setStatusInvoiceId(int statusInvoiceId) {
		this.statusInvoiceId = statusInvoiceId;
	}

	public String getStatusInvoiceName() {
		return statusInvoiceName;
	}

	public void setStatusInvoiceName(String statusInvoiceName) {
		this.statusInvoiceName = statusInvoiceName;
	}

	public int getInvoiceTypeId() {
		return invoiceTypeId;
	}

	public void setInvoiceTypeId(int invoiceTypeId) {
		this.invoiceTypeId = invoiceTypeId;
	}

	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}

	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Double getTotalNet() {
		return totalNet;
	}

	public void setTotalNet(Double totalNet) {
		this.totalNet = totalNet;
	}

	public Double getPpnPercentage() {
		return ppnPercentage;
	}

	public void setPpnPercentage(Double ppnPercentage) {
		this.ppnPercentage = ppnPercentage;
	}

	public int getIsGross() {
		return isGross;
	}

	public void setIsGross(int isGross) {
		this.isGross = isGross;
	}

	public Double getTotalGross() {
		return totalGross;
	}

	public void setTotalGross(Double totalGross) {
		this.totalGross = totalGross;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public String getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(String changedDate) {
		this.changedDate = changedDate;
	}

	public List<InvoiceDetailBean> getDetailList() {
		if (this.detailList == null) {
			detailList = new ArrayList();
		}
		return detailList;
	}

	public void setDetailList(List<InvoiceDetailBean> detailList) {
		this.detailList = detailList;
	}

	public Double getTotalPpn() {
		return totalPpn;
	}

	public void setTotalPpn(Double totalPpn) {
		this.totalPpn = totalPpn;
	}

	public String getDetailSize() {
		return detailSize;
	}

	public void setDetailSize(String detailSize) {
		this.detailSize = detailSize;
	}

	public String getSettlementInvoiceId() {
		return settlementInvoiceId;
	}

	public void setSettlementInvoiceId(String settlementInvoiceId) {
		this.settlementInvoiceId = settlementInvoiceId;
	}

}
