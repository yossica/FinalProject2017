package outsource;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import client.ClientBean;

public class OutsourceForm extends ActionForm{
	private int transactionOutsourceId;
	private int clientId;
	private String clientName;
	private int employeeId;
	private String employeeName;
	
	private String startDate;
	private String endDate;
	private int isGross;
	private double fee;
	
	private String task;
	private List messageList;
	
	private List<ClientBean> clientList;
	
	public List<ClientBean> getClientList() {
		return clientList;
	}

	public void setClientList(List<ClientBean> clientList) {
		this.clientList = clientList;
	}

	public List getMessageList() {
		if (this.messageList == null) {
			this.messageList = new ArrayList();
		}
		return messageList;
	}

	public void setMessageList(List messageList) {
		this.messageList = messageList;
	}

	public int getTransactionOutsourceId() {
		return transactionOutsourceId;
	}

	public void setTransactionOutsourceId(int transactionOutsourceId) {
		this.transactionOutsourceId = transactionOutsourceId;
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

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getIsGross() {
		return isGross;
	}

	public void setIsGross(int isGross) {
		this.isGross = isGross;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}
	
	
	

}
