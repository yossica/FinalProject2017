package index;

import org.apache.struts.action.ActionForm;

public class IndexForm  extends ActionForm{
	private String taskIndex;

	public String getTaskIndex() {
		return taskIndex;
	}

	public void setTaskIndex(String taskIndex) {
		this.taskIndex = taskIndex;
	}
}
