package user;

import index.IndexForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class UserHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
<<<<<<< HEAD
		//return mapping.findForward("login");
=======
		UserForm userForm = (UserForm) form;
		UserManager userManager = new UserManager();
		HttpSession session = request.getSession(true);
		
		if("login".equals(userForm.getTaskLogin())){
			UserBean userBean = new UserBean();
			userBean.setUserName(userForm.getUserName());
			userBean.setPassword(userForm.getPassword());
			int count=userManager.checkLogin(userBean);
			if(count>0)
			{
				session.setAttribute("username", userBean.getUserName());
				response.sendRedirect("/FinalProject2017/index.do");
			}
			return null;
		}
		else {
			return mapping.findForward("login");
		}
>>>>>>> refs/remotes/origin/master
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
