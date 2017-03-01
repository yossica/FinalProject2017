package employee;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class EmployeeForm extends ActionForm {
	private String task;
	private EmployeeBean employeeBean = new EmployeeBean();
	private List employeeList;
	private List messageList;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public List getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List employeeList) {
		this.employeeList = employeeList;
	}

	public List getMessageList() {
		return messageList;
	}

	public void setMessageList(List messageList) {
		this.messageList = messageList;
	}

	public EmployeeBean getEmployeeBean() {
		return employeeBean;
	}

	public void setEmployeeBean(EmployeeBean employeeBean) {
		this.employeeBean = employeeBean;
	}
}
