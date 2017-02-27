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
		if (session.getAttribute("username") == null) {
			return mapping.findForward("login");
		}

		EmployeeForm employeeForm = (EmployeeForm) form;
		EmployeeManager employeeManager = new EmployeeManager();

		if ("create".equals(employeeForm.getTask())) {
			employeeForm.setTask("save" + employeeForm.getTask());
			return mapping.findForward("formEmployee");
		} else if ("savecreate".equals(employeeForm.getTask())) {
			EmployeeBean employeeBean = new EmployeeBean();
			employeeBean.setName(employeeForm.getName());
			employeeBean.setEmail(employeeForm.getEmail());
			employeeBean.setIsEnabled(employeeForm.getIsEnabled());
			employeeBean
					.setCreatedBy((String) session.getAttribute("username"));
			employeeManager.insert(employeeBean);
			employeeForm.setEmployeeList(employeeManager.getAll());
			return mapping.findForward("employee");
		} else if ("update".equals(employeeForm.getTask())) {
			employeeForm.setTask("save" + employeeForm.getTask());
			EmployeeBean employeeBean = employeeManager.getById(employeeForm
					.getEmployeeId());
			employeeForm.setEmployeeId(employeeBean.getEmployeeId());
			employeeForm.setName(employeeBean.getName());
			employeeForm.setEmail(employeeBean.getEmail());
			employeeForm.setIsEnabled(employeeBean.getIsEnabled());
			return mapping.findForward("formEmployee");
		} else if ("saveupdate".equals(employeeForm.getTask())) {
			EmployeeBean employeeBean = new EmployeeBean();
			employeeBean.setEmployeeId(employeeForm.getEmployeeId());
			employeeBean.setName(employeeForm.getName());
			employeeBean.setEmail(employeeForm.getEmail());
			employeeBean.setIsEnabled(employeeForm.getIsEnabled());
			employeeBean
					.setChangedBy((String) session.getAttribute("username"));
			employeeManager.update(employeeBean);
			employeeForm.setEmployeeList(employeeManager.getAll());
			return mapping.findForward("employee");
		} else {
			employeeForm.setEmployeeList(employeeManager.getAll());
			return mapping.findForward("employee");
		}
	}
}
