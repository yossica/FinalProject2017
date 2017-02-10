package outsource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import client.ClientManager;

public class OutsourceHandler extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		OutsourceForm outsourceForm = (OutsourceForm) form;
		OutsourceManager outsourceManager = new OutsourceManager();
		ClientManager clientManager = new ClientManager();
		outsourceForm.setClientList(clientManager.getAll());
		
		return mapping.findForward("outsource");
	}

}
