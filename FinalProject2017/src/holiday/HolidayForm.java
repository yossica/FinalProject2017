package holiday;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

public class HolidayForm extends ActionForm{
	private String task;
	private String holidayCsv;
	private List messageList;
	
	private int holidayId;
	private List holidayList;
	
	private String filterYear;
	private List<LabelValueBean> yearList;
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
	public List getMessageList() {
		if(messageList == null){
			messageList = new ArrayList();
		}
		return messageList;
	}
	public void setMessageList(List messageList) {
		this.messageList = messageList;
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
	public String getFilterYear() {
		return filterYear;
	}
	public void setFilterYear(String filterYear) {
		this.filterYear = filterYear;
	}
	public List<LabelValueBean> getYearList() {
		if (this.yearList == null) {
			this.yearList = new ArrayList();
		}
		return yearList;
	}
	public void setYearList(List<String> yearList) {
		if (this.yearList == null) {
			this.yearList = new ArrayList();
		}
		for(String string : yearList){
			LabelValueBean temp = new LabelValueBean();
			temp.setLabel(string);
			temp.setValue(string);
			this.yearList.add(temp);
		}
	}	
	
}
