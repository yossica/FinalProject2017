package outsource;

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

		if ("create".equals(outsourceForm.getTask())) {
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			outsourceForm.setOptEmployeeList(employeeManager.getAllEnabled());

			outsourceForm.setTask("save" + outsourceForm.getTask());
			return mapping.findForward("formOutsource");
		} else if ("update".equals(outsourceForm.getTask())) {

			OutsourceBean outsourceBean = outsourceManager
					.getById(outsourceForm.getOutsourceBean().getTransactionOutsourceId());
			outsourceForm.setOutsourceBean(outsourceBean);

			outsourceForm.setTask("save" + outsourceForm.getTask());
			return mapping.findForward("formOutsource");
		} else if ("mutation".equals(outsourceForm.getTask())) {
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			outsourceForm.setOptEmployeeList(employeeManager.getAllEnabled());

			OutsourceBean outsourceBean = outsourceManager
					.getById(outsourceForm.getOutsourceBean().getTransactionOutsourceId());

			outsourceForm.setOutsourceBean(outsourceBean);

			outsourceForm.setTask("save" + outsourceForm.getTask());
			return mapping.findForward("formOutsource");
		} else if ("end".equals(outsourceForm.getTask())) {

			OutsourceBean outsourceBean = outsourceManager
					.getById(outsourceForm.getOutsourceBean().getTransactionOutsourceId());
			
			outsourceForm.setOutsourceBean(outsourceBean);

			return mapping.findForward("formOutsource");
		} else if ("savecreate".equals(outsourceForm.getTask())) {
			outsourceForm.getOutsourceBean().setCreatedBy((String) session
					.getAttribute("username"));

			outsourceManager.insert(outsourceForm.getOutsourceBean());
			
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			Calendar cal = Calendar.getInstance();
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
			outsourceForm.getOutsourceBean().setChangedBy((String) session
					.getAttribute("username"));

			outsourceManager.update(outsourceForm.getOutsourceBean());
			
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			Calendar cal = Calendar.getInstance();
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
			
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			outsourceForm.setFilterMonth("");
			outsourceForm.setFilterYear(String.valueOf(year));

			Map filter = new HashMap();
			filter.put("date", null);
			filter.put("year", outsourceForm.getFilterYear());

			outsourceForm.setOutsourceList(outsourceManager
					.getAllWithFilter(filter));

			//save mutation belom
			return mapping.findForward("outsource");
		} else {
			outsourceForm.setOptClientList(clientManager.getAllEnabled());

			Map filter = new HashMap();
			
			if("filter".equals(outsourceForm.getTask())){
			}else{
				//jika baru pertama kali buka
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				outsourceForm.setFilterMonth("");
				outsourceForm.setFilterYear(String.valueOf(year));
			}
			//jika klik tombol filter
			filter.put("client", outsourceForm.getFilterClient());

			if(outsourceForm.getFilterYear().isEmpty()){
				filter.put("date", null);
				filter.put("year", null);
			}else{
				if(outsourceForm.getFilterMonth().isEmpty()){
					filter.put("date", null);
					filter.put("year", outsourceForm.getFilterYear());
				}else{
					filter.put("date", outsourceForm.getFilterMonth()+"/01/"+outsourceForm.getFilterYear());
					filter.put("year", null);
				}
			}
					
			outsourceForm.setOutsourceList(outsourceManager
					.getAllWithFilter(filter));

			return mapping.findForward("outsource");
		}
	}

}
