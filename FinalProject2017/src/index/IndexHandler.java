package index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import master.MasterManager;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cashInBank.CashInBankManager;
import pettyCash.PettyCashManager;

public class IndexHandler extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IndexForm indexForm = (IndexForm) form;
		
		if("invoice".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("invoice");
		}
		else if("pettyCash".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("pettyCash");
		}
		else if("cashInBank".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("cashInBank");
		}
		else if("financeSummary".equals(indexForm.getTaskIndex()))
		{
			MasterManager masterManager = new MasterManager();
			PettyCashManager pettyCashManager = new PettyCashManager();
			CashInBankManager cashInBankManager = new CashInBankManager();
			indexForm.setFinanceSummaryList(masterManager.getAllFinanceSummary());
			indexForm.setCashInBankBalance(cashInBankManager.getCurrentBalance());
			indexForm.setPettyCashBalance(pettyCashManager.getCurrentBalance());
			return mapping.findForward("financeSummary");
		}
		else if("changePassword".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("changePassword");
		}
		else if("employee".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("employee");
		}
		else if("additionalTraining".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("additionalTraining");
		}
		else if("generalInformation".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("generalInformation");
		}
		else if("holiday".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("holiday");
		}
		else if("outsource".equals(indexForm.getTaskIndex()))
		{
			return mapping.findForward("outsource");
		}
		else if("logout".equals(indexForm.getTaskIndex()))
		{
			request.getSession().removeAttribute("username");
			return mapping.findForward("success");
		}
		else
		{
			return mapping.findForward("success");
		}
	}

}
