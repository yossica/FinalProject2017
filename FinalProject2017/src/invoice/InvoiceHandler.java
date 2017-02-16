package invoice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import master.MasterManager;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import client.ClientManager;

public class InvoiceHandler extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InvoiceForm invoiceForm = (InvoiceForm) form;
		InvoiceManager invoiceManager = new InvoiceManager();
		ClientManager clientManager = new ClientManager();
		MasterManager masterManager = new MasterManager();
		invoiceForm.setClientList(clientManager.getAll());
		invoiceForm.setInvoiceTypeList(masterManager.getAllInvoiceType());

		if ("createInvoice".equals(invoiceForm.getTask())){
			return mapping.findForward("createInvoice");
		}else if ("createInvoicePS".equals(invoiceForm.getTask())) {
			return mapping.findForward("createInvoicePS");
		}else if ("createInvoiceHH".equals(invoiceForm.getTask())) {

			invoiceForm.getInvoiceBean().setClientName(clientManager.getById(invoiceForm.getInvoiceBean().getClientId()).getName());
			invoiceForm.getInvoiceBean().setInvoiceTypeName(masterManager.getInvoiceTypeById(invoiceForm.getInvoiceBean().getInvoiceTypeId()).getName());

			return mapping.findForward("createInvoiceHH");
		}else if ("createInvoiceTR".equals(invoiceForm.getTask())) {
			return mapping.findForward("createInvoiceTR");
		}else {
			invoiceForm.setInvoiceList(invoiceManager.getAllWithFilter());
			return mapping.findForward("invoice");
		}
	}
}
