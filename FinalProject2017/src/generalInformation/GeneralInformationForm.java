package generalInformation;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class GeneralInformationForm extends ActionForm {
	private String task;
	private GeneralInformationBean generalInformationBean = new GeneralInformationBean();
	private List listGeneralInformation;
	private List messageList;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public List getListGeneralInformation() {
		return listGeneralInformation;
	}

	public void setListGeneralInformation(List listGeneralInformation) {
		this.listGeneralInformation = listGeneralInformation;
	}

	public List getMessageList() {
		return messageList;
	}

	public void setMessageList(List messageList) {
		this.messageList = messageList;
	}

	public GeneralInformationBean getGeneralInformationBean() {
		return generalInformationBean;
	}

	public void setGeneralInformationBean(
			GeneralInformationBean generalInformationBean) {
		this.generalInformationBean = generalInformationBean;
	}
}
