package invoice;

import holiday.HolidayManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import master.MasterManager;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import outsource.OutsourceBean;
import outsource.OutsourceManager;
import client.ClientManager;
import employee.EmployeeBean;

public class InvoiceHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InvoiceForm invoiceForm = (InvoiceForm) form;
		InvoiceManager invoiceManager = new InvoiceManager();
		ClientManager clientManager = new ClientManager();
		MasterManager masterManager = new MasterManager();
		HolidayManager holidayManager = new HolidayManager();
		OutsourceManager outsourceManager = new OutsourceManager();
		invoiceForm.setClientList(clientManager.getAll());
		invoiceForm.setInvoiceTypeList(masterManager.getAllInvoiceType());

		if ("createInvoice".equals(invoiceForm.getTask())) {
			return mapping.findForward("createInvoice");
		}else if ("createInvoicePS".equals(invoiceForm.getTask())) {
			String exampleDate = invoiceForm.getInvoiceBean().getPeriodMonth()
					+ "/01/" + invoiceForm.getInvoiceBean().getPeriodYear();
			Map paramMap = new HashMap();
			paramMap.put("clientId", invoiceForm.getInvoiceBean().getClientId());
			paramMap.put("exampleDate", exampleDate);
			System.out.println(paramMap);
			if (outsourceManager.checkContract(paramMap) != 0) {
				invoiceForm.getInvoiceBean().setClientName(
						clientManager.getById(
								invoiceForm.getInvoiceBean().getClientId())
								.getName());
				invoiceForm.getInvoiceBean().setInvoiceTypeName(
						masterManager
								.getInvoiceTypeById(
										invoiceForm.getInvoiceBean()
												.getInvoiceTypeId()).getName());
				invoiceForm.getInvoiceBean().setNotes(
						invoiceForm.getInvoiceBean().getNotes());
				invoiceForm.setOutsourceList(outsourceManager
						.getOutsourceContract(paramMap));
				List<OutsourceBean> bean = new ArrayList<OutsourceBean>();
				bean = outsourceManager.getOutsourceContract(paramMap);
				InvoiceDetailBean invoiceDetailBean;
				for (OutsourceBean temp : bean) {
					invoiceDetailBean = new InvoiceDetailBean();
					invoiceDetailBean.setEmployeeName(temp.getEmployeeName());
					invoiceDetailBean.setFee(temp.getFee());
					invoiceDetailBean.setWorkDays(holidayManager
							.getWorkingDays(exampleDate));
					invoiceForm.getInvoiceDetailList().add(invoiceDetailBean);
				}
				return mapping.findForward("createInvoicePS");
			}else {
				return mapping.findForward("createInvoice");
			}
		} else if ("detailInvoice".equals(invoiceForm.getTask())) {
			return mapping.findForward("detailInvoice");
		} else if ("createInvoiceHH".equals(invoiceForm.getTask())) {
			invoiceForm.getInvoiceBean().setClientName(
					clientManager.getById(
							invoiceForm.getInvoiceBean().getClientId())
							.getName());
			invoiceForm.getInvoiceBean().setInvoiceTypeName(
					masterManager.getInvoiceTypeById(
							invoiceForm.getInvoiceBean().getInvoiceTypeId())
							.getName());
			DateFormat dateFormatMonth = new SimpleDateFormat("MM");
			DateFormat dateFormatYear = new SimpleDateFormat("yyyy");
			Date date = new Date();
			Integer month = Integer.parseInt(dateFormatMonth.format(date));
			Integer year = Integer.parseInt(dateFormatYear.format(date));
			invoiceForm.getInvoiceBean().setPeriodMonth(month);
			invoiceForm.getInvoiceBean().setPeriodYear(year);
			return mapping.findForward("createInvoiceHH");
		} else if ("addDetailHH".equals(invoiceForm.getTask())) {
			invoiceForm.getHeadHunterList().add(new InvoiceDetailBean());
			return mapping.findForward("createInvoiceHH");
		} else if ("createInvoiceTR".equals(invoiceForm.getTask())) {
			return mapping.findForward("createInvoiceTR");
		} else if ("insert".equals(invoiceForm.getTask())) {
			DateFormat dateFormat = new SimpleDateFormat("MM.yyyy");
			Date date = new Date();
			invoiceForm.getInvoiceBean().setTransactionInvoiceHeaderId(
					invoiceManager.getMaxInvoiceHeaderId());
			// System.out.println(invoiceManager.getMaxInvoiceHeaderId());
			invoiceForm.getInvoiceBean().setInvoiceNumber(
					invoiceManager.getInvoiceNumber(dateFormat.format(date)));
			// System.out.println(invoiceManager.getInvoiceNumber(dateFormat.format(date)));
			return mapping.findForward("createInvoiceHH");
		} else if ("changeStatus".equals(invoiceForm.getTask())) {
			String invoiceNumber = invoiceForm.getInvoiceNumber();
			Integer nextStatusId = Integer.parseInt(masterManager.getNextStatus(invoiceForm.getStatusId()));
			Map paramMap = new HashMap();
			paramMap.put("invoiceNumber", invoiceNumber);
			paramMap.put("nextStatusId", nextStatusId);
			masterManager.setNextStatus(paramMap);
			String client = invoiceForm.getClientId();
			String monthFrom = invoiceForm.getMonthFrom();
			String yearFrom = invoiceForm.getYearFrom();
			String monthTo = invoiceForm.getMonthTo();
			String yearTo = invoiceForm.getYearTo();
			String status = invoiceForm.getStatusInvoiceId();
			if ("".equals(client)) {
				client = null;
			}
			if ("".equals(status)) {
				status = null;
			}
			if ("".equals(yearTo)) {
				monthFrom = null;
				yearFrom = null;
				monthTo = null;
				yearTo = null;
			}
			paramMap = new HashMap();
			paramMap.put("monthFrom", monthFrom);
			paramMap.put("yearFrom", yearFrom);
			paramMap.put("monthTo", monthTo);
			paramMap.put("yearTo", yearTo);
			paramMap.put("client", client);
			paramMap.put("status", status);
			invoiceForm.setStatusInvoiceList(masterManager
					.getAllStatusInvoice());
			invoiceForm.setInvoiceList(invoiceManager
					.getAllWithFilter(paramMap));
			return mapping.findForward("invoice");
		} else if ("filter".equals(invoiceForm.getTask())) {
			String client = invoiceForm.getClientId();
			String monthFrom = invoiceForm.getMonthFrom();
			String yearFrom = invoiceForm.getYearFrom();
			String monthTo = invoiceForm.getMonthTo();
			String yearTo = invoiceForm.getYearTo();
			String status = invoiceForm.getStatusInvoiceId();
			if ("".equals(client)) {
				client = null;
			}
			if ("".equals(status)) {
				status = null;
			}
			if ("".equals(yearTo)) {
				monthFrom = null;
				yearFrom = null;
				monthTo = null;
				yearTo = null;
			}
			Map paramMap = new HashMap();
			paramMap.put("monthFrom", monthFrom);
			paramMap.put("yearFrom", yearFrom);
			paramMap.put("monthTo", monthTo);
			paramMap.put("yearTo", yearTo);
			paramMap.put("client", client);
			paramMap.put("status", status);
			invoiceForm.setStatusInvoiceList(masterManager
					.getAllStatusInvoice());
			invoiceForm.setInvoiceList(invoiceManager
					.getAllWithFilter(paramMap));
			return mapping.findForward("invoice");
		} else {
			Calendar cc = Calendar.getInstance();
			int cyear = cc.get(Calendar.YEAR);
			int cmonth = cc.get(Calendar.MONTH) + 1;
			int pyear = cc.get(Calendar.YEAR);
			int pmonth = cc.get(Calendar.MONTH);
			if (pmonth == 0) {
				pmonth = 12;
				pyear--;
			}
			invoiceForm.setMonthFrom(pmonth + "");
			invoiceForm.setYearFrom(pyear + "");
			invoiceForm.setMonthTo(cmonth + "");
			invoiceForm.setYearTo(cyear + "");
			Map paramMap = new HashMap();
			paramMap.put("monthFrom", pmonth);
			paramMap.put("yearFrom", pyear);
			paramMap.put("monthTo", cmonth);
			paramMap.put("yearTo", cyear);
			paramMap.put("client", null);
			paramMap.put("status", null);
			invoiceForm.setStatusInvoiceList(masterManager
					.getAllStatusInvoice());
			invoiceForm.setInvoiceList(invoiceManager
					.getAllWithFilter(paramMap));
			return mapping.findForward("invoice");
		}
	}
}
