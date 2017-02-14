package outsource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

import client.ClientBean;
import employee.EmployeeBean;

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

	private String filterClient;
	private String filterMonth;
	private String filterYear;
	
	private List<ClientBean> optClientList;
	private List<LabelValueBean> optYear;
	private List<EmployeeBean> optEmployeeList;
	
	private List<OutsourceBean> outsourceList;
		
	
	public List<EmployeeBean> getOptEmployeeList() {
		return optEmployeeList;
	}

	public void setOptEmployeeList(List<EmployeeBean> optEmployeeList) {
		this.optEmployeeList = optEmployeeList;
	}

	public List<OutsourceBean> getOutsourceList() {
		return outsourceList;
	}

	public void setOutsourceList(List<OutsourceBean> outsourceList) {
		this.outsourceList = outsourceList;
	}

	public List<LabelValueBean> getOptYear() {
		if (this.optYear == null) {
			this.optYear = new ArrayList();
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = 2000; i <= year; i++) {		
			LabelValueBean temp = new LabelValueBean();
			temp.setLabel(String.valueOf(i));
			temp.setValue(String.valueOf(i));
			optYear.add(temp);
		}
		
		return optYear;
	}

	public void setOptYear(List<LabelValueBean> optYear) {
		this.optYear = optYear;
	}

	public String getFilterClient() {
		return filterClient;
	}

	public void setFilterClient(String filterClient) {
		this.filterClient = filterClient;
	}

	public String getFilterMonth() {
		return filterMonth;
	}

	public void setFilterMonth(String filterMonth) {
		this.filterMonth = filterMonth;
	}

	public String getFilterYear() {
		return filterYear;
	}

	public void setFilterYear(String filterYear) {
		this.filterYear = filterYear;
	}

	public List<ClientBean> getOptClientList() {
		return optClientList;
	}

	public void setOptClientList(List<ClientBean> optClientList) {
		this.optClientList = optClientList;
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
