package index;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class IndexForm  extends ActionForm{
	private String taskIndex;
	private List listedRemainderList;
	private List listedTrainingRemainderList;
	private List createdRemainderList;
	private List sentOutsourceRemainderList;

	public List getListedRemainderList() {
		return listedRemainderList;
	}

	public void setListedRemainderList(List listedRemainderList) {
		this.listedRemainderList = listedRemainderList;
	}

	public List getCreatedRemainderList() {
		return createdRemainderList;
	}

	public void setCreatedRemainderList(List createdRemainderList) {
		this.createdRemainderList = createdRemainderList;
	}

	public String getTaskIndex() {
		return taskIndex;
	}

	public void setTaskIndex(String taskIndex) {
		this.taskIndex = taskIndex;
	}

	public List getSentOutsourceRemainderList() {
		return sentOutsourceRemainderList;
	}

	public void setSentOutsourceRemainderList(List sentOutsourceRemainderList) {
		this.sentOutsourceRemainderList = sentOutsourceRemainderList;
	}

	public List getListedTrainingRemainderList() {
		return listedTrainingRemainderList;
	}

	public void setListedTrainingRemainderList(
			List listedTrainingRemainderList) {
		this.listedTrainingRemainderList = listedTrainingRemainderList;
	}
}
