package holiday;

import org.apache.struts.action.ActionForm;

public class HolidayForm extends ActionForm{
	private String task;
	private String holidayCsv;
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getHolidayCsv() {
		return holidayCsv;
	}
	public void setHolidayCsv(String holidayCsv) {
		this.holidayCsv = holidayCsv;
	}
	
	
}
