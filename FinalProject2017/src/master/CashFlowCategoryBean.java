package master;

import java.io.Serializable;

public class CashFlowCategoryBean implements Serializable {
	private String cashFlowCategoryId;
	private String name;
	private int isDebit;
	private int isEnabled;
	private String createdBy;
	private String createDate;
	private String changedBy;
	private String changedDate;
	public String getCashFlowCategoryId() {
		return cashFlowCategoryId;
	}
	public void setCashFlowCategoryId(String cashFlowCategoryId) {
		this.cashFlowCategoryId = cashFlowCategoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getIsDebit() {
		return isDebit;
	}
	public void setIsDebit(int isDebit) {
		this.isDebit = isDebit;
	}
	public int getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(int isEnabled) {
		this.isEnabled = isEnabled;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
	
}
