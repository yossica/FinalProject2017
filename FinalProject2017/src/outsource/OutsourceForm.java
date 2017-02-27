package outsource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

import client.ClientBean;
import employee.EmployeeBean;

public class OutsourceForm extends ActionForm {
	private int transactionOutsourceId;
	private OutsourceBean outsourceBean = new OutsourceBean();
	private String task;
	private List messageList;

	private String filterClient;
	private String filterEmployee;
	private String filterMonth;
	private String filterYear;

	private List<ClientBean> optClientList;
	private List<LabelValueBean> optYear;
	private List<EmployeeBean> optEmployeeList;

	private List<OutsourceBean> outsourceList;

	public String getFilterEmployee() {
		return filterEmployee;
	}

	public void setFilterEmployee(String filterEmployee) {
		this.filterEmployee = filterEmployee;
	}

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

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public OutsourceBean getOutsourceBean() {
		return outsourceBean;
	}

	public void setOutsourceBean(OutsourceBean outsourceBean) {
		this.outsourceBean = outsourceBean;
	}

	public int getTransactionOutsourceId() {
		return transactionOutsourceId;
	}

	public void setTransactionOutsourceId(int transactionOutsourceId) {
		this.transactionOutsourceId = transactionOutsourceId;
	}

}
