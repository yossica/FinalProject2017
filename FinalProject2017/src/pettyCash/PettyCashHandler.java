package pettyCash;

import generalInformation.GeneralInformationManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import master.CashFlowCategoryBean;
import master.MasterManager;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PettyCashHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();

		if (session.getAttribute("username") == null) {
			return mapping.findForward("login");
		}

		PettyCashForm pettyCashForm = (PettyCashForm) form;
		PettyCashManager pettyCashManager = new PettyCashManager();
		MasterManager masterManager = new MasterManager();
		GeneralInformationManager generalInformationManager = new GeneralInformationManager();

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if ("filter".equals(pettyCashForm.getTask())) {
			pettyCashForm.setRemainingBalance(pettyCashManager
					.getCurrentBalance());

			Map paramMap = new HashMap();

			if ("".equals(pettyCashForm.getFilterEndDate())) {
				paramMap.put("endDate", null);
				paramMap.put("startDate", null);
			} else {
				Calendar cal = Calendar.getInstance();
				cal.setTime(showDateFormat.parse(pettyCashForm
						.getFilterEndDate()));
				paramMap.put("endDate", dateFormat.format(cal.getTime()));
				pettyCashForm.setFilterEndDate(showDateFormat.format(cal
						.getTime()));
				cal.setTime(showDateFormat.parse(pettyCashForm
						.getFilterStartDate()));
				paramMap.put("startDate", dateFormat.format(cal.getTime()));
				pettyCashForm.setFilterStartDate(showDateFormat.format(cal
						.getTime()));
			}
			String cat = pettyCashForm.getCategoryId();
			if ("".equals(cat))
				paramMap.put("category", null);
			else
				paramMap.put("category", cat);
			pettyCashForm.setTransactionList(pettyCashManager
					.getAllWithFilter(paramMap));

			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Petty Cash");
			paramMap.put("isDebit", null);
			paramMap.put("isEnabled", null);
			CashFlowCategoryBean cashFlowCategoryBean;
			List cashFlowCategoryList = masterManager
					.getAllCashFlowCategory(paramMap);
			for (Object obj : cashFlowCategoryList) {
				cashFlowCategoryBean = (CashFlowCategoryBean) obj;
				cashFlowCategoryBean.setName(cashFlowCategoryBean.getName()
						+ "-"
						+ (cashFlowCategoryBean.getIsDebit() == 1 ? "Debit"
								: "Credit"));
			}
			pettyCashForm.setCashFlowCategoryList(cashFlowCategoryList);
			pettyCashForm.setCategoryId(pettyCashForm.getCategoryId());

			return mapping.findForward("pettyCash");
		} else if ("balancing".equals(pettyCashForm.getTask())) {
			// get max cash
			double maxBalance = Double.parseDouble(generalInformationManager
					.getByKey("max_petty").getValue());
			double currBalance = pettyCashManager.getCurrentBalance();

			Calendar cal = Calendar.getInstance();

			PettyCashBean pettyCashBean = new PettyCashBean();
			pettyCashBean.setCashFlowCategoryId("0p-bal");
			pettyCashBean.setAmount(maxBalance - currBalance);
			pettyCashBean.setBalance(maxBalance);
			pettyCashBean.setDescription("Balancing Petty Cash "
					+ dateFormat.format(cal.getTime()));
			pettyCashBean.setTransactionDate(dateFormat.format(cal.getTime()));
			pettyCashBean.setIsDebit(0);
			pettyCashBean.setCreatedBy((String) session
					.getAttribute("username"));
			pettyCashManager.insert(pettyCashBean);

			pettyCashForm.setRemainingBalance(pettyCashManager
					.getCurrentBalance());

			Map paramMap = new HashMap();

			paramMap.put("endDate", dateFormat.format(cal.getTime()));
			pettyCashForm
					.setFilterEndDate(showDateFormat.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -30);
			paramMap.put("startDate", dateFormat.format(cal.getTime()));
			pettyCashForm.setFilterStartDate(showDateFormat.format(cal
					.getTime()));
			paramMap.put("category", null);
			pettyCashForm.setTransactionList(pettyCashManager
					.getAllWithFilter(paramMap));

			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Petty Cash");
			paramMap.put("isDebit", null);
			paramMap.put("isEnabled", null);
			CashFlowCategoryBean cashFlowCategoryBean;
			List cashFlowCategoryList = masterManager
					.getAllCashFlowCategory(paramMap);
			for (Object obj : cashFlowCategoryList) {
				cashFlowCategoryBean = (CashFlowCategoryBean) obj;
				cashFlowCategoryBean.setName(cashFlowCategoryBean.getName()
						+ "-"
						+ (cashFlowCategoryBean.getIsDebit() == 1 ? "Debit"
								: "Credit"));
			}
			pettyCashForm.setCashFlowCategoryList(cashFlowCategoryList);

			return mapping.findForward("pettyCash");
		} else if ("debit".equals(pettyCashForm.getTask())) {
			pettyCashForm.setTask("saveDebit");
			pettyCashForm.getPettyCashBean().setIsDebit(1);

			pettyCashForm.setRemainingBalance(pettyCashManager
					.getCurrentBalance());

			Map paramMap = new HashMap();
			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Petty Cash");
			paramMap.put("isDebit", 1);
			paramMap.put("isEnabled", "1");
			CashFlowCategoryBean cashFlowCategoryBean;
			List cashFlowCategoryList = masterManager
					.getAllCashFlowCategory(paramMap);
			for (Object obj : cashFlowCategoryList) {
				cashFlowCategoryBean = (CashFlowCategoryBean) obj;
				cashFlowCategoryBean.setName(cashFlowCategoryBean.getName()
						+ "-"
						+ (cashFlowCategoryBean.getIsDebit() == 1 ? "Debit"
								: "Credit"));
			}
			pettyCashForm.setCashFlowCategoryList(cashFlowCategoryList);

			pettyCashForm.getPettyCashBean().setTransactionDate(
					showDateFormat.format(Calendar.getInstance().getTime()));

			return mapping.findForward("form");
		} else if ("saveDebit".equals(pettyCashForm.getTask())) {
			Calendar cal = Calendar.getInstance();
			// validate, if false, go back to form
			double currBalance = pettyCashManager.getCurrentBalance();
			if (currBalance - pettyCashForm.getPettyCashBean().getAmount() < 0) {
				pettyCashForm.getMessageList().clear();
				pettyCashForm
						.getMessageList()
						.add("Cannot create transaction that cost more than remaining balance!");

				pettyCashForm.setTask("saveDebit");
				pettyCashForm.getPettyCashBean().setIsDebit(1);

				pettyCashForm.setRemainingBalance(pettyCashManager
						.getCurrentBalance());

				Map paramMap = new HashMap();
				paramMap = new HashMap();
				paramMap.put("cashFlowType", "Petty Cash");
				paramMap.put("isDebit", 1);
				paramMap.put("isEnabled", "1");
				CashFlowCategoryBean cashFlowCategoryBean;
				List cashFlowCategoryList = masterManager
						.getAllCashFlowCategory(paramMap);
				for (Object obj : cashFlowCategoryList) {
					cashFlowCategoryBean = (CashFlowCategoryBean) obj;
					cashFlowCategoryBean.setName(cashFlowCategoryBean.getName()
							+ "-"
							+ (cashFlowCategoryBean.getIsDebit() == 1 ? "Debit"
									: "Credit"));
				}
				pettyCashForm.setCashFlowCategoryList(cashFlowCategoryList);
				pettyCashForm.getPettyCashBean().setTransactionDate(
						pettyCashForm.getPettyCashBean().getTransactionDate());
				return mapping.findForward("form");
			}
			// save to cash in bank
			PettyCashBean pettyCashBean = pettyCashForm.getPettyCashBean();
			pettyCashBean.setBalance(currBalance
					- pettyCashForm.getPettyCashBean().getAmount());
			cal.setTime(showDateFormat.parse(pettyCashForm.getPettyCashBean()
					.getTransactionDate()));
			pettyCashBean.setTransactionDate(dateFormat.format(cal.getTime()));
			pettyCashBean.setCreatedBy((String) session
					.getAttribute("username"));
			pettyCashManager.insert(pettyCashBean);

			// show cash in bank view
			pettyCashForm.setRemainingBalance(pettyCashManager
					.getCurrentBalance());

			Map paramMap = new HashMap();
			cal = Calendar.getInstance();
			paramMap.put("endDate", dateFormat.format(cal.getTime()));
			pettyCashForm
					.setFilterEndDate(showDateFormat.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -30);
			paramMap.put("startDate", dateFormat.format(cal.getTime()));
			pettyCashForm.setFilterStartDate(showDateFormat.format(cal
					.getTime()));
			paramMap.put("category", null);
			pettyCashForm.setTransactionList(pettyCashManager
					.getAllWithFilter(paramMap));

			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Petty Cash");
			paramMap.put("isDebit", null);
			paramMap.put("isEnabled", null);
			CashFlowCategoryBean cashFlowCategoryBean;
			List cashFlowCategoryList = masterManager
					.getAllCashFlowCategory(paramMap);
			for (Object obj : cashFlowCategoryList) {
				cashFlowCategoryBean = (CashFlowCategoryBean) obj;
				cashFlowCategoryBean.setName(cashFlowCategoryBean.getName()
						+ "-"
						+ (cashFlowCategoryBean.getIsDebit() == 1 ? "Debit"
								: "Credit"));
			}
			pettyCashForm.setCategoryId("");
			pettyCashForm.setCashFlowCategoryList(cashFlowCategoryList);

			return mapping.findForward("pettyCash");
		} else if ("credit".equals(pettyCashForm.getTask())) {
			pettyCashForm.setTask("saveCredit");
			pettyCashForm.getPettyCashBean().setIsDebit(0);

			pettyCashForm.setRemainingBalance(pettyCashManager
					.getCurrentBalance());

			Map paramMap = new HashMap();
			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Petty Cash");
			paramMap.put("isDebit", 0);
			paramMap.put("isEnabled", "1");
			CashFlowCategoryBean cashFlowCategoryBean;
			List cashFlowCategoryList = masterManager
					.getAllCashFlowCategory(paramMap);
			for (Object obj : cashFlowCategoryList) {
				cashFlowCategoryBean = (CashFlowCategoryBean) obj;
				cashFlowCategoryBean.setName(cashFlowCategoryBean.getName()
						+ "-"
						+ (cashFlowCategoryBean.getIsDebit() == 1 ? "Debit"
								: "Credit"));
			}
			pettyCashForm.setCashFlowCategoryList(cashFlowCategoryList);

			pettyCashForm.getPettyCashBean().setTransactionDate(
					showDateFormat.format(Calendar.getInstance().getTime()));

			return mapping.findForward("form");
		} else if ("saveCredit".equals(pettyCashForm.getTask())) {
			Calendar cal = Calendar.getInstance();
			double currBalance = pettyCashManager.getCurrentBalance();

			// save to cash in bank
			PettyCashBean pettyCashBean = pettyCashForm.getPettyCashBean();
			pettyCashBean.setBalance(currBalance
					+ pettyCashForm.getPettyCashBean().getAmount());
			cal.setTime(showDateFormat.parse(pettyCashForm.getPettyCashBean()
					.getTransactionDate()));
			pettyCashBean.setTransactionDate(dateFormat.format(cal.getTime()));
			pettyCashBean.setCreatedBy((String) session
					.getAttribute("username"));
			pettyCashManager.insert(pettyCashBean);

			// show cash in bank view
			pettyCashForm.setRemainingBalance(pettyCashManager
					.getCurrentBalance());

			Map paramMap = new HashMap();
			cal = Calendar.getInstance();

			paramMap.put("endDate", dateFormat.format(cal.getTime()));
			pettyCashForm
					.setFilterEndDate(showDateFormat.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -30);
			paramMap.put("startDate", dateFormat.format(cal.getTime()));
			pettyCashForm.setFilterStartDate(showDateFormat.format(cal
					.getTime()));
			paramMap.put("category", null);
			pettyCashForm.setTransactionList(pettyCashManager
					.getAllWithFilter(paramMap));

			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Petty Cash");
			paramMap.put("isDebit", null);
			paramMap.put("isEnabled", null);
			CashFlowCategoryBean cashFlowCategoryBean;
			List cashFlowCategoryList = masterManager
					.getAllCashFlowCategory(paramMap);
			for (Object obj : cashFlowCategoryList) {
				cashFlowCategoryBean = (CashFlowCategoryBean) obj;
				cashFlowCategoryBean.setName(cashFlowCategoryBean.getName()
						+ "-"
						+ (cashFlowCategoryBean.getIsDebit() == 1 ? "Debit"
								: "Credit"));
			}
			pettyCashForm.setCategoryId("");
			pettyCashForm.setCashFlowCategoryList(cashFlowCategoryList);

			return mapping.findForward("pettyCash");
		} else if ("export".equals(pettyCashForm.getTask())) {
			// get all filter field
			// get cash in bank data based on filter field
			// export to pdf

			// show initial page
			pettyCashForm.setRemainingBalance(pettyCashManager
					.getCurrentBalance());

			Map paramMap = new HashMap();
			Calendar cal = Calendar.getInstance();

			paramMap.put("endDate", dateFormat.format(cal.getTime()));
			pettyCashForm
					.setFilterEndDate(showDateFormat.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -30);
			paramMap.put("startDate", dateFormat.format(cal.getTime()));
			pettyCashForm.setFilterStartDate(showDateFormat.format(cal
					.getTime()));
			paramMap.put("category", null);
			pettyCashForm.setTransactionList(pettyCashManager
					.getAllWithFilter(paramMap));

			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Petty Cash");
			paramMap.put("isDebit", null);
			paramMap.put("isEnabled", null);
			CashFlowCategoryBean cashFlowCategoryBean;
			List cashFlowCategoryList = masterManager
					.getAllCashFlowCategory(paramMap);
			for (Object obj : cashFlowCategoryList) {
				cashFlowCategoryBean = (CashFlowCategoryBean) obj;
				cashFlowCategoryBean.setName(cashFlowCategoryBean.getName()
						+ "-"
						+ (cashFlowCategoryBean.getIsDebit() == 1 ? "Debit"
								: "Credit"));
			}
			cashFlowCategoryBean = new CashFlowCategoryBean();
			pettyCashForm.setCategoryId("");
			pettyCashForm.setCashFlowCategoryList(cashFlowCategoryList);

			return mapping.findForward("pettyCash");
		} else {
			pettyCashForm.setRemainingBalance(pettyCashManager
					.getCurrentBalance());

			Map paramMap = new HashMap();
			Calendar cal = Calendar.getInstance();

			paramMap.put("endDate", dateFormat.format(cal.getTime()));
			pettyCashForm
					.setFilterEndDate(showDateFormat.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -30);
			paramMap.put("startDate", dateFormat.format(cal.getTime()));
			pettyCashForm.setFilterStartDate(showDateFormat.format(cal
					.getTime()));
			paramMap.put("category", null);
			pettyCashForm.setTransactionList(pettyCashManager
					.getAllWithFilter(paramMap));

			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Petty Cash");
			paramMap.put("isDebit", null);
			paramMap.put("isEnabled", null);
			CashFlowCategoryBean cashFlowCategoryBean;
			List cashFlowCategoryList = masterManager
					.getAllCashFlowCategory(paramMap);
			for (Object obj : cashFlowCategoryList) {
				cashFlowCategoryBean = (CashFlowCategoryBean) obj;
				cashFlowCategoryBean.setName(cashFlowCategoryBean.getName()
						+ "-"
						+ (cashFlowCategoryBean.getIsDebit() == 1 ? "Debit"
								: "Credit"));
			}
			pettyCashForm.setCategoryId("");
			pettyCashForm.setCashFlowCategoryList(cashFlowCategoryList);

			return mapping.findForward("pettyCash");
		}
	}

}
