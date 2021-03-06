package outsource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import client.ClientManager;
import employee.EmployeeManager;

public class OutsourceHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();

		OutsourceForm outsourceForm = (OutsourceForm) form;
		OutsourceManager outsourceManager = new OutsourceManager();
		ClientManager clientManager = new ClientManager();
		EmployeeManager employeeManager = new EmployeeManager();
		int flagError = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		String message = "";

		if ("create".equals(outsourceForm.getTask())) {
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			outsourceForm.setOptEmployeeList(employeeManager.getAllEnabled());

			outsourceForm.setTask("save" + outsourceForm.getTask());
			return mapping.findForward("formOutsource");
		} else if ("update".equals(outsourceForm.getTask())) {

			outsourceForm.setOutsourceBean(outsourceManager
					.getById(outsourceForm.getTransactionOutsourceId()));

			outsourceForm.setTask("save" + outsourceForm.getTask());
			return mapping.findForward("formOutsource");
		} else if ("mutation".equals(outsourceForm.getTask())) {
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			outsourceForm.setOptEmployeeList(employeeManager.getAllEnabled());

			OutsourceBean outsourceBean = outsourceManager
					.getById(outsourceForm.getTransactionOutsourceId());

			outsourceForm.setOutsourceBean(outsourceBean);

			// date format show
			cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getStartDate()));
			outsourceForm.getOutsourceBean().setStartDate(
					showDateFormat.format(cal.getTime()));
			cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getEndDate()));
			outsourceForm.getOutsourceBean().setEndDate(
					showDateFormat.format(cal.getTime()));

			outsourceForm.setTask("save" + outsourceForm.getTask());
			return mapping.findForward("formOutsource");
		} else if ("end".equals(outsourceForm.getTask())) {

			OutsourceBean outsourceBean = outsourceManager
					.getById(outsourceForm.getTransactionOutsourceId());

			outsourceForm.setOutsourceBean(outsourceBean);

			// date format data
			cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getStartDate()));
			outsourceForm.getOutsourceBean().setStartDate(
					showDateFormat.format(cal.getTime()));
			cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getEndDate()));
			outsourceForm.getOutsourceBean().setEndDate(
					showDateFormat.format(cal.getTime()));

			outsourceForm.setTask("save" + outsourceForm.getTask());
			return mapping.findForward("formOutsource");
		} else if ("savecreate".equals(outsourceForm.getTask())) {
			outsourceForm.getOutsourceBean().setCreatedBy(
					(String) session.getAttribute("username"));

			// date format data
			cal.setTime(showDateFormat.parse(outsourceForm.getOutsourceBean()
					.getStartDate()));
			outsourceForm.getOutsourceBean().setStartDate(
					dateFormat.format(cal.getTime()));
			cal.setTime(showDateFormat.parse(outsourceForm.getOutsourceBean()
					.getEndDate()));
			outsourceForm.getOutsourceBean().setEndDate(
					dateFormat.format(cal.getTime()));

			// validation
			// ada employee di table
			// cek seluruh data input start > end data or input end < start data

			// ambil data dari rentang tahun input start - end
			Calendar cal2 = Calendar.getInstance();
			cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getStartDate()));
			cal2.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getEndDate()));
			Calendar cal3 = Calendar.getInstance();
			Calendar cal4 = Calendar.getInstance();
			for (int i = cal.get(Calendar.YEAR); i <= cal2.get(Calendar.YEAR); i++) {
				Map filter = new HashMap();
				filter.put("employee", outsourceForm.getOutsourceBean()
						.getEmployeeId());
				filter.put("year", i);
				List<OutsourceBean> tmpList = new ArrayList<OutsourceBean>();
				tmpList = outsourceManager.getAllWithFilter(filter);
				for (int j = 0; j < tmpList.size(); j++) {
					cal3.setTime(dateFormat
							.parse(tmpList.get(j).getStartDate()));
					cal4.setTime(dateFormat.parse(tmpList.get(j).getEndDate()));
					if (cal.after(cal4) || cal2.before(cal3)) {

					} else {
						flagError = 1;
						message = message
								+ "Ooooops!!! Employee already with another contract, with "
								+ tmpList.get(j).getClientName()
								+ " in period " + tmpList.get(j).getStartDate()
								+ " until " + tmpList.get(j).getEndDate()
								+ "\n";
						break;
					}
				}
				if (flagError == 1) {
					break;
				}
			}

			// cek ada contract dengan client yang sama atau tidak
			// yang overlap
			// maka is grossnya harus sama
			cal2 = Calendar.getInstance();
			cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getStartDate()));
			cal2.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getEndDate()));
			cal3 = Calendar.getInstance();
			cal4 = Calendar.getInstance();
			Map filter = new HashMap();
			for (int i = cal.get(Calendar.YEAR); i <= cal2.get(Calendar.YEAR); i++) {
				filter = new HashMap();
				filter.put("client", outsourceForm.getOutsourceBean()
						.getClientId());
				filter.put("year", i);
				List<OutsourceBean> tmpList = new ArrayList<OutsourceBean>();
				tmpList = outsourceManager.getAllWithFilter(filter);
				for (int j = 0; j < tmpList.size(); j++) {
					cal3.setTime(dateFormat
							.parse(tmpList.get(j).getStartDate()));
					cal4.setTime(dateFormat.parse(tmpList.get(j).getEndDate()));
					if (cal.after(cal4) || cal2.before(cal3)) {

					} else {
						if (tmpList.get(j).getIsGross() != outsourceForm
								.getOutsourceBean().getIsGross()) {
							flagError = 1;
							if (tmpList.get(j).getIsGross() == 1) {
								message = message
										+ "Ooooops!!! Client already have contract "
										+ "with tax include, new contract "
										+ "must be tax include as well" + "\n";
							} else {
								message = message
										+ "Ooooops!!! Client already have contract "
										+ "with tax exclude, new contract "
										+ "must be tax exclude as well" + "\n";
							}
							break;
						}
					}
				}
				if (flagError == 1) {
					break;
				}
			}

			if (flagError == 1) {
				// date format show
				outsourceForm.getMessageList().add(message);
				cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
						.getStartDate()));
				outsourceForm.getOutsourceBean().setStartDate(
						showDateFormat.format(cal.getTime()));
				cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
						.getEndDate()));
				outsourceForm.getOutsourceBean().setEndDate(
						showDateFormat.format(cal.getTime()));

				outsourceForm.setOptClientList(clientManager.getAllEnabled());
				outsourceForm.setOptEmployeeList(employeeManager
						.getAllEnabled());
				outsourceForm.setTask("savecreate");
				return mapping.findForward("formOutsource");
			} else {
				outsourceManager.insert(outsourceForm.getOutsourceBean());
				outsourceForm
						.getMessageList()
						.add("Success!!! Profesional Service Contract has been Created!");

				outsourceForm.setOptClientList(clientManager.getAllEnabled());
				outsourceForm.setOptEmployeeList(employeeManager
						.getAllEnabled());
				outsourceForm.setFilterMonth("");
				outsourceForm.setFilterYear(String.valueOf(year));

				filter = new HashMap();
				filter.put("year", outsourceForm.getFilterYear());

				outsourceForm.setOutsourceList(outsourceManager
						.getAllWithFilter(filter));
				return mapping.findForward("outsource");
			}
		} else if ("saveupdate".equals(outsourceForm.getTask())) {
			outsourceForm.getOutsourceBean().setChangedBy(
					(String) session.getAttribute("username"));

			OutsourceBean outsourceBean = outsourceManager
					.getById(outsourceForm.getTransactionOutsourceId());

			// validasi apakah gross berubah,
			// maka cek ada contract lain dengan client yang sama pada sysdate
			// apa tidak
			if (outsourceForm.getOutsourceBean().getIsGross() != outsourceBean
					.getIsGross()) {

				// cek ada contract dengan client yang sama atau tidak
				// start date input < end date contract yang sudah ada
				// maka is grossnya harus sama
				Map filter = new HashMap();
				filter.put("client", outsourceForm.getOutsourceBean()
						.getClientId());
				filter.put("gtEndDate", outsourceForm.getOutsourceBean()
						.getStartDate().substring(0, 3)
						+ "01"
						+ outsourceForm.getOutsourceBean().getStartDate()
								.substring(5, 10));
				List<OutsourceBean> tmpList = new ArrayList<OutsourceBean>();
				tmpList = outsourceManager.getAllWithFilter(filter);
				if (tmpList.size() > 0) {
					if ((tmpList.get(0).getIsGross() != outsourceForm
							.getOutsourceBean().getIsGross())
							&& tmpList.get(0).getTransactionOutsourceId() != outsourceForm
									.getTransactionOutsourceId()) {
						flagError = 1;
						if (tmpList.get(0).getIsGross() == 1) {
							message = message
									+ "Ooooops!!! Client already have contract "
									+ "with tax include, new contract "
									+ "must be tax include as well" + "\n";
						} else {
							message = message
									+ "Ooooops!!! Client already have contract "
									+ "with tax exclude, new contract "
									+ "must be tax exclude as well" + "\n";
						}

					}
				}
			}
			if (flagError == 1) {
				outsourceForm.getMessageList().add(message);
				outsourceForm.setTask("saveupdate");
				return mapping.findForward("formOutsource");
			} else {
				outsourceManager.update(outsourceForm.getOutsourceBean());
				outsourceForm
						.getMessageList()
						.add("Success!!! Profesional Service Contract has been Updated!");

				outsourceForm.setOptClientList(clientManager.getAllEnabled());
				outsourceForm.setOptEmployeeList(employeeManager
						.getAllEnabled());
				outsourceForm.setFilterMonth("");
				outsourceForm.setFilterYear(String.valueOf(year));

				Map filter = new HashMap();
				filter.put("year", outsourceForm.getFilterYear());

				outsourceForm.setOutsourceList(outsourceManager
						.getAllWithFilter(filter));

				return mapping.findForward("outsource");
			}
		} else if ("savemutation".equals(outsourceForm.getTask())) {

			// date format data
			cal.setTime(showDateFormat.parse(outsourceForm.getOutsourceBean()
					.getStartDate()));
			outsourceForm.getOutsourceBean().setStartDate(
					dateFormat.format(cal.getTime()));
			cal.setTime(showDateFormat.parse(outsourceForm.getOutsourceBean()
					.getEndDate()));
			outsourceForm.getOutsourceBean().setEndDate(
					dateFormat.format(cal.getTime()));

			// ambil data lama
			OutsourceBean outsourceBean = outsourceManager
					.getById(outsourceForm.getOutsourceBean()
							.getTransactionOutsourceId());
			Calendar cal2 = Calendar.getInstance();
			// validation
			// client baru tidak boleh sama dengan client lama
			if (outsourceForm.getOutsourceBean().getClientId() == outsourceBean
					.getClientId()) {
				flagError = 1;
				message = message
						+ "Ooooops!!! Client must be different from previous client"
						+ "\n";
			}

			cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getStartDate()));
			cal2.setTime(dateFormat.parse(outsourceBean.getStartDate()));

			// input start date > old start date, jika tidak maka error
			if (cal.compareTo(cal2) <= 0) {

				message = message
						+ "Ooooops!!! Start date must be later than previous start date ["
						+ outsourceBean.getStartDate() + "] \n";
				flagError = 1;
			}
			// input start date < old end date, jika tidak maka error
			cal2.setTime(dateFormat.parse(outsourceBean.getEndDate()));
			if (cal.compareTo(cal2) >= 0) {
				message = message
						+ "Ooooops!!! Start date must be before than previous end date ["
						+ outsourceBean.getEndDate() + "] \n";
				flagError = 1;
			}
			// cek data yang baru di input ada jadwal yang bentrok apa nggak
			// kecuali transaksi id yang di mutasi
			cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getStartDate()));
			cal2.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getEndDate()));
			Calendar cal3 = Calendar.getInstance();
			Calendar cal4 = Calendar.getInstance();
			Map filter = new HashMap();
			for (int i = cal.get(Calendar.YEAR); i <= cal2.get(Calendar.YEAR); i++) {
				filter = new HashMap();
				filter.put("employee", outsourceForm.getOutsourceBean()
						.getEmployeeId());
				filter.put("year", i);
				List<OutsourceBean> tmpList = new ArrayList<OutsourceBean>();
				tmpList = outsourceManager.getAllWithFilter(filter);
				for (int j = 0; j < tmpList.size(); j++) {
					if (tmpList.get(j).getTransactionOutsourceId() != outsourceForm
							.getOutsourceBean().getTransactionOutsourceId()) {

						cal3.setTime(dateFormat.parse(tmpList.get(j)
								.getStartDate()));
						cal4.setTime(dateFormat.parse(tmpList.get(j)
								.getEndDate()));
						if (cal.after(cal4) || cal2.before(cal3)) {

						} else {
							flagError = 1;
							message = message
									+ "Ooooops!!! Employee already with another contract, with "
									+ tmpList.get(j).getClientName()
									+ " in period "
									+ tmpList.get(j).getStartDate() + " until "
									+ tmpList.get(j).getEndDate() + "\n";
							break;
						}
					}
				}
				if (flagError == 1) {
					break;
				}
			}

			// cek ada contract dengan client yang sama atau tidak
			// yang overlap
			// maka is grossnya harus sama
			cal2 = Calendar.getInstance();
			cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getStartDate()));
			cal2.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getEndDate()));
			cal3 = Calendar.getInstance();
			cal4 = Calendar.getInstance();
			for (int i = cal.get(Calendar.YEAR); i <= cal2.get(Calendar.YEAR); i++) {
				filter = new HashMap();
				filter.put("client", outsourceForm.getOutsourceBean()
						.getClientId());
				filter.put("year", i);
				List<OutsourceBean> tmpList = new ArrayList<OutsourceBean>();
				tmpList = outsourceManager.getAllWithFilter(filter);
				for (int j = 0; j < tmpList.size(); j++) {
					cal3.setTime(dateFormat
							.parse(tmpList.get(j).getStartDate()));
					cal4.setTime(dateFormat.parse(tmpList.get(j).getEndDate()));
					if (cal.after(cal4) || cal2.before(cal3)) {

					} else {
						if (tmpList.get(j).getIsGross() != outsourceForm
								.getOutsourceBean().getIsGross()) {
							flagError = 1;
							if (tmpList.get(j).getIsGross() == 1) {
								message = message
										+ "Ooooops!!! Client already have contract "
										+ "with tax include, new contract "
										+ "must be tax include as well" + "\n";
							} else {
								message = message
										+ "Ooooops!!! Client already have contract "
										+ "with tax exclude, new contract "
										+ "must be tax exclude as well" + "\n";
							}
							break;
						}
					}
				}
				if (flagError == 1) {
					break;
				}
			}

			if (flagError == 1) {
				outsourceForm.getMessageList().add(message);
				// date format show
				cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
						.getStartDate()));
				outsourceForm.getOutsourceBean().setStartDate(
						showDateFormat.format(cal.getTime()));
				cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
						.getEndDate()));
				outsourceForm.getOutsourceBean().setEndDate(
						showDateFormat.format(cal.getTime()));

				outsourceForm.setOptClientList(clientManager.getAllEnabled());
				outsourceForm.setOptEmployeeList(employeeManager
						.getAllEnabled());
				outsourceForm.setTask("savemutation");
				return mapping.findForward("formOutsource");
			} else {
				// berhasil
				// update data lama
				cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
						.getStartDate()));
				cal.add(Calendar.DATE, -1);
				outsourceBean.setEndDate(dateFormat.format(cal.getTime()));

				outsourceManager.update(outsourceBean);
				// insert data baru
				outsourceManager.insert(outsourceForm.getOutsourceBean());
				outsourceForm.getMessageList().add(
						"Success!!! Employee has been Mutated!");

				// for view data
				outsourceForm.setOptClientList(clientManager.getAllEnabled());
				outsourceForm.setOptEmployeeList(employeeManager
						.getAllEnabled());
				outsourceForm.setFilterMonth("");
				outsourceForm.setFilterYear(String.valueOf(year));

				filter = new HashMap();
				filter.put("year", outsourceForm.getFilterYear());

				outsourceForm.setOutsourceList(outsourceManager
						.getAllWithFilter(filter));

				return mapping.findForward("outsource");
			}
		} else if ("saveend".equals(outsourceForm.getTask())) {
			// date format data
			cal.setTime(showDateFormat.parse(outsourceForm.getOutsourceBean()
					.getStartDate()));
			outsourceForm.getOutsourceBean().setStartDate(
					dateFormat.format(cal.getTime()));
			cal.setTime(showDateFormat.parse(outsourceForm.getOutsourceBean()
					.getEndDate()));
			outsourceForm.getOutsourceBean().setEndDate(
					dateFormat.format(cal.getTime()));

			// ambil data lama
			OutsourceBean outsourceBean = outsourceManager
					.getById(outsourceForm.getOutsourceBean()
							.getTransactionOutsourceId());
			Calendar cal2 = Calendar.getInstance();
			// validation
			// input end date <= old end date
			cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
					.getEndDate()));
			cal2.setTime(dateFormat.parse(outsourceBean.getEndDate()));

			if (cal.after(cal2)) {
				message = message
						+ "Ooooops!!! End date must before than previous end date \n";
				flagError = 1;

			}
			if (flagError == 1) {
				outsourceForm.getMessageList().add(message);
				// date format show
				cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
						.getStartDate()));
				outsourceForm.getOutsourceBean().setStartDate(
						showDateFormat.format(cal.getTime()));
				cal.setTime(dateFormat.parse(outsourceForm.getOutsourceBean()
						.getEndDate()));
				outsourceForm.getOutsourceBean().setEndDate(
						showDateFormat.format(cal.getTime()));

				outsourceForm.setTask("saveend");
				return mapping.findForward("formOutsource");
			} else {
				outsourceManager.update(outsourceForm.getOutsourceBean());
				outsourceForm.getMessageList().add(
						"Success!!! Employee Contract has been Ended!");
				// for view data
				outsourceForm.setOptClientList(clientManager.getAllEnabled());
				outsourceForm.setOptEmployeeList(employeeManager
						.getAllEnabled());
				outsourceForm.setFilterMonth("");
				outsourceForm.setFilterYear(String.valueOf(year));

				Map filter = new HashMap();
				filter.put("date", null);
				filter.put("year", outsourceForm.getFilterYear());

				outsourceForm.setOutsourceList(outsourceManager
						.getAllWithFilter(filter));

				return mapping.findForward("outsource");
			}
		} else {
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			outsourceForm.setOptEmployeeList(employeeManager.getAllEnabled());

			Map filter = new HashMap();

			if ("filter".equals(outsourceForm.getTask())) {
			} else {
				// jika baru pertama kali buka

				outsourceForm.setFilterMonth("");
				outsourceForm.setFilterYear(String.valueOf(year));
			}
			// jika klik tombol filter
			filter.put("client", outsourceForm.getFilterClient());
			filter.put("employee", outsourceForm.getFilterEmployee());
			if (outsourceForm.getFilterYear().isEmpty()) {
				filter.put("date", null);
				filter.put("year", null);
			} else {
				if (outsourceForm.getFilterMonth().isEmpty()) {
					filter.put("date", null);
					filter.put("year", outsourceForm.getFilterYear());
				} else {
					filter.put("date", outsourceForm.getFilterMonth() + "/01/"
							+ outsourceForm.getFilterYear());
					filter.put("year", null);
				}
			}

			outsourceForm.setOutsourceList(outsourceManager
					.getAllWithFilter(filter));

			return mapping.findForward("outsource");
		}
	}
}
