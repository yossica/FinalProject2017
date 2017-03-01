package client;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import training.TrainingBean;

public class ClientForm extends ActionForm {
	private String task;
	private ClientBean clientBean = new ClientBean();
	private List listClient;
	private List messageList;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}
	
	public List getListClient() {
		return listClient;
	}

	public void setListClient(List listClient) {
		this.listClient = listClient;
	}

	public List getMessageList() {
		if (messageList == null)
			messageList = new ArrayList();
		return messageList;
	}

	public void setMessageList(List messageList) {
		this.messageList = messageList;
	}

	public ClientBean getClientBean() {
		return clientBean;
	}

	public void setClientBean(ClientBean clientBean) {
		this.clientBean = clientBean;
	}

}
