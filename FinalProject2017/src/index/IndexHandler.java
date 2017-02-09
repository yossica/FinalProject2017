package index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class IndexHandler extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IndexForm indexForm = (IndexForm) form;
		
		if("invoice".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("invoice");
		}
		else if("pettyCash".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("pettyCash");
		}
		else if("cashInBank".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("cashInBank");
		}
		else if("financeSummary".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("financeSummary");
		}
		else if("changePassword".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("changePassword");
		}
		else if("employee".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("employee");
		}
<<<<<<< HEAD
		else if("generalInformation".equals(indexForm.getTask()))
		{
			return mapping.findForward("generalInformation");
		}
		else if("logout".equals(indexForm.getTask()))
=======
		else if("holiday".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("holiday");
		}
		else if("logout".equals(indexForm.getTaskIndex()))
>>>>>>> refs/remotes/origin/master
		{
			request.getSession().removeAttribute("username");
			return mapping.findForward("success");
		}
		else
		{
			return mapping.findForward("success");
		}
	}

}
