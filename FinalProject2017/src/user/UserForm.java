package user;

import org.apache.struts.action.ActionForm;

public class UserForm extends ActionForm {

	private String taskLogin;
	private String userName;
	private String password;	
  
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
	public String getTaskLogin() {
		return taskLogin;
	}
	public void setTaskLogin(String taskLogin) {
		this.taskLogin = taskLogin;
	}	
}
