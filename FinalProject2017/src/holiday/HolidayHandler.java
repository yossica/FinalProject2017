package holiday;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HolidayHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();

		HolidayForm holidayForm = (HolidayForm) form;
		HolidayManager holidayManager = new HolidayManager();

		if ("insert".equals(holidayForm.getTask())) {
			String[] row = holidayForm.getHolidayCsv().split("\n");
			for (int i = 0; i < row.length; i++) {
				String[] column = row[i].split(",");
				HolidayBean holidayBean = new HolidayBean();
				holidayBean.setCreatedBy((String) session
						.getAttribute("username"));
				holidayBean.setHolidayDate(column[0]);
				holidayBean.setName(column[1].trim());
				holidayManager.insert(holidayBean);
			}
			holidayForm.setHolidayList(holidayManager
					.getAllWithFilter(new HashMap()));
			holidayForm.setYearList(holidayManager.getDistinctYear());
			holidayForm.setHolidayCsv("");
			holidayForm.getMessageList().add(
					"Success!!! All Data successfully inserted!");
			return mapping.findForward("holiday");
		} else if ("delete".equals(holidayForm.getTask())) {
			holidayManager.delete(holidayForm.getHolidayId());

			Map paramMap = new HashMap();
			paramMap.put("filterYear", holidayForm.getFilterYear());
			holidayForm.setHolidayList(holidayManager
					.getAllWithFilter(paramMap));
			holidayForm.setYearList(holidayManager.getDistinctYear());
			holidayForm.setHolidayCsv("");
			holidayForm.getMessageList().add(
					"Success!!! Data successfully deleted!");
			return mapping.findForward("holiday");
		} else if ("filter".equals(holidayForm.getTask())) {
			Map paramMap = new HashMap();
			paramMap.put("filterYear", holidayForm.getFilterYear());
			holidayForm.setHolidayList(holidayManager
					.getAllWithFilter(paramMap));
			holidayForm.setYearList(holidayManager.getDistinctYear());
			return mapping.findForward("holiday");
		} else {
			holidayForm.setHolidayList(holidayManager
					.getAllWithFilter(new HashMap()));
			holidayForm.setYearList(holidayManager.getDistinctYear());
			return mapping.findForward("holiday");
		}
	}

}
