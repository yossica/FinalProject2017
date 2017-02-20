package training;

import java.io.Serializable;

public class TrainingDetailBean implements Serializable {
	private int transactionTrainingDetailId;
	private int transactionTrainingHeaderId;
	private String description;
	private double fee;
	private String note;
	private int isSettlement;
	private String createdBy;
	private String createdDate;
	private String changedBy;
	private String changedDate;
	public int getTransactionTrainingDetailId() {
		return transactionTrainingDetailId;
	}
	public void setTransactionTrainingDetailId(int transactionTrainingDetailId) {
		this.transactionTrainingDetailId = transactionTrainingDetailId;
	}
	public int getTransactionTrainingHeaderId() {
		return transactionTrainingHeaderId;
	}
	public void setTransactionTrainingHeaderId(int transactionTrainingHeaderId) {
		this.transactionTrainingHeaderId = transactionTrainingHeaderId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public int getIsSettlement() {
		return isSettlement;
	}
	public void setIsSettlement(int isSettlement) {
		this.isSettlement = isSettlement;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

}
