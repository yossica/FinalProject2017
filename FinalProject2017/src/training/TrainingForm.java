package training;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class TrainingForm extends ActionForm {
	private String task;
	private List messageList;

	// additional training
	private int clientId;
	private int transactionTrainingDetailId;
	private TrainingDetailBean trainingDetailBean = new TrainingDetailBean();

	private List trainingDetailList;
	private List clientList;
	private List trainingHeaderList;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public List getMessageList() {
		if (messageList == null) {
			messageList = new ArrayList();
		}
		return messageList;
	}

	public void setMessageList(List messageList) {
		this.messageList = messageList;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getTransactionTrainingDetailId() {
		return transactionTrainingDetailId;
	}

	public void setTransactionTrainingDetailId(int transactionTrainingDetailId) {
		this.transactionTrainingDetailId = transactionTrainingDetailId;
	}

	public TrainingDetailBean getTrainingDetailBean() {
		return trainingDetailBean;
	}

	public void setTrainingDetailBean(TrainingDetailBean trainingDetailBean) {
		this.trainingDetailBean = trainingDetailBean;
	}

	public List getTrainingDetailList() {
		return trainingDetailList;
	}

	public void setTrainingDetailList(List trainingDetailList) {
		this.trainingDetailList = trainingDetailList;
	}

	public List getClientList() {
		return clientList;
	}

	public void setClientList(List clientList) {
		this.clientList = clientList;
	}

	public List getTrainingHeaderList() {
		return trainingHeaderList;
	}

	public void setTrainingHeaderList(List trainingHeaderList) {
		this.trainingHeaderList = trainingHeaderList;
	}

}
