package client;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class ClientForm extends ActionForm {
	private String task;
	
	private int clientId;
	private String name;
	private String address;
	private String city;
	private String phoneNumber;
	private String faxNumber;
	private String postalCode;
	private String contactPerson;

	private int isEnabled;

	private List listClient;
	private List messageList;

	public String getTask() {
		return task;
	}

	public int getClientId() {
		return clientId;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public List getListClient() {
		return listClient;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setListClient(List listClient) {
		this.listClient = listClient;
	}

	public int getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(int isEnabled) {
		this.isEnabled = isEnabled;
	}

	public List getMessageList() {
		if (messageList == null)
			messageList = new ArrayList();
		return messageList;
	}

	public void setMessageList(List messageList) {
		this.messageList = messageList;
	}

}
