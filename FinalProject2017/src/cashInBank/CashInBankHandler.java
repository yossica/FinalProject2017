package cashInBank;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import master.CashFlowCategoryBean;
import master.MasterManager;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import utils.Filter;

public class CashInBankHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CashInBankForm cashInBankForm = (CashInBankForm) form;
		CashInBankManager cashInBankManager = new CashInBankManager();
		MasterManager masterManager = new MasterManager();
		
		if("filter".equals(cashInBankForm.getTask())){
			return mapping.findForward("cashInBank");
		}
		else if("balancing".equals(cashInBankForm.getTask())){
			cashInBankForm.setTask("saveBalance");
			
			return mapping.findForward("form");
		}
		else if("debit".equals(cashInBankForm.getTask())){
			cashInBankForm.setTask("saveDebit");
			cashInBankForm.setIsDebit(1);
			
			Map paramMap = new HashMap();
			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Cash In Bank");
			paramMap.put("isDebit", 1);
			CashFlowCategoryBean cashFlowCategoryBean;
			List cashFlowCategoryList = masterManager.getAllCashFlowCategory(paramMap);
			for (Object obj : cashFlowCategoryList) {
				cashFlowCategoryBean = (CashFlowCategoryBean) obj;
				cashFlowCategoryBean.setName(cashFlowCategoryBean.getName()+"-"+(cashFlowCategoryBean.getIsDebit()==1?"Debit":"Credit"));
			}
			cashInBankForm.setCashFlowCategoryList(cashFlowCategoryList);
			
			return mapping.findForward("form");
		}
		else if("credit".equals(cashInBankForm.getTask())){
			cashInBankForm.setTask("saveCredit");
			cashInBankForm.setIsDebit(0);
			
			Map paramMap = new HashMap();
			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Cash In Bank");
			paramMap.put("isDebit", 0);
			CashFlowCategoryBean cashFlowCategoryBean;
			List cashFlowCategoryList = masterManager.getAllCashFlowCategory(paramMap);
			for (Object obj : cashFlowCategoryList) {
				cashFlowCategoryBean = (CashFlowCategoryBean) obj;
				cashFlowCategoryBean.setName(cashFlowCategoryBean.getName()+"-"+(cashFlowCategoryBean.getIsDebit()==1?"Debit":"Credit"));
			}
			cashInBankForm.setCashFlowCategoryList(cashFlowCategoryList);
			
			return mapping.findForward("form");
		}
		else if("transfer".equals(cashInBankForm.getTask())){
			return mapping.findForward("form");
		}
		else if("export".equals(cashInBankForm.getTask())){
			return mapping.findForward("cashInBank");
		}
		else {
			cashInBankForm.setRemainingBalance(cashInBankManager.getCurrentBalance());
			
			Map paramMap = new HashMap();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			paramMap.put("startDate",dateFormat.format(cal.getTime()));
			cashInBankForm.setFilterStartDate(showDateFormat.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -30);
			paramMap.put("endDate", dateFormat.format(cal.getTime()));
			cashInBankForm.setFilterEndDate(showDateFormat.format(cal.getTime()));			
			paramMap.put("category", null);			
			cashInBankForm.setTransactionList(cashInBankManager.getAllWithFilter(paramMap));
			
			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Cash In Bank");
			paramMap.put("isDebit", null);
			CashFlowCategoryBean cashFlowCategoryBean;
			List cashFlowCategoryList = masterManager.getAllCashFlowCategory(paramMap);
			for (Object obj : cashFlowCategoryList) {
				cashFlowCategoryBean = (CashFlowCategoryBean) obj;
				cashFlowCategoryBean.setName(cashFlowCategoryBean.getName()+"-"+(cashFlowCategoryBean.getIsDebit()==1?"Debit":"Credit"));
			}
			cashFlowCategoryBean = new CashFlowCategoryBean();
			cashFlowCategoryBean.setCashFlowCategoryId("0");
			cashFlowCategoryBean.setName("All");
			cashFlowCategoryList.add(0, cashFlowCategoryBean);
			cashInBankForm.setCategoryId("0");
			cashInBankForm.setCashFlowCategoryList(cashFlowCategoryList);
			
			return mapping.findForward("cashInBank");
		}
	}
}

