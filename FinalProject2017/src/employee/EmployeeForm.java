package employee;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class EmployeeForm extends ActionForm{
	private String task;
	
	private int employeeId;
	private String name;
	private String email;
	
	private List listEmployee;

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

	public List getListEmployee() {
		return listEmployee;
	}

	public void setListEmployee(List listEmployee) {
		this.listEmployee = listEmployee;
	}
	
}
