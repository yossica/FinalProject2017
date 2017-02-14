package invoice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import client.ClientManager;
import client.ClientBean;

public class InvoiceHandler extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InvoiceForm invoiceForm = (InvoiceForm) form;
		InvoiceManager invoiceManager = new InvoiceManager();
		ClientManager clientManager = new ClientManager();
		invoiceForm.setClientList(clientManager.getAll());
		if ("createInvoice".equals(invoiceForm.getTask())){
			return mapping.findForward("createInvoice");
		}else if ("createInvoicePS".equals(invoiceForm.getTask())) {
			return mapping.findForward("createInvoicePS");
		}else if ("createInvoiceHH".equals(invoiceForm.getTask())) {
			return mapping.findForward("createInvoiceHH");
		}else if ("createInvoiceTR".equals(invoiceForm.getTask())) {
			return mapping.findForward("createInvoiceTR");
		}else {
			return mapping.findForward("invoice");
		}
	}
}
