package holiday;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class HolidayForm extends ActionForm{
	private String task;
	private String holidayCsv;
	
	private int holidayId;
	private List holidayList;
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
	public int getHolidayId() {
		return holidayId;
	}
	public void setHolidayId(int holidayId) {
		this.holidayId = holidayId;
	}
	public List getHolidayList() {
		return holidayList;
	}
	public void setHolidayList(List holidayList) {
		this.holidayList = holidayList;
	}	
	
}
