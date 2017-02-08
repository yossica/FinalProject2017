package user;

import org.apache.struts.action.ActionForm;

public class UserForm extends ActionForm {
	private String task;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}
	
}
