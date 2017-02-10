package training;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class TrainingForm extends ActionForm{
	private String task;
	private List messageList;
	
	//additional training
	private String clientId;
	private int transactionTrainingHeaderId;
	private double fee;
	private String description;
	private int transactionTrainingDetailId;
	
	private List clientList;
	private List trainingHeaderList;
	private List trainingDetailList;
	
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public List getMessageList() {
		if(messageList == null){
			messageList = new ArrayList();
		}
		return messageList;
	}
	public void setMessageList(List messageList) {
		this.messageList = messageList;
	}	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public int getTransactionTrainingHeaderId() {
		return transactionTrainingHeaderId;
	}
	public void setTransactionTrainingHeaderId(
			int transactionTrainingHeaderId) {
		this.transactionTrainingHeaderId = transactionTrainingHeaderId;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getTransactionTrainingDetailId() {
		return transactionTrainingDetailId;
	}
	public void setTransactionTrainingDetailId(
			int transactionTrainingDetailId) {
		this.transactionTrainingDetailId = transactionTrainingDetailId;
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
	public List getTrainingDetailList() {
		return trainingDetailList;
	}
	public void setTrainingDetailList(List trainingDetailList) {
		this.trainingDetailList = trainingDetailList;
	}	
	
}
