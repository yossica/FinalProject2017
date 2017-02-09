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
		
		if("invoice".equals(indexForm.getTask()))
		{
			return mapping.findForward("invoice");
		}
		else if("pettyCash".equals(indexForm.getTask()))
		{
			return mapping.findForward("pettyCash");
		}
		else if("cashInBank".equals(indexForm.getTask()))
		{
			return mapping.findForward("cashInBank");
		}
		else if("financeSummary".equals(indexForm.getTask()))
		{
			return mapping.findForward("financeSummary");
		}
		else if("changePassword".equals(indexForm.getTask()))
		{
			return mapping.findForward("changePassword");
		}
		else if("holiday".equals(indexForm.getTask()))
		{
			return mapping.findForward("holiday");
		}
		else if("logout".equals(indexForm.getTask()))
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
