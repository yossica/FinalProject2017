package client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ClientHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ClientForm clientForm = (ClientForm) form;
		ClientManager clientManager = new ClientManager();

		clientForm.setListClient(clientManager.getAll());
		if ("formClient".equals(clientForm.getTask())) {
			return mapping.findForward("formClient");
		} else if("insert".equals(clientForm.getTask())) {
			ClientBean clientBean = new ClientBean();
			clientBean.setName(clientForm.getName());
			clientBean.setAddress(clientForm.getAddress());
			clientBean.setCity(clientForm.getCity());
			clientBean.setPhoneNumber(clientForm.getPhoneNumber());
			clientBean.setFaxNumber(clientForm.getFaxNumber());
			clientBean.setPostalCode(clientForm.getPostalCode());
		/*	clientBean.setCreatedBy();*/
			return mapping.findForward("client");
		} else {
			return mapping.findForward("client");
		}

	}
}
