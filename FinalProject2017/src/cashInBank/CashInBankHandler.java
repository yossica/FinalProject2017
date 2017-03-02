package cashInBank;

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

import pettyCash.PettyCashBean;
import pettyCash.PettyCashManager;
import utils.ExportReportManager;

public class CashInBankHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		
		CashInBankForm cashInBankForm = (CashInBankForm) form;
		CashInBankManager cashInBankManager = new CashInBankManager();
		MasterManager masterManager = new MasterManager();
		GeneralInformationManager generalInformationManager = new GeneralInformationManager();

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if ("filter".equals(cashInBankForm.getTask())) {
			cashInBankForm.setRemainingBalance(cashInBankManager
					.getCurrentBalance());

			Map paramMap = new HashMap();

			if ("".equals(cashInBankForm.getFilterEndDate())) {
				paramMap.put("endDate", null);
				paramMap.put("startDate", null);
			} else {
				Calendar cal = Calendar.getInstance();
				cal.setTime(showDateFormat.parse(cashInBankForm
						.getFilterEndDate()));
				paramMap.put("endDate", dateFormat.format(cal.getTime()));
				cashInBankForm.setFilterEndDate(showDateFormat.format(cal
						.getTime()));
				cal.setTime(showDateFormat.parse(cashInBankForm
						.getFilterStartDate()));
				paramMap.put("startDate", dateFormat.format(cal.getTime()));
				cashInBankForm.setFilterStartDate(showDateFormat.format(cal
						.getTime()));
			}
			String cat = cashInBankForm.getCategoryId();
			if ("".equals(cat))
				paramMap.put("category", null);
			else
				paramMap.put("category", cat);
			cashInBankForm.setTransactionList(cashInBankManager
					.getAllWithFilter(paramMap));

			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Cash In Bank");
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
			cashInBankForm.setCashFlowCategoryList(cashFlowCategoryList);
			cashInBankForm.setCategoryId(cashInBankForm.getCategoryId());
			/*
			 * cashInBankForm.getMessageList().add(
			 * "Success!!! Cash In Bank has been balanced!");
			 */
			return mapping.findForward("cashInBank");
		} else if ("balancing".equals(cashInBankForm.getTask())) {
			Calendar cal = Calendar.getInstance();
			// get max cash
			double maxBalance = Double.parseDouble(generalInformationManager
					.getByKey("max_cash").getValue());
			double currBalance = cashInBankManager.getCurrentBalance();
			double amount = maxBalance - currBalance;
			
			//get max petty
			double maxPettyBalance = Double.parseDouble(generalInformationManager
					.getByKey("max_petty").getValue());
			double currPettyBalance = new PettyCashManager().getCurrentBalance();
			double pettyAmount = maxPettyBalance - currPettyBalance;
			
			amount += pettyAmount;
			CashInBankBean cashInBankBean = new CashInBankBean();
			if (amount <= 0) {
				cashInBankBean.setCashFlowCategoryId("1c-bal");
				cashInBankBean.setAmount(Math.abs(amount));
				cashInBankBean.setIsDebit(1);
			} else {
				cashInBankBean.setCashFlowCategoryId("0c-bal");
				cashInBankBean.setAmount(amount);
				cashInBankBean.setIsDebit(0);
			}
			cashInBankBean.setBalance(maxBalance);
			cashInBankBean.setDescription("Balancing Cash in Bank "
					+ dateFormat.format(cal.getTime()));
			cashInBankBean.setTransactionDate(dateFormat.format(cal.getTime()));
			cashInBankBean.setCreatedBy((String) session
					.getAttribute("username"));
			cashInBankManager.insert(cashInBankBean);

			cashInBankForm.setRemainingBalance(cashInBankManager
					.getCurrentBalance());

			Map paramMap = new HashMap();

			paramMap.put("endDate", dateFormat.format(cal.getTime()));
			cashInBankForm
					.setFilterEndDate(showDateFormat.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -30);
			paramMap.put("startDate", dateFormat.format(cal.getTime()));
			cashInBankForm.setFilterStartDate(showDateFormat.format(cal
					.getTime()));
			paramMap.put("category", null);
			cashInBankForm.setTransactionList(cashInBankManager
					.getAllWithFilter(paramMap));

			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Cash In Bank");
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
			cashInBankForm.setCashFlowCategoryList(cashFlowCategoryList);

			return mapping.findForward("cashInBank");
		} else if ("debit".equals(cashInBankForm.getTask())) {
			cashInBankForm.setTask("saveDebit");
			cashInBankForm.getCashInBankBean().setIsDebit(1);

			cashInBankForm.setRemainingBalance(cashInBankManager
					.getCurrentBalance());

			Map paramMap = new HashMap();
			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Cash In Bank");
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
			cashInBankForm.setCashFlowCategoryList(cashFlowCategoryList);

			cashInBankForm.getCashInBankBean().setTransactionDate(
					showDateFormat.format(Calendar.getInstance().getTime()));

			return mapping.findForward("form");
		} else if ("saveDebit".equals(cashInBankForm.getTask())) {
			Calendar cal = Calendar.getInstance();
			// validate, if false, go back to form
			double currBalance = cashInBankManager.getCurrentBalance();
			double max_transaction = Double
					.parseDouble(generalInformationManager.getByKey(
							"max_trans_cash").getValue());
			double min_transaction = Double
					.parseDouble(generalInformationManager.getByKey(
							"max_trans_petty").getValue());
			double amount = cashInBankForm.getCashInBankBean().getAmount();
			boolean flag = true;
			if (currBalance - amount < 0) {
				cashInBankForm.getMessageList().clear();
				cashInBankForm
						.getMessageList()
						.add("Ooooops!!! Cannot create transaction that cost more than remaining balance!");
				flag = false;
			} else if (amount > max_transaction) {
				cashInBankForm.getMessageList().clear();
				cashInBankForm.getMessageList().add(
						"Ooooops!!! Cannot create transaction more than IDR"
								+ max_transaction + "!");
				flag = false;
			} else if (!"1c-cor".equals(cashInBankForm.getCashInBankBean()
					.getCashFlowCategoryId())) {
				if (amount < min_transaction) {
					cashInBankForm.getMessageList().clear();
					cashInBankForm
							.getMessageList()
							.add("Ooooops!!! Create transaction less than IDR"
									+ min_transaction
									+ "!\nPlease do this in petty cash instead");
					flag = false;
				}
			}

			if (!flag) {
				cashInBankForm.setTask("saveDebit");
				cashInBankForm.getCashInBankBean().setIsDebit(1);

				cashInBankForm.setRemainingBalance(cashInBankManager
						.getCurrentBalance());

				Map paramMap = new HashMap();
				paramMap = new HashMap();
				paramMap.put("cashFlowType", "Cash In Bank");
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
				cashInBankForm.setCashFlowCategoryList(cashFlowCategoryList);
				cashInBankForm.getCashInBankBean()
						.setTransactionDate(
								cashInBankForm.getCashInBankBean()
										.getTransactionDate());
				return mapping.findForward("form");
			}
			// save to cash in bank
			CashInBankBean cashInBankBean = cashInBankForm.getCashInBankBean();
			cashInBankBean.setBalance(currBalance
					- cashInBankForm.getCashInBankBean().getAmount());
			cal.setTime(showDateFormat.parse(cashInBankForm.getCashInBankBean()
					.getTransactionDate()));
			cashInBankBean.setTransactionDate(dateFormat.format(cal.getTime()));
			cashInBankBean.setCreatedBy((String) session
					.getAttribute("username"));
			String message = cashInBankManager.insert(cashInBankBean);
			if (message.startsWith("Failed")) {
				cashInBankForm.getMessageList().clear();
				cashInBankForm.getMessageList().add(message);

				cashInBankForm.setTask("saveDebit");
				cashInBankForm.getCashInBankBean().setIsDebit(1);

				cashInBankForm.setRemainingBalance(cashInBankManager
						.getCurrentBalance());

				Map paramMap = new HashMap();
				paramMap = new HashMap();
				paramMap.put("cashFlowType", "Cash In Bank");
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
				cashInBankForm.setCashFlowCategoryList(cashFlowCategoryList);
				cashInBankForm.getCashInBankBean()
						.setTransactionDate(
								cashInBankForm.getCashInBankBean()
										.getTransactionDate());
				return mapping.findForward("form");
			}

			// show cash in bank view
			cashInBankForm.setRemainingBalance(cashInBankManager
					.getCurrentBalance());

			Map paramMap = new HashMap();
			cal = Calendar.getInstance();
			paramMap.put("endDate", dateFormat.format(cal.getTime()));
			cashInBankForm
					.setFilterEndDate(showDateFormat.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -30);
			paramMap.put("startDate", dateFormat.format(cal.getTime()));
			cashInBankForm.setFilterStartDate(showDateFormat.format(cal
					.getTime()));
			paramMap.put("category", null);
			cashInBankForm.setTransactionList(cashInBankManager
					.getAllWithFilter(paramMap));

			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Cash In Bank");
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
			cashInBankForm.setCategoryId("");
			cashInBankForm.setCashFlowCategoryList(cashFlowCategoryList);
			// ini
			cashInBankForm.getMessageList().add(
					"Success!!! Transaction has been added!");
			return mapping.findForward("cashInBank");
		} else if ("credit".equals(cashInBankForm.getTask())) {
			cashInBankForm.setTask("saveCredit");
			cashInBankForm.getCashInBankBean().setIsDebit(0);

			cashInBankForm.setRemainingBalance(cashInBankManager
					.getCurrentBalance());

			Map paramMap = new HashMap();
			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Cash In Bank");
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
			cashInBankForm.setCashFlowCategoryList(cashFlowCategoryList);

			cashInBankForm.getCashInBankBean().setTransactionDate(
					showDateFormat.format(Calendar.getInstance().getTime()));

			return mapping.findForward("form");
		} else if ("saveCredit".equals(cashInBankForm.getTask())) {
			Calendar cal = Calendar.getInstance();
			// validate, if false, go back to form
			double currBalance = cashInBankManager.getCurrentBalance();
			double max_transaction = Double
					.parseDouble(generalInformationManager.getByKey(
							"max_trans_cash").getValue());
			double amount = cashInBankForm.getCashInBankBean().getAmount();
			boolean flag = true;
			if (amount > max_transaction) {
				cashInBankForm.getMessageList().clear();
				cashInBankForm.getMessageList().add(
						"Ooooops!!! Cannot create transaction more than IDR"
								+ max_transaction + "!");
				flag = false;
			}

			if (!flag) {
				cashInBankForm.setTask("saveCredit");
				cashInBankForm.getCashInBankBean().setIsDebit(0);

				cashInBankForm.setRemainingBalance(cashInBankManager
						.getCurrentBalance());

				Map paramMap = new HashMap();
				paramMap = new HashMap();
				paramMap.put("cashFlowType", "Cash In Bank");
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
				cashInBankForm.setCashFlowCategoryList(cashFlowCategoryList);
				cashInBankForm.getCashInBankBean()
						.setTransactionDate(
								cashInBankForm.getCashInBankBean()
										.getTransactionDate());
				return mapping.findForward("form");
			}

			// save to cash in bank
			CashInBankBean cashInBankBean = cashInBankForm.getCashInBankBean();
			cashInBankBean.setBalance(currBalance + amount);
			cal.setTime(showDateFormat.parse(cashInBankForm.getCashInBankBean()
					.getTransactionDate()));
			cashInBankBean.setTransactionDate(dateFormat.format(cal.getTime()));
			cashInBankBean.setCreatedBy((String) session
					.getAttribute("username"));
			cashInBankManager.insert(cashInBankBean);

			// show cash in bank view
			cashInBankForm.setRemainingBalance(cashInBankManager
					.getCurrentBalance());

			Map paramMap = new HashMap();
			cal = Calendar.getInstance();

			paramMap.put("endDate", dateFormat.format(cal.getTime()));
			cashInBankForm
					.setFilterEndDate(showDateFormat.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -30);
			paramMap.put("startDate", dateFormat.format(cal.getTime()));
			cashInBankForm.setFilterStartDate(showDateFormat.format(cal
					.getTime()));
			paramMap.put("category", null);
			cashInBankForm.setTransactionList(cashInBankManager
					.getAllWithFilter(paramMap));

			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Cash In Bank");
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
			cashInBankForm.getMessageList().add(
					"Success!!! Transaction has been added!");
			cashInBankForm.setCategoryId("");
			cashInBankForm.setCashFlowCategoryList(cashFlowCategoryList);

			return mapping.findForward("cashInBank");
		} else if ("transfer".equals(cashInBankForm.getTask())) {
			cashInBankForm.setTask("saveTransfer");
			cashInBankForm.getCashInBankBean().setIsDebit(-1);
			cashInBankForm.setRemainingBalance(cashInBankManager
					.getCurrentBalance());
			cashInBankForm.setPettyCashBalance(new PettyCashManager()
					.getCurrentBalance());

			cashInBankForm.getCashInBankBean().setTransactionDate(
					showDateFormat.format(Calendar.getInstance().getTime()));

			return mapping.findForward("form");
		} else if ("saveTransfer".equals(cashInBankForm.getTask())) {
			PettyCashManager pettyCashManager = new PettyCashManager();
			Calendar cal = Calendar.getInstance();
			// validate, if false, go back to form
			double currCashBalance = cashInBankManager.getCurrentBalance();
			double currPettyBalance = pettyCashManager.getCurrentBalance();
			double maxPetty = Double.parseDouble(generalInformationManager
					.getByKey("max_petty").getValue());
			double amount = cashInBankForm.getCashInBankBean().getAmount();
			boolean flag = true;
			cashInBankForm.getMessageList().clear();
			if (currCashBalance - amount < 0) {
				// kalau cash in bank tidak cukup
				cashInBankForm
						.getMessageList()
						.add("Ooooops!!! Cannot transfer more than remaining cash in bank balance!");
				flag = false;
			}
			if (currPettyBalance + amount > maxPetty) {
				// kalau petty cash melewati max petty cash balance
				cashInBankForm.getMessageList().add(
						"Ooooops!!! Exceeded max petty cash balance if transferred (Rp."
								+ maxPetty + ")!");
				flag = false;
			}
			if (!flag) {
				cashInBankForm.setTask("saveTransfer");
				cashInBankForm.getCashInBankBean().setIsDebit(-1);
				cashInBankForm.setRemainingBalance(cashInBankManager
						.getCurrentBalance());
				cashInBankForm.setPettyCashBalance(pettyCashManager
						.getCurrentBalance());

				cashInBankForm.getCashInBankBean()
						.setTransactionDate(
								cashInBankForm.getCashInBankBean()
										.getTransactionDate());

				return mapping.findForward("form");
			}
			// save to cash in bank
			CashInBankBean cashInBankBean = cashInBankForm.getCashInBankBean();
			cashInBankBean.setCashFlowCategoryId("1c-trf");
			cashInBankBean.setIsDebit(1);
			cashInBankBean.setBalance(currCashBalance - amount);
			cal.setTime(showDateFormat.parse(cashInBankForm.getCashInBankBean()
					.getTransactionDate()));
			cashInBankBean.setTransactionDate(dateFormat.format(cal.getTime()));
			cashInBankBean.setCreatedBy((String) session
					.getAttribute("username"));
			cashInBankManager.insert(cashInBankBean);

			// save to petty cash
			PettyCashBean pettyCashBean = new PettyCashBean();
			pettyCashBean.setCashFlowCategoryId("0p-trf");
			pettyCashBean.setIsDebit(0);
			pettyCashBean.setAmount(amount);
			pettyCashBean.setBalance(currPettyBalance + amount);
			pettyCashBean.setDescription(cashInBankForm.getCashInBankBean()
					.getDescription());
			pettyCashBean.setTransactionDate(dateFormat.format(cal.getTime()));
			pettyCashBean.setCreatedBy((String) session
					.getAttribute("username"));
			pettyCashManager.insert(pettyCashBean);

			// show cash in bank view
			cashInBankForm.setRemainingBalance(cashInBankManager
					.getCurrentBalance());

			Map paramMap = new HashMap();
			cal = Calendar.getInstance();

			paramMap.put("endDate", dateFormat.format(cal.getTime()));
			cashInBankForm
					.setFilterEndDate(showDateFormat.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -30);
			paramMap.put("startDate", dateFormat.format(cal.getTime()));
			cashInBankForm.setFilterStartDate(showDateFormat.format(cal
					.getTime()));
			paramMap.put("category", null);
			cashInBankForm.setTransactionList(cashInBankManager
					.getAllWithFilter(paramMap));

			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Cash In Bank");
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
			cashInBankForm.setCategoryId("");
			cashInBankForm.setCashFlowCategoryList(cashFlowCategoryList);
			cashInBankForm.getMessageList().add(
					"Success!!! Transaction has been added!");
			return mapping.findForward("cashInBank");
		} else if ("export".equals(cashInBankForm.getTask())) {
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
			parameters.put("reportType", "Cash in Bank");
			String cat = cashInBankForm.getCategoryId();
			if ("".equals(cat)) {
				paramMap.put("category", null);
				parameters.put("category", "All");
			} else {
				paramMap.put("category", cat);
				parameters.put("category", masterManager
						.getCashFlowCategoryById(cat).getName());
			}

			if ("".equals(cashInBankForm.getFilterEndDate())) {
				paramMap.put("endDate", null);
				paramMap.put("startDate", null);
				parameters.put("endDate", "All");
				parameters.put("startDate", "All");
			} else {
				cal.setTime(showDateFormat.parse(cashInBankForm
						.getFilterEndDate()));
				paramMap.put("endDate", dateFormat.format(cal.getTime()));
				parameters.put("endDate", dateFormat.format(cal.getTime()));
				cashInBankForm.setFilterEndDate(showDateFormat.format(cal
						.getTime()));
				cal.setTime(showDateFormat.parse(cashInBankForm
						.getFilterStartDate()));
				paramMap.put("startDate", dateFormat.format(cal.getTime()));
				parameters.put("startDate", dateFormat.format(cal.getTime()));
				cashInBankForm.setFilterStartDate(showDateFormat.format(cal
						.getTime()));
			}
			// get cash in bank data based on filter field
			List cashInBankData = cashInBankManager.getAllWithFilter(paramMap);
			// export to pdf
			String filePath = this.getServlet().getServletContext()
					.getRealPath("/asset/");
			parameters.put("filePath", filePath);
			cal = Calendar.getInstance();
			SimpleDateFormat printDateFormat = new SimpleDateFormat(
					"yyyyMMddhhmmss");
			String fileName = "CashInBankReport_"
					+ printDateFormat.format(cal.getTime()) + ".pdf";
			String resultServerPath = ExportReportManager.exportToPdf(filePath
					+ "\\report\\FinanceTransactionReport" + ".jrxml",
					fileName, parameters, cashInBankData);
			
			ExportReportManager.downloadFile(response, resultServerPath, fileName);
			
			return null;
		} else {
			cashInBankForm.setRemainingBalance(cashInBankManager
					.getCurrentBalance());

			Map paramMap = new HashMap();
			Calendar cal = Calendar.getInstance();

			paramMap.put("endDate", dateFormat.format(cal.getTime()));
			cashInBankForm
					.setFilterEndDate(showDateFormat.format(cal.getTime()));
			cal.add(Calendar.DAY_OF_MONTH, -30);
			paramMap.put("startDate", dateFormat.format(cal.getTime()));
			cashInBankForm.setFilterStartDate(showDateFormat.format(cal
					.getTime()));
			paramMap.put("category", null);
			cashInBankForm.setTransactionList(cashInBankManager
					.getAllWithFilter(paramMap));

			paramMap = new HashMap();
			paramMap.put("cashFlowType", "Cash In Bank");
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
			cashInBankForm.setCategoryId("");
			cashInBankForm.setCashFlowCategoryList(cashFlowCategoryList);

			return mapping.findForward("cashInBank");
		}
	}
}
