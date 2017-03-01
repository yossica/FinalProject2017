package client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ClientHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();

		ClientForm clientForm = (ClientForm) form;
		ClientManager clientManager = new ClientManager();

		clientForm.setListClient(clientManager.getAll());
		if ("create".equals(clientForm.getTask())) {
			clientForm.setTask("save" + clientForm.getTask());
			return mapping.findForward("formClient");
		} else if ("savecreate".equals(clientForm.getTask())) {
			ClientBean clientBean = clientForm.getClientBean();
			clientBean.setCreatedBy((String) session.getAttribute("username"));
			clientManager.insert(clientBean);
			clientForm.setListClient(clientManager.getAll());
			return mapping.findForward("client");
		} else if ("update".equals(clientForm.getTask())) {
			clientForm.setTask("save" + clientForm.getTask());
			ClientBean clientBean = clientManager.getById(clientForm
					.getClientBean().getClientId());
			clientForm.setClientBean(clientBean);
			return mapping.findForward("formClient");
		} else if ("saveupdate".equals(clientForm.getTask())) {
			clientForm.getClientBean().setChangedBy(
					(String) session.getAttribute("username"));
			clientManager.update(clientForm.getClientBean());
			clientForm.setListClient(clientManager.getAll());
			return mapping.findForward("client");
		} else {
			return mapping.findForward("client");
		}

	}
}
