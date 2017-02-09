package employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmployeeHandler extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeForm employeeForm = (EmployeeForm) form;
		EmployeeManager employeeManager = new EmployeeManager();
		
		if ("formEmployee".equals(employeeForm.getTask())) {
			return mapping.findForward("formEmployee");
		} else {
			employeeForm.setEmployeeList(employeeManager.getAll());
			return mapping.findForward("employee");
		}
	}
}
