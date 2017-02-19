package invoice;

import generalInformation.GeneralInformationManager;
import holiday.HolidayManager;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import client.ClientManager;
import employee.EmployeeBean;

public class InvoiceHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();

		if (session.getAttribute("username") == null) {
			return mapping.findForward("login");
		}
		InvoiceForm invoiceForm = (InvoiceForm) form;
		InvoiceManager invoiceManager = new InvoiceManager();
		ClientManager clientManager = new ClientManager();
		MasterManager masterManager = new MasterManager();
		HolidayManager holidayManager = new HolidayManager();
		OutsourceManager outsourceManager = new OutsourceManager();
		GeneralInformationManager generalInformationManager =  new GeneralInformationManager();
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
				invoiceForm.getInvoiceBean().setClientName(clientManager.getById(invoiceForm.getInvoiceBean().getClientId()).getName());
				invoiceForm.getInvoiceBean().setInvoiceTypeName(masterManager.getInvoiceTypeById(invoiceForm.getInvoiceBean().getInvoiceTypeId()).getName());
				invoiceForm.getInvoiceBean().setNotes(invoiceForm.getInvoiceBean().getNotes());
				invoiceForm.setOutsourceList(outsourceManager.getOutsourceContract(paramMap));
				List<OutsourceBean> bean = new ArrayList<OutsourceBean>();
				bean = outsourceManager.getOutsourceContract(paramMap);
				InvoiceDetailBean invoiceDetailBean;
				for (OutsourceBean temp : bean) {
					invoiceDetailBean = new InvoiceDetailBean();
					invoiceDetailBean.setEmployeeName(temp.getEmployeeName());
					invoiceDetailBean.setFee(temp.getFee());
					invoiceDetailBean.setWorkDays(holidayManager.getWorkingDays(exampleDate));
					invoiceForm.getProfessionalServiceList().add(invoiceDetailBean);
					System.out.println(invoiceForm.getProfessionalServiceList());
				}
				
				return mapping.findForward("createInvoicePS");
			}else {
				invoiceForm.getMessageList().clear();
				invoiceForm.getMessageList().add("There's no contract!");
				return mapping.findForward("createInvoice");
			}
		} else if ("detailInvoice".equals(invoiceForm.getTask())) {
			invoiceForm.setInvoiceBean(invoiceManager.getHeaderById(invoiceForm.getTransactionInvoiceHeaderId()));
			invoiceForm.setClientBean(clientManager.getById(Integer.parseInt(invoiceForm.getClient())));
			invoiceForm.setInvoiceDetailList(invoiceManager.getDetailById(invoiceForm.getTransactionInvoiceHeaderId()));
			invoiceForm.setNote(generalInformationManager.getByKey("rek_no"));
			invoiceForm.setSign(generalInformationManager.getByKey("sign"));
			return mapping.findForward("detailInvoice");
		}else if("insertTransactionOutsource".equals(invoiceForm.getTask())){
			DateFormat dateFormat = new SimpleDateFormat("MM.yyyy");
			Date date = new Date();
			invoiceForm.getInvoiceBean().setTransactionInvoiceHeaderId(
					invoiceManager.getMaxInvoiceHeaderId());
			invoiceForm.getInvoiceBean().setInvoiceNumber(
					invoiceManager.getInvoiceNumber(dateFormat.format(date)));
			return null;
		}	else if ("createInvoiceHH".equals(invoiceForm.getTask())) {
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
		} else if ("insertHH".equals(invoiceForm.getTask())) {
			DateFormat dateFormat = new SimpleDateFormat("MM.yy");
			Date date = new Date();
			invoiceForm.getInvoiceBean().setInvoiceNumber(invoiceManager.getInvoiceNumber(dateFormat.format(date)));
			invoiceForm.getInvoiceBean().setStatusInvoiceId(1);
			float ppn = Float.parseFloat(generalInformationManager.getByKey("tax").getValue());
			invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			double netTotal = 0;
			if (invoiceForm.getInvoiceBean().getIsGross() == 0){
				//Ini kalau exclude PPN
				for (InvoiceDetailBean bean : invoiceForm.getHeadHunterList()){
					bean.setCreatedBy((String)session.getAttribute("username"));
					netTotal += bean.getFee();
					bean.setUnitPrice(bean.getFee());
					bean.setTotalFee(bean.getFee());
					invoiceForm.getInvoiceBean().getDetailList().add(bean);
				}
				double formula = netTotal+(netTotal*ppn/100);
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(formula);
				invoiceForm.getInvoiceBean().setTotalPpn(formula-netTotal);
				invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			}else if (invoiceForm.getInvoiceBean().getIsGross() == 1){
				//Ini kalau include PPN
				NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRANCE);
				DecimalFormat doubleFormat = new DecimalFormat(".##");
				double devider = 100+ppn;
				double netFee;
				double grossTotal = 0;
				for (InvoiceDetailBean bean : invoiceForm.getHeadHunterList()){
					bean.setCreatedBy((String)session.getAttribute("username"));
					netFee = bean.getFee() * 100 / devider;
					netTotal += netFee;
					grossTotal += bean.getFee();
					bean.setUnitPrice(numberFormat.parse(doubleFormat.format(netFee)).doubleValue());
					bean.setTotalFee(numberFormat.parse(doubleFormat.format(netFee)).doubleValue());
					invoiceForm.getInvoiceBean().getDetailList().add(bean);
				}
				double ppnValue = grossTotal - netTotal;
				netTotal = numberFormat.parse(doubleFormat.format(netTotal)).doubleValue();
				ppnValue = numberFormat.parse(doubleFormat.format(ppnValue)).doubleValue();
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(grossTotal);
				invoiceForm.getInvoiceBean().setTotalPpn(ppnValue);
			}
			invoiceForm.getInvoiceBean().setCreatedBy((String)session.getAttribute("username"));
			//invoiceForm.print();
			invoiceManager.insert(invoiceForm.getInvoiceBean());
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
