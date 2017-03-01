package user;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class UserForm extends ActionForm {

	private String task;
	private List messageList;
	private String userName;
	private String password;
	private String createdDate;

	private String newPassword;
	private String confirmPassword;

	private String newUser;
	private List userList;

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

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	public String getNewUser() {
		return newUser;
	}

	public void setNewUser(String newUser) {
		this.newUser = newUser;
	}

	public List getUserList() {
		return userList;
	}

	public void setUserList(List userList) {
		this.userList = userList;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
