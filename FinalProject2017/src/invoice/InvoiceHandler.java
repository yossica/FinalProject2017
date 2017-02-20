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
import utils.ExportReportManager;
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
			paramMap.put("periodMonth", invoiceForm.getInvoiceBean().getPeriodMonth());
			paramMap.put("periodYear", invoiceForm.getInvoiceBean().getPeriodYear());
			if(invoiceManager.checkInvoice(paramMap) != 0){
				//client sudah punya invoice professional service di period tsb
				//balikin ke create invoice + message ilst dikasi
				invoiceForm.getMessageList().clear();
				invoiceForm.getMessageList().add("Invoice already created");
				return mapping.findForward("createInvoice");
			} else{
				if (outsourceManager.checkContract(paramMap) != 0) {
					//cek ada kontrak apa ga
					invoiceForm.getInvoiceBean().setClientName(clientManager.getById(invoiceForm.getInvoiceBean().getClientId()).getName());
					invoiceForm.getInvoiceBean().setInvoiceTypeName(masterManager.getInvoiceTypeById(invoiceForm.getInvoiceBean().getInvoiceTypeId()).getName());
					invoiceForm.getInvoiceBean().setNotes(invoiceForm.getInvoiceBean().getNotes());
					invoiceForm.setOutsourceList(outsourceManager.getOutsourceContract(paramMap));
					List<OutsourceBean> bean = new ArrayList<OutsourceBean>();
					bean = outsourceManager.getOutsourceContract(paramMap);
					invoiceForm.getInvoiceBean().setIsGross(bean.get(0).getIsGross());
					InvoiceDetailBean invoiceDetailBean;
					for (OutsourceBean temp : bean) {
						invoiceDetailBean = new InvoiceDetailBean();
						invoiceDetailBean.setEmployeeName(temp.getEmployeeName());
						invoiceDetailBean.setEmployeeId(temp.getEmployeeId());
						invoiceDetailBean.setFee(temp.getFee());
						invoiceDetailBean.setWorkDays(holidayManager.getWorkingDays(exampleDate));
						invoiceForm.getProfessionalServiceList().add(invoiceDetailBean);
					}
					invoiceForm.getInvoiceBean().setDetailSize(String.valueOf(invoiceForm.getProfessionalServiceList().size()));
					return mapping.findForward("createInvoicePS");
				}else {
					invoiceForm.getMessageList().clear();
					invoiceForm.getMessageList().add("There's no contract!");
					return mapping.findForward("createInvoice");
				}
			}
		} else if ("detailInvoice".equals(invoiceForm.getTask())) {
			invoiceForm.setInvoiceBean(invoiceManager.getHeaderById(invoiceForm.getTransactionInvoiceHeaderId()));
			invoiceForm.setClientBean(clientManager.getById(Integer.parseInt(invoiceForm.getClient())));
			invoiceForm.setInvoiceDetailList(invoiceManager.getDetailById(invoiceForm.getTransactionInvoiceHeaderId()));
			invoiceForm.setNote(generalInformationManager.getByKey("rek_no"));
			invoiceForm.setSign(generalInformationManager.getByKey("sign"));
			return mapping.findForward("detailInvoice");
		}else if("insertTransactionOutsource".equals(invoiceForm.getTask())){
			NumberFormat numberFormat = NumberFormat.getInstance(Locale.FRANCE);
			DecimalFormat doubleFormat = new DecimalFormat(".##");
			DateFormat dateFormat = new SimpleDateFormat("MM.yy");
			Date date = new Date();
			//generate workdays
			String exampleDate = invoiceForm.getInvoiceBean().getPeriodMonth()
					+ "/01/" + invoiceForm.getInvoiceBean().getPeriodYear();
			Integer workDays=holidayManager.getWorkingDays(exampleDate);
			invoiceForm.getInvoiceBean().setInvoiceNumber(invoiceManager.getInvoiceNumber(dateFormat.format(date)));
			invoiceForm.getInvoiceBean().setStatusInvoiceId(1);
			float ppn = Float.parseFloat(generalInformationManager.getByKey("tax").getValue());
			invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			double netTotal = 0;
			if (invoiceForm.getInvoiceBean().getIsGross() == 0){
				//Ini kalau exclude PPN
				for (InvoiceDetailBean bean : invoiceForm.getProfessionalServiceList()){
					bean.setCreatedBy((String)session.getAttribute("username"));
					String description = "Jasa Professional Service - "+bean.getEmployeeName()+" "+ bean.getManDays() + " hari";
					int manDays = bean.getManDays();
					double fee = bean.getFee();
					double totalFee = fee * manDays / workDays;
					netTotal += totalFee;
					bean.setUnitPrice(bean.getFee());
					bean.setTotalFee(totalFee);
					bean.setWorkDays(workDays);
					bean.setDescription(description);
					invoiceForm.getInvoiceBean().getDetailList().add(bean);
				}
				double formula = netTotal+(netTotal*ppn/100);
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(formula);
				invoiceForm.getInvoiceBean().setTotalPpn(formula-netTotal);
				invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			}else if (invoiceForm.getInvoiceBean().getIsGross() == 1){
				//Ini kalau include PPN
				double devider = 100+ppn;
				double netFee;
				double grossTotal = 0;
				for (InvoiceDetailBean bean : invoiceForm.getProfessionalServiceList()){
					bean.setCreatedBy((String)session.getAttribute("username"));
					int manDays = bean.getManDays();
					double fee = bean.getFee() * 100 / devider;
					double totalFee = fee * manDays / workDays;
					double totalGross = bean.getFee() * manDays / workDays;
					String description = "Jasa Professional Service - "+bean.getEmployeeName()+" "+ bean.getManDays() + " hari";
					netTotal += totalFee;
					grossTotal += totalGross;
					bean.setUnitPrice(numberFormat.parse(doubleFormat.format(fee)).doubleValue());
					bean.setTotalFee(numberFormat.parse(doubleFormat.format(fee)).doubleValue());
					bean.setWorkDays(workDays);
					bean.setDescription(description);
					invoiceForm.getInvoiceBean().getDetailList().add(bean);
				}
				double ppnValue = grossTotal - netTotal;
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(grossTotal);
				invoiceForm.getInvoiceBean().setTotalPpn(ppnValue);
				invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			}
			invoiceForm.getInvoiceBean().setCreatedBy((String)session.getAttribute("username"));
			//invoiceForm.print();
			invoiceManager.insert(invoiceForm.getInvoiceBean());
			return  mapping.findForward("createInvoicePS");
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
		} else if ("export".equals(invoiceForm.getTask())) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("name", generalInformationManager.getByKey("name").getValue());
			parameters.put("address", generalInformationManager.getByKey("addr").getValue()+
					"\nPhone:"+generalInformationManager.getByKey("telp").getValue()+
					"\nFax: "+generalInformationManager.getByKey("fax").getValue());
			
			String client = invoiceForm.getClientId();
			String monthFrom = invoiceForm.getMonthFrom();
			String yearFrom = invoiceForm.getYearFrom();
			String monthTo = invoiceForm.getMonthTo();
			String yearTo = invoiceForm.getYearTo();
			String status = invoiceForm.getStatusInvoiceId();
			if ("".equals(client)) {
				client = null;
				parameters.put("client", "All");
			}
			else{
				parameters.put("client", new ClientManager().getById(Integer.parseInt(client)).getName());
			}
			if ("".equals(status)) {
				status = null;
				parameters.put("invoiceStatus", "All");
			}
			else {
				parameters.put("invoiceStatus", new MasterManager().getStatusInvoiceById(Integer.parseInt(status)).getName());
			}
			if ("".equals(yearTo)) {
				monthFrom = null;
				yearFrom = null;
				monthTo = null;
				yearTo = null;
				parameters.put("startDate", "All");
				parameters.put("endDate", "All");
			}
			else {
				parameters.put("startDate", getMonthName(monthFrom)+" "+yearFrom);
				parameters.put("endDate", getMonthName(monthTo)+" "+yearTo);
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
			
			List<InvoiceBean> invoiceSummaryData = invoiceManager
					.getAllWithFilter(paramMap);			
			//export to pdf
			String filePath = this.getServlet().getServletContext().getRealPath("/asset/");
			parameters.put("filePath", filePath);
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat printDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
			String fileName = "InvoiceSummaryReport_"+printDateFormat.format(cal.getTime())+".pdf";
			ExportReportManager.exportToPdf(filePath+"\\report\\InvoiceSummaryReport"+".jrxml",
					fileName, parameters, invoiceSummaryData);
			
			invoiceForm.setInvoiceList(invoiceSummaryData);
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
	
	private String getMonthName(String input){
		String result = "";
		Integer month = Integer.parseInt(input);
		switch(month){
			case 1: result = "January"; break;
			case 2 :result="February"; break;
	        case 3 :result="March"; break;
	        case 4 :result="April"; break;
	        case 5 :result="May"; break;
	        case 6 :result="June"; break;
	        case 7 :result="July"; break;
	        case 8 :result="August"; break;
	        case 9 :result="September"; break;
	        case 10:result="October"; break;
	        case 11:result="November"; break;
	        case 12:result="December"; break;
		}
		return result;
	}
}
