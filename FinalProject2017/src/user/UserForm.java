package user;

import org.apache.struts.action.ActionForm;

public class UserForm extends ActionForm {

	private String task;
	private String userName;
	private String password;	
  
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	

}
