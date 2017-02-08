package user;

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
		UserForm userForm = (UserForm) form;
		UserManager userManager = new UserManager();
		
		if("login".equals(userForm.getTaskLogin()))
		{
			return mapping.findForward("login");
		}
		return null;
		//change password
		//cek login dulu biar pastiin old passwordnya sama kaya old password yg di input
		
	}

}
