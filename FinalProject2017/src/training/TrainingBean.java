package training;

import java.io.Serializable;
import java.util.List;

public class TrainingBean implements Serializable {
	private int transactionTrainingDetailId;
	private int transactionTrainingHeaderId;
	private int clientId;
	private String clientName;

	private int dpInvoiceId;
	private String trainingStartDate;
	private String trainingEndDate;
	private String description;
	private int isGross;
	private int settlementInvoiceId;
	private int isSettlement;
	private String createdBy;
	private String createdDate;
	private String changedBy;
	private String changedDate;
	private Integer fee;

	private List<TrainingDetailBean> detailList;

	public int getTransactionTrainingHeaderId() {
		return transactionTrainingHeaderId;
	}

	public void setTransactionTrainingHeaderId(int transactionTrainingHeaderId) {
		this.transactionTrainingHeaderId = transactionTrainingHeaderId;
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

	public int getDpInvoiceId() {
		return dpInvoiceId;
	}

	public void setDpInvoiceId(int dpInvoiceId) {
		this.dpInvoiceId = dpInvoiceId;
	}

	public String getTrainingStartDate() {
		return trainingStartDate;
	}

	public void setTrainingStartDate(String trainingStartDate) {
		this.trainingStartDate = trainingStartDate;
	}

	public String getTrainingEndDate() {
		return trainingEndDate;
	}

	public void setTrainingEndDate(String trainingEndDate) {
		this.trainingEndDate = trainingEndDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsGross() {
		return isGross;
	}

	public void setIsGross(int isGross) {
		this.isGross = isGross;
	}

	public int getSettlementInvoiceId() {
		return settlementInvoiceId;
	}

	public void setSettlementInvoiceId(int settlementInvoiceId) {
		this.settlementInvoiceId = settlementInvoiceId;
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

	public List<TrainingDetailBean> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<TrainingDetailBean> detailList) {
		this.detailList = detailList;
	}

	public int getTransactionTrainingDetailId() {
		return transactionTrainingDetailId;
	}

	public void setTransactionTrainingDetailId(int transactionTrainingDetailId) {
		this.transactionTrainingDetailId = transactionTrainingDetailId;
	}

	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	public int getIsSettlement() {
		return isSettlement;
	}

	public void setIsSettlement(int isSettlement) {
		this.isSettlement = isSettlement;
	}

}
