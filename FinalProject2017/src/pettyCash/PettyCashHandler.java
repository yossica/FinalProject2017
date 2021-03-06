package pettyCash;

import generalInformation.GeneralInformationManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import master.CashFlowCategoryBean;
import master.MasterManager;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cashInBank.CashInBankBean;
import cashInBank.CashInBankManager;
import utils.ExportReportManager;

public class PettyCashHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();

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

			double amount = maxBalance - currBalance;
			PettyCashBean pettyCashBean = new PettyCashBean();
			CashInBankBean cashInBankBean = new CashInBankBean();
			if (amount <= 0) {
				pettyCashBean.setCashFlowCategoryId("1p-bal");
				pettyCashBean.setAmount(Math.abs(amount));
				pettyCashBean.setIsDebit(1);
				cashInBankBean.setCashFlowCategoryId("0c-bal");
				cashInBankBean.setAmount(Math.abs(amount));
				cashInBankBean.setIsDebit(0);
			} else {
				pettyCashBean.setCashFlowCategoryId("0p-bal");
				pettyCashBean.setAmount(amount);
				pettyCashBean.setIsDebit(0);
				cashInBankBean.setCashFlowCategoryId("1c-bal");
				cashInBankBean.setAmount(amount);
				cashInBankBean.setIsDebit(1);
			}
			String desc = "Balancing Petty Cash "
					+ dateFormat.format(cal.getTime());
			pettyCashBean.setDescription(desc);
			pettyCashBean.setTransactionDate(dateFormat.format(cal.getTime()));
			pettyCashBean.setCreatedBy((String) session
					.getAttribute("username"));
			pettyCashManager.insert(pettyCashBean);
			//insert transaction to cash in bank
			cashInBankBean.setDescription(desc);
			cashInBankBean.setTransactionDate(dateFormat.format(cal.getTime()));
			cashInBankBean.setCreatedBy((String) session
					.getAttribute("username"));
			new CashInBankManager().insert(cashInBankBean);
			
			//view list petty cash
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
			/*
			 * pettyCashForm.getMessageList().add(
			 * "Success!!! Petty Cash has been balanced!");
			 */
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
			double max_transaction = Double
					.parseDouble(generalInformationManager.getByKey(
							"max_trans_petty").getValue());
			double amount = pettyCashForm.getPettyCashBean().getAmount();
			boolean flag = true;
			if (currBalance - amount < 0) {
				pettyCashForm.getMessageList().clear();
				pettyCashForm
						.getMessageList()
						.add("Ooooops!!! Cannot create transaction that cost more than remaining balance!");

				flag = false;
			} else if (amount > max_transaction) {
				pettyCashForm.getMessageList().clear();
				pettyCashForm.getMessageList().add(
						"Ooooops!!! Cannot create transaction more than IDR"
								+ max_transaction
								+ "!\nPlease do this in cash in bank instead");
				flag = false;
			}

			if (!flag) {
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
			String message = pettyCashManager.insert(pettyCashBean);
			if (message.startsWith("Failed")) {
				pettyCashForm.getMessageList().clear();
				pettyCashForm.getMessageList().add(message);
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
			pettyCashForm.getMessageList().add(
					"Success!!! Transaction has been added!");
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
			// validate, if false, go back to form
			double currBalance = pettyCashManager.getCurrentBalance();
			double max_transaction = Double
					.parseDouble(generalInformationManager.getByKey(
							"max_trans_petty").getValue());
			double amount = pettyCashForm.getPettyCashBean().getAmount();
			boolean flag = true;
			if (amount > max_transaction) {
				pettyCashForm.getMessageList().clear();
				pettyCashForm.getMessageList().add(
						"Ooooops!!! Cannot create transaction more than IDR"
								+ max_transaction + "!");
				flag = false;
			}

			if (!flag) {
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
						pettyCashForm.getPettyCashBean().getTransactionDate());
				return mapping.findForward("form");
			}

			// save to cash in bank
			PettyCashBean pettyCashBean = pettyCashForm.getPettyCashBean();
			pettyCashBean.setBalance(currBalance + amount);
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
			pettyCashForm.getMessageList().add(
					"Success!!! Transaction has been added!");
			return mapping.findForward("pettyCash");
		} else if ("export".equals(pettyCashForm.getTask())) {
			Map paramMap = new HashMap();
			Calendar cal = Calendar.getInstance();

			// get all filter field
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("name", generalInformationManager.getByKey("name")
					.getValue());
			parameters.put("address", generalInformationManager
					.getByKey("addr").getValue()
					+ "\nPhone:"
					+ generalInformationManager.getByKey("telp").getValue()
					+ "\nFax: "
					+ generalInformationManager.getByKey("fax").getValue());
			parameters.put("reportType", "Petty Cash");
			String cat = pettyCashForm.getCategoryId();
			if ("".equals(cat)) {
				paramMap.put("category", null);
				parameters.put("category", "All");
			} else {
				paramMap.put("category", cat);
				parameters.put("category", masterManager
						.getCashFlowCategoryById(cat).getName());
			}

			if ("".equals(pettyCashForm.getFilterEndDate())) {
				paramMap.put("endDate", null);
				paramMap.put("startDate", null);
				parameters.put("endDate", "All");
				parameters.put("startDate", "All");
			} else {
				cal.setTime(showDateFormat.parse(pettyCashForm
						.getFilterEndDate()));
				paramMap.put("endDate", dateFormat.format(cal.getTime()));
				parameters.put("endDate", dateFormat.format(cal.getTime()));
				pettyCashForm.setFilterEndDate(showDateFormat.format(cal
						.getTime()));
				cal.setTime(showDateFormat.parse(pettyCashForm
						.getFilterStartDate()));
				paramMap.put("startDate", dateFormat.format(cal.getTime()));
				parameters.put("startDate", dateFormat.format(cal.getTime()));
				pettyCashForm.setFilterStartDate(showDateFormat.format(cal
						.getTime()));
			}
			// get petty cash data based on filter field
			List cashInBankData = pettyCashManager.getAllWithFilter(paramMap);
			// export to pdf
			String filePath = this.getServlet().getServletContext()
					.getRealPath("/asset/");
			parameters.put("filePath", filePath);
			cal = Calendar.getInstance();
			SimpleDateFormat printDateFormat = new SimpleDateFormat(
					"yyyyMMddhhmmss");
			String fileName = "PettyCashReport_"
					+ printDateFormat.format(cal.getTime()) + ".pdf";
			String resultServerPath = ExportReportManager.exportToPdf(filePath
					+ "\\report\\FinanceTransactionReport" + ".jrxml",
					fileName, parameters, cashInBankData);

			ExportReportManager.downloadFile(response, resultServerPath, fileName);
			
			return null;
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
