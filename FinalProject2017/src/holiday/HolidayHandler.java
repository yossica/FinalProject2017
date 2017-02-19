package holiday;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HolidayHandler extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("username") == null) {
			return mapping.findForward("login");
		}
		HolidayForm holidayForm = (HolidayForm) form;
		HolidayManager holidayManager = new HolidayManager();
		
		if("insert".equals(holidayForm.getTask())){
			String[] row = holidayForm.getHolidayCsv().split("\n");
			for(int i = 0 ; i < row.length ; i++){
				String[] column = row[i].split(",");
				HolidayBean holidayBean = new HolidayBean();
				holidayBean.setCreatedBy((String)session.getAttribute("username"));
				holidayBean.setHolidayDate(column[0]);
				holidayBean.setName(column[1].trim());
				holidayManager.insert(holidayBean);
			}
			holidayForm.setHolidayList(holidayManager.getAll());
			holidayForm.setHolidayCsv("");
			holidayForm.getMessageList().add("All Data successfully inserted!");
			return mapping.findForward("holiday");
		}
		else if("delete".equals(holidayForm.getTask())){
			holidayManager.delete(holidayForm.getHolidayId());

			holidayForm.setHolidayList(holidayManager.getAll());
			holidayForm.setHolidayCsv("");
			holidayForm.getMessageList().add("Data successfully deleted!");
			return mapping.findForward("holiday");
		}
		else{
			holidayForm.setHolidayList(holidayManager.getAll());
			return mapping.findForward("holiday");
		}
	}

}
