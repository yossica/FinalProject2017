package user;

import index.IndexForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UserHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//return mapping.findForward("login");
		//change password
		//cek login dulu biar pastiin old passwordnya sama kaya old password yg di input
		
		UserForm userForm = (UserForm) form;
		if("changePassword".equals(userForm.getTask()))
		{
			return mapping.findForward("changePassword");
		}
		else
		{
			//return mapping.findForward("success");
			return mapping.findForward("login");
		}
	}

}
