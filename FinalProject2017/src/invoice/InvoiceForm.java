package invoice;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class InvoiceForm extends ActionForm{
	private String task;
	private String message;
	private String clientId;
	private String monthFrom;
	private String yearFrom;
	private String monthTo;
	private String yearTo;
	private List clientList;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public List getClientList() {
		return clientList;
	}
	public void setClientList(List clientList) {
		this.clientList = clientList;
	}
	
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public String getMonthFrom() {
		return monthFrom;
	}
	public void setMonthFrom(String monthFrom) {
		this.monthFrom = monthFrom;
	}
	public String getYearFrom() {
		return yearFrom;
	}
	public void setYearFrom(String yearFrom) {
		this.yearFrom = yearFrom;
	}
	public String getMonthTo() {
		return monthTo;
	}
	public void setMonthTo(String monthTo) {
		this.monthTo = monthTo;
	}
	public String getYearTo() {
		return yearTo;
	}
	public void setYearTo(String yearTo) {
		this.yearTo = yearTo;
	}
}
