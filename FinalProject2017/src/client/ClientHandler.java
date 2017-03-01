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
			ClientBean clientBean = new ClientBean();
			clientBean.setName(clientForm.getName());
			clientBean.setAddress(clientForm.getAddress());
			clientBean.setCity(clientForm.getCity());
			clientBean.setPhoneNumber(clientForm.getPhoneNumber());
			clientBean.setFaxNumber(clientForm.getFaxNumber());
			clientBean.setPostalCode(clientForm.getPostalCode());
			clientBean.setContactPerson(clientForm.getContactPerson());
			clientBean.setIsEnabled(clientForm.getIsEnabled());
			clientBean.setCreatedBy((String) session.getAttribute("username"));
			clientManager.insert(clientBean);
			clientForm.setListClient(clientManager.getAll());
			return mapping.findForward("client");
		} else if ("update".equals(clientForm.getTask())) {
			clientForm.setTask("save" + clientForm.getTask());
			ClientBean clientBean = clientManager.getById(clientForm
					.getClientId());
			clientForm.setClientId(clientBean.getClientId());
			clientForm.setName(clientBean.getName());
			clientForm.setAddress(clientBean.getAddress());
			clientForm.setCity(clientBean.getCity());
			clientForm.setPhoneNumber(clientBean.getPhoneNumber());
			clientForm.setFaxNumber(clientBean.getFaxNumber());
			clientForm.setPostalCode(clientBean.getPostalCode());
			clientForm.setContactPerson(clientBean.getContactPerson());
			clientForm.setIsEnabled(clientBean.getIsEnabled());
			return mapping.findForward("formClient");
		} else if ("saveupdate".equals(clientForm.getTask())) {
			ClientBean clientBean = new ClientBean();
			clientBean.setClientId(clientForm.getClientId());
			clientBean.setName(clientForm.getName());
			clientBean.setAddress(clientForm.getAddress());
			clientBean.setCity(clientForm.getCity());
			clientBean.setPhoneNumber(clientForm.getPhoneNumber());
			clientBean.setFaxNumber(clientForm.getFaxNumber());
			clientBean.setPostalCode(clientForm.getPostalCode());
			clientBean.setContactPerson(clientForm.getContactPerson());
			clientBean.setIsEnabled(clientForm.getIsEnabled());
			clientBean.setChangedBy((String) session.getAttribute("username"));
			clientManager.update(clientBean);
			clientForm.setListClient(clientManager.getAll());
			return mapping.findForward("client");
		} else {
			return mapping.findForward("client");
		}

	}
}
