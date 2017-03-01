package employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmployeeHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();

		EmployeeForm employeeForm = (EmployeeForm) form;
		EmployeeManager employeeManager = new EmployeeManager();

		if ("create".equals(employeeForm.getTask())) {
			employeeForm.setTask("save" + employeeForm.getTask());
			return mapping.findForward("formEmployee");
		} else if ("savecreate".equals(employeeForm.getTask())) {
			EmployeeBean employeeBean = new EmployeeBean();
			employeeBean
					.setCreatedBy((String) session.getAttribute("username"));
			employeeManager.insert(employeeBean);
			employeeForm.setEmployeeList(employeeManager.getAll());
			return mapping.findForward("employee");
		} else if ("update".equals(employeeForm.getTask())) {
			employeeForm.setTask("save" + employeeForm.getTask());
			EmployeeBean employeeBean = employeeManager.getById(employeeForm
					.getEmployeeBean().getEmployeeId());
			employeeForm.setEmployeeBean(employeeBean);
			return mapping.findForward("formEmployee");
		} else if ("saveupdate".equals(employeeForm.getTask())) {
			employeeForm.getEmployeeBean().setChangedBy(
					(String) session.getAttribute("username"));
			employeeManager.update(employeeForm.getEmployeeBean());
			employeeForm.setEmployeeList(employeeManager.getAll());
			return mapping.findForward("employee");
		} else {
			employeeForm.setEmployeeList(employeeManager.getAll());
			return mapping.findForward("employee");
		}
	}
}
