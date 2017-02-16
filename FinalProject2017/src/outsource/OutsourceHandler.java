package outsource;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import client.ClientManager;
import employee.EmployeeManager;

public class OutsourceHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		OutsourceForm outsourceForm = (OutsourceForm) form;
		OutsourceManager outsourceManager = new OutsourceManager();
		ClientManager clientManager = new ClientManager();
		EmployeeManager employeeManager = new EmployeeManager();
		HttpSession session = request.getSession();

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		if ("create".equals(outsourceForm.getTask())) {
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			outsourceForm.setOptEmployeeList(employeeManager.getAllEnabled());

			outsourceForm.setTask("save" + outsourceForm.getTask());
			return mapping.findForward("formOutsource");
		} else if ("update".equals(outsourceForm.getTask())) {

			outsourceForm.setOutsourceBean(outsourceManager
					.getById(outsourceForm.getTransactionOutsourceId()));

			outsourceForm.setTask("save" + outsourceForm.getTask());
			return mapping.findForward("formOutsource");
		} else if ("mutation".equals(outsourceForm.getTask())) {
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			outsourceForm.setOptEmployeeList(employeeManager.getAllEnabled());

			OutsourceBean outsourceBean = outsourceManager
					.getById(outsourceForm.getTransactionOutsourceId());

			outsourceForm.setOutsourceBean(outsourceBean);

			// date format
			cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getStartDate()));
			outsourceForm.getOutsourceBean().setStartDate(
					showDateFormat.format(cal.getTime()));
			cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getEndDate()));
			outsourceForm.getOutsourceBean().setEndDate(
					showDateFormat.format(cal.getTime()));

			outsourceForm.setTask("save" + outsourceForm.getTask());
			return mapping.findForward("formOutsource");
		} else if ("end".equals(outsourceForm.getTask())) {

			OutsourceBean outsourceBean = outsourceManager
					.getById(outsourceForm.getTransactionOutsourceId());

			outsourceForm.setOutsourceBean(outsourceBean);

			// date format
			cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getEndDate()));
			outsourceForm.getOutsourceBean().setEndDate(
					showDateFormat.format(cal.getTime()));

			return mapping.findForward("formOutsource");
		} else if ("savecreate".equals(outsourceForm.getTask())) {
			outsourceForm.getOutsourceBean().setCreatedBy(
					(String) session.getAttribute("username"));

			// date format
			cal.setTime(showDateFormat.parse(outsourceForm.getOutsourceBean()
					.getStartDate()));
			outsourceForm.getOutsourceBean().setStartDate(
					dateFormat.format(cal.getTime()));
			cal.setTime(showDateFormat.parse(outsourceForm.getOutsourceBean()
					.getEndDate()));
			outsourceForm.getOutsourceBean().setEndDate(
					dateFormat.format(cal.getTime()));

			outsourceManager.insert(outsourceForm.getOutsourceBean());
			outsourceForm.getMessageList().add(
					"Success Create Profesional Service Contract");

			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			int year = cal.get(Calendar.YEAR);
			outsourceForm.setFilterMonth("");
			outsourceForm.setFilterYear(String.valueOf(year));

			Map filter = new HashMap();
			filter.put("date", null);
			filter.put("year", outsourceForm.getFilterYear());

			outsourceForm.setOutsourceList(outsourceManager
					.getAllWithFilter(filter));

			return mapping.findForward("outsource");
		} else if ("saveupdate".equals(outsourceForm.getTask())) {
			outsourceForm.getOutsourceBean().setChangedBy(
					(String) session.getAttribute("username"));

			outsourceManager.update(outsourceForm.getOutsourceBean());
			outsourceForm.getMessageList().add(
					"Success Update Profesional Service Contract");

			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			int year = cal.get(Calendar.YEAR);
			outsourceForm.setFilterMonth("");
			outsourceForm.setFilterYear(String.valueOf(year));

			Map filter = new HashMap();
			filter.put("date", null);
			filter.put("year", outsourceForm.getFilterYear());

			outsourceForm.setOutsourceList(outsourceManager
					.getAllWithFilter(filter));

			return mapping.findForward("outsource");
		} else if ("savemutation".equals(outsourceForm.getTask())) {

			// date format
			cal.setTime(showDateFormat.parse(outsourceForm.getOutsourceBean()
					.getStartDate()));
			outsourceForm.getOutsourceBean().setStartDate(
					dateFormat.format(cal.getTime()));
			cal.setTime(showDateFormat.parse(outsourceForm.getOutsourceBean()
					.getEndDate()));
			outsourceForm.getOutsourceBean().setEndDate(
					dateFormat.format(cal.getTime()));
			OutsourceBean outsourceBean = outsourceManager
					.getById(outsourceForm.getOutsourceBean()
							.getTransactionOutsourceId());
			Calendar cal2 = Calendar.getInstance();
			//validation
			//input start date > old start date
			//input start date < old end date
			//input end date >= old date
			 cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean().getStartDate()));
			 cal2.setTime(dateFormat.parse(outsourceBean.getStartDate()));
			 
			if(cal.after(cal2) ){
				 cal2.setTime(dateFormat.parse(outsourceBean.getEndDate()));
				if(cal.before(cal2)){
					 cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean().getEndDate()));
					if(cal.compareTo(cal2)>=0){
						
						
					}else{
						outsourceForm.getMessageList().add("End date must be after or equal than previous end date");
					}
				}else{
					outsourceForm.getMessageList().add("Start date must be before than previous end date");
				}
			}else{
				outsourceForm.getMessageList().add("Start date must be later than previous start date");
			}
			

			// for view data
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			int year = cal.get(Calendar.YEAR);
			outsourceForm.setFilterMonth("");
			outsourceForm.setFilterYear(String.valueOf(year));

			Map filter = new HashMap();
			filter.put("date", null);
			filter.put("year", outsourceForm.getFilterYear());

			outsourceForm.setOutsourceList(outsourceManager
					.getAllWithFilter(filter));

			// save mutation belom
			return mapping.findForward("outsource");
		} else {
			outsourceForm.setOptClientList(clientManager.getAllEnabled());

			Map filter = new HashMap();

			if ("filter".equals(outsourceForm.getTask())) {
			} else {
				// jika baru pertama kali buka
				int year = cal.get(Calendar.YEAR);
				outsourceForm.setFilterMonth("");
				outsourceForm.setFilterYear(String.valueOf(year));
			}
			// jika klik tombol filter
			filter.put("client", outsourceForm.getFilterClient());

			if (outsourceForm.getFilterYear().isEmpty()) {
				filter.put("date", null);
				filter.put("year", null);
			} else {
				if (outsourceForm.getFilterMonth().isEmpty()) {
					filter.put("date", null);
					filter.put("year", outsourceForm.getFilterYear());
				} else {
					filter.put("date", outsourceForm.getFilterMonth() + "/01/"
							+ outsourceForm.getFilterYear());
					filter.put("year", null);
				}
			}

			outsourceForm.setOutsourceList(outsourceManager
					.getAllWithFilter(filter));

			return mapping.findForward("outsource");
		}
	}
}
