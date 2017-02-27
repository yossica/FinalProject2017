package employee;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class EmployeeForm extends ActionForm {
	private String task;

	private int employeeId;
	private String name;
	private String email;
	private int isEnabled;

	private List employeeList;
	private List messageList;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(int isEnabled) {
		this.isEnabled = isEnabled;
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
}
