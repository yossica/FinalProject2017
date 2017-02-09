package invoice;

import org.apache.struts.action.ActionForm;

public class InvoiceForm extends ActionForm{
	private String task;
	private String message;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
