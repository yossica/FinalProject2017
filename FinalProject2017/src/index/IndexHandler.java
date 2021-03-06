package index;

import invoice.InvoiceBean;
import invoice.InvoiceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import master.MasterManager;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import outsource.OutsourceBean;
import outsource.OutsourceManager;
import pettyCash.PettyCashManager;
import training.TrainingManager;
import cashInBank.CashInBankManager;

public class IndexHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();

		IndexForm indexForm = (IndexForm) form;
		OutsourceManager outsourceManager = new OutsourceManager();
		InvoiceManager invoiceManager = new InvoiceManager();
		TrainingManager trainingManager = new TrainingManager();

		if ("invoice".equals(indexForm.getTaskIndex())) {
			return mapping.findForward("invoice");
		} else if ("pettyCash".equals(indexForm.getTaskIndex())) {
			return mapping.findForward("pettyCash");
		} else if ("createInvoiceIndex".equals(indexForm.getTaskIndex())) {
			return mapping.findForward("createInvoiceIndex");
		} else if ("createInvoiceIndexTr".equals(indexForm.getTaskIndex())) {
			return mapping.findForward("createInvoiceIndex");
		} else if ("cashInBank".equals(indexForm.getTaskIndex())) {
			return mapping.findForward("cashInBank");
		} else if ("financeSummary".equals(indexForm.getTaskIndex())) {
			MasterManager masterManager = new MasterManager();
			PettyCashManager pettyCashManager = new PettyCashManager();
			CashInBankManager cashInBankManager = new CashInBankManager();
			indexForm.setFinanceSummaryList(masterManager
					.getAllFinanceSummary());
			indexForm.setCashInBankBalance(cashInBankManager
					.getCurrentBalance());
			indexForm.setPettyCashBalance(pettyCashManager.getCurrentBalance());
			return mapping.findForward("financeSummary");
		} else if ("client".equals(indexForm.getTaskIndex())) {
			return mapping.findForward("client");
		} else if ("changePassword".equals(indexForm.getTaskIndex())) {
			return mapping.findForward("changePassword");
		} else if ("manageUser".equals(indexForm.getTaskIndex())) {
			return mapping.findForward("manageUser");
		} else if ("employee".equals(indexForm.getTaskIndex())) {
			return mapping.findForward("employee");
		} else if ("additionalTraining".equals(indexForm.getTaskIndex())) {
			return mapping.findForward("additionalTraining");
		} else if ("generalInformation".equals(indexForm.getTaskIndex())) {
			return mapping.findForward("generalInformation");
		} else if ("holiday".equals(indexForm.getTaskIndex())) {
			return mapping.findForward("holiday");
		} else if ("outsource".equals(indexForm.getTaskIndex())) {
			return mapping.findForward("outsource");
		} else if ("logout".equals(indexForm.getTaskIndex())) {
			session.removeAttribute("username");
			return mapping.findForward("success");
		} else {
			Integer currentPeriod = Integer.parseInt(outsourceManager
					.getPeriod());
			List<OutsourceBean> list = outsourceManager.getMinStartDate();
			List<InvoiceBean> arrList = new ArrayList();
			for (int i = 0; i < list.size(); i++) {
				currentPeriod = Integer.parseInt(outsourceManager.getPeriod());
				int period = Integer.parseInt(list.get(i).getStartDate());
				while (currentPeriod >= period) {
					String exampleDate = (currentPeriod + "").substring(4, 6)
							+ "/01/" + (currentPeriod + "").substring(0, 4);
					Map paramMap = new HashMap();
					paramMap.put("clientId", list.get(i).getClientId());
					paramMap.put("exampleDate", exampleDate);
					if (outsourceManager.checkContract(paramMap) != 0) {
						// punya kontrak di period yang bersangkutan
						// apakah punya invoice??
						Integer periodYear = Integer
								.parseInt((currentPeriod + "").substring(0, 4));
						Integer periodMonth = Integer
								.parseInt((currentPeriod + "").substring(4, 6));
						paramMap.put("periodMonth", periodMonth);
						paramMap.put("periodYear", periodYear);
						if (invoiceManager.checkInvoice(paramMap) == 0) {
							InvoiceBean invoiceBean = new InvoiceBean();
							invoiceBean.setClientName(list.get(i)
									.getClientName());
							invoiceBean.setClientId(list.get(i).getClientId());
							invoiceBean.setPeriodMonth(periodMonth);
							invoiceBean.setPeriodYear(periodYear);
							invoiceBean.setInvoiceTypeId(1);
							arrList.add(invoiceBean);
						}
					}
					//gimana cara mundur in dari jan 2017 ke des 2016
					Integer year = Integer.parseInt((currentPeriod + "")
							.substring(0, 4));
					Integer month = Integer.parseInt((currentPeriod + "")
							.substring(4, 6));
					if (month == 1) {
						year--;
						month = 12;
					} else {
						month--;
					}
					currentPeriod = Integer.parseInt("" + year
							+ String.format("%02d", month));
				}
			}
			indexForm.setListedRemainderList(arrList);
			indexForm.setCreatedRemainderList(invoiceManager
					.getCreatedRemainderList());
			indexForm.setSentOutsourceRemainderList(invoiceManager
					.getSentOutsourceRemainderList());
			indexForm.setListedTrainingRemainderList(trainingManager
					.getListedTrainingRemainderList());

			return mapping.findForward("success");
		}
	}

}
