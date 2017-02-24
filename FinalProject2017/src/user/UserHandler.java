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
		UserForm userForm = (UserForm) form;
		UserManager userManager = new UserManager();
		HttpSession session = request.getSession(true);

		if ("login".equals(userForm.getTask())) {
			if(userForm.getUserName() == null){
				return mapping.findForward("login");
			}
			UserBean userBean = new UserBean();
			userBean.setUserName(userForm.getUserName());
			userBean.setPassword(userForm.getPassword());
			int count = userManager.checkLogin(userBean);
			if (count > 0) {
				session.setAttribute("username", userBean.getUserName());
				session.setMaxInactiveInterval(30*60);//30 menit
				response.sendRedirect("/FinalProject2017/index.do");
				return null;
			}
			else {
				userForm.setTask("");
				userForm.getMessageList().clear();
				userForm.getMessageList().add("Ooooops incorrect Username or Password");
				return mapping.findForward("login");
			}
		} else if ("changePassword".equals(userForm.getTask())) {
			if (session.getAttribute("username") == null) {
				return mapping.findForward("login");
			}
			userForm.setUserName(session.getAttribute("username").toString());
			userForm.setTask("");
			return mapping.findForward("changePassword");
		} else if ("saveChangePassword".equals(userForm.getTask())) {
			// validasi login
			if (session.getAttribute("username") == null) {
				return mapping.findForward("login");
			}
			
			boolean flag = true;
			// validasi old password sama ga sama yang di database
			UserBean userBean = new UserBean();
			userBean.setUserName((String)session.getAttribute("username"));
			userBean.setPassword(userForm.getPassword());
			userForm.getMessageList().clear();
			if(userManager.checkLogin(userBean) == 0){
				userForm.getMessageList().add("Ooooops Old Password does not match");
				flag = false;
			}
			// validasi new password dengan confirm password sama apa nggak
			if(!userForm.getNewPassword().equals(userForm.getConfirmPassword())){
				userForm.getMessageList().add("Ooooops New Password is not same as Confirm Password");
				flag = false;
			}
			if(!flag) {
				userForm.setPassword("");
				userForm.setNewPassword("");
				userForm.setConfirmPassword("");
				return mapping.findForward("changePassword");
			}
			// update password
			userBean.setPassword(userForm.getNewPassword());
			userManager.changePassword(userBean);
			userForm.setPassword("");
			userForm.setNewPassword("");
			userForm.setConfirmPassword("");
			userForm.getMessageList().add("Success Change Password");
			userForm.setTask("");
			return mapping.findForward("changePassword");
		} else if ("manageUser".equals(userForm.getTask())) {
			userForm.setUserList(userManager.getAll());
			return mapping.findForward("manageUser");
		} else if ("insertUser".equals(userForm.getTask())) {
			userForm.getMessageList().clear();
			if(userManager.checkUsername(userForm.getNewUser()) > 0){
				userForm.getMessageList().add("Username already exist!");
				return mapping.findForward("manageUser");
			}
			userManager.insert(userForm.getNewUser());
			userForm.setNewUser("");
			userForm.setUserList(userManager.getAll());
			return mapping.findForward("manageUser");
		} else if ("resetPassword".equals(userForm.getTask())) {
			userManager.resetPassword(userForm.getUserName());
			userForm.setUserList(userManager.getAll());
			return mapping.findForward("manageUser");
		} else {
			return mapping.findForward("login");
		}
	}

}
