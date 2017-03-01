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
		if (session.getAttribute("username") == null) {
			return mapping.findForward("login");
		}
		ClientForm clientForm = (ClientForm) form;
		ClientManager clientManager = new ClientManager();

		clientForm.setListClient(clientManager.getAll());
		if ("create".equals(clientForm.getTask())) {
			clientForm.setTask("save" + clientForm.getTask());
			return mapping.findForward("formClient");
		} else if ("savecreate".equals(clientForm.getTask())) {
			ClientBean clientBean = new ClientBean();
			clientBean.setName(clientForm.getClientBean().getName());
			clientBean.setAddress(clientForm.getClientBean().getAddress());
			clientBean.setCity(clientForm.getClientBean().getCity());
			clientBean.setPhoneNumber(clientForm.getClientBean().getPhoneNumber());
			clientBean.setFaxNumber(clientForm.getClientBean().getFaxNumber());
			clientBean.setPostalCode(clientForm.getClientBean().getPostalCode());
			clientBean.setContactPerson(clientForm.getClientBean().getContactPerson());
			clientBean.setIsEnabled(clientForm.getClientBean().getIsEnabled());
			clientBean.setCreatedBy((String) session.getAttribute("username"));
			clientManager.insert(clientBean);
			clientForm.setListClient(clientManager.getAll());
			return mapping.findForward("client");
		} else if ("update".equals(clientForm.getTask())) {
			clientForm.setTask("save" + clientForm.getTask());
			ClientBean clientBean = clientManager.getById(clientForm.getClientBean().getClientId());
			clientForm.getClientBean().setClientId(clientBean.getClientId());
			clientForm.getClientBean().setName(clientBean.getName());
			clientForm.getClientBean().setAddress(clientBean.getAddress());
			clientForm.getClientBean().setCity(clientBean.getCity());
			clientForm.getClientBean().setPhoneNumber(clientBean.getPhoneNumber());
			clientForm.getClientBean().setFaxNumber(clientBean.getFaxNumber());
			clientForm.getClientBean().setPostalCode(clientBean.getPostalCode());
			clientForm.getClientBean().setContactPerson(clientBean.getContactPerson());
			clientForm.getClientBean().setIsEnabled(clientBean.getIsEnabled());
			return mapping.findForward("formClient");
		} else if ("saveupdate".equals(clientForm.getTask())) {
			ClientBean clientBean = new ClientBean();
			clientBean.setClientId(clientForm.getClientBean().getClientId());
			clientBean.setName(clientForm.getClientBean().getName());
			clientBean.setAddress(clientForm.getClientBean().getAddress());
			clientBean.setCity(clientForm.getClientBean().getCity());
			clientBean.setPhoneNumber(clientForm.getClientBean().getPhoneNumber());
			clientBean.setFaxNumber(clientForm.getClientBean().getFaxNumber());
			clientBean.setPostalCode(clientForm.getClientBean().getPostalCode());
			clientBean.setContactPerson(clientForm.getClientBean().getContactPerson());
			clientBean.setIsEnabled(clientForm.getClientBean().getIsEnabled());
			clientBean.setChangedBy((String) session.getAttribute("username"));
			clientManager.update(clientBean);
			clientForm.setListClient(clientManager.getAll());
			return mapping.findForward("client");
		} else {
			return mapping.findForward("client");
		}

	}
}
