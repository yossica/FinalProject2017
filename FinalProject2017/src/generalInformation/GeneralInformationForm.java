package generalInformation;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class GeneralInformationForm extends ActionForm{
	private String task;
	private String key;
	private String value;
	private String dataType;
	private Integer length;
	private String createdBy;
	private String createDate;
	private String changedBy;
	private String changedDate;
	
	private List listGeneralInformation;
	
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
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
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public List getListGeneralInformation() {
		return listGeneralInformation;
	}
	public void setListGeneralInformation(List listGeneralInformation) {
		this.listGeneralInformation = listGeneralInformation;
	}
	
}
