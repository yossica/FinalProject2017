package outsource;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import utils.Filter;
import client.ClientManager;
import employee.EmployeeManager;

public class OutsourceHandler extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		OutsourceForm outsourceForm = (OutsourceForm) form;
		OutsourceManager outsourceManager = new OutsourceManager();
		ClientManager clientManager = new ClientManager();
		EmployeeManager employeeManager = new EmployeeManager();
		HttpSession session = request.getSession();
		
		if("create".equals(outsourceForm.getTask())){
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			outsourceForm.setOptEmployeeList(employeeManager.getAllEnabled());
			
			outsourceForm.setTask("save" + outsourceForm.getTask());
			return mapping.findForward("formOutsource");
		}else if("update".equals(outsourceForm.getTask())){
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			outsourceForm.setOptEmployeeList(employeeManager.getAllEnabled());
			
			OutsourceBean outsourceBean = outsourceManager.getById(outsourceForm.getTransactionOutsourceId());
			outsourceForm.setClientId(outsourceBean.getClientId());
			outsourceForm.setClientName(outsourceBean.getClientName());
			outsourceForm.setEmployeeId(outsourceBean.getEmployeeId());
			outsourceForm.setEmployeeName(outsourceBean.getEmployeeName());
			outsourceForm.setStartDate(outsourceBean.getStartDate());
			outsourceForm.setEndDate(outsourceBean.getEndDate());
			outsourceForm.setIsGross(outsourceBean.getIsGross());
			outsourceForm.setFee(outsourceBean.getFee());
			
			outsourceForm.setTask("save" + outsourceForm.getTask());
			return mapping.findForward("formOutsource");
		}else if("mutation".equals(outsourceForm.getTask())){
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			outsourceForm.setOptEmployeeList(employeeManager.getAllEnabled());
			
			OutsourceBean outsourceBean = outsourceManager.getById(outsourceForm.getTransactionOutsourceId());
			outsourceForm.setClientId(outsourceBean.getClientId());
			outsourceForm.setClientName(outsourceBean.getClientName());
			outsourceForm.setEmployeeId(outsourceBean.getEmployeeId());
			outsourceForm.setEmployeeName(outsourceBean.getEmployeeName());
			outsourceForm.setStartDate(outsourceBean.getStartDate());
			outsourceForm.setEndDate(outsourceBean.getEndDate());
			outsourceForm.setIsGross(outsourceBean.getIsGross());
			outsourceForm.setFee(outsourceBean.getFee());
			
			outsourceForm.setTask("save" + outsourceForm.getTask());
			return mapping.findForward("formOutsource");
		}else if("end".equals(outsourceForm.getTask())){
			
			return mapping.findForward("formOutsource");
		}else if("savecreate".equals(outsourceForm.getTask())){
			OutsourceBean outsourceBean = new OutsourceBean();
			outsourceBean.setClientId(outsourceForm.getClientId());
			outsourceBean.setEmployeeId(outsourceForm.getEmployeeId());
			outsourceBean.setStartDate(outsourceForm.getStartDate());
			outsourceBean.setEndDate(outsourceForm.getEndDate());
			outsourceBean.setIsGross(outsourceForm.getIsGross());
			outsourceBean.setFee(outsourceForm.getFee());
			outsourceBean.setCreatedBy((String) session.getAttribute("username"));
			
			outsourceManager.insert(outsourceBean);
			
			return mapping.findForward("outsource");
		}else if("saveupdate".equals(outsourceForm.getTask())){
			OutsourceBean outsourceBean = new OutsourceBean();
			outsourceBean.setTransactionOutsourceId(outsourceForm.getTransactionOutsourceId());
			outsourceBean.setEndDate(outsourceForm.getEndDate());
			outsourceBean.setIsGross(outsourceForm.getIsGross());
			outsourceBean.setFee(outsourceForm.getFee());
			outsourceBean.setChangedBy((String) session.getAttribute("username"));

			outsourceManager.update(outsourceBean);
			
			return mapping.findForward("outsource");
		}else if("savemutation".equals(outsourceForm.getTask())){
			return mapping.findForward("outsource");
		}else{
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			
			Map filter = new HashMap();
			filter.put("client", outsourceForm.getFilterClient());
			filter.put("month", outsourceForm.getFilterMonth());
			filter.put("year", outsourceForm.getFilterYear());
			
			outsourceForm.setOutsourceList(outsourceManager.getAllWithFilter(filter));
	
			return mapping.findForward("outsource");
		}
	}

}
