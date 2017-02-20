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
import training.TrainingBean;
import training.TrainingDetailBean;
import training.TrainingManager;
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
		TrainingManager trainingManager = new TrainingManager();
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
		} else if ("createInvoiceTRDP".equals(invoiceForm.getTask())) {			
			invoiceForm.getInvoiceBean().setClientName(clientManager.getById(invoiceForm.getInvoiceBean().getClientId()).getName());
			invoiceForm.getInvoiceBean().setInvoiceTypeName(masterManager.getInvoiceTypeById(invoiceForm.getInvoiceBean().getInvoiceTypeId()).getName());
			return mapping.findForward("createInvoiceTRDP");
		} else if ("insertTRDP".equals(invoiceForm.getTask())) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat invoiceDateFormat = new SimpleDateFormat("MM.yy");
			Calendar cal = Calendar.getInstance();
			invoiceForm.getInvoiceBean().setInvoiceNumber(invoiceManager.getInvoiceNumber(invoiceDateFormat.format(cal.getTime())));
			invoiceDateFormat = new SimpleDateFormat("MM");
			invoiceForm.getInvoiceBean().setPeriodMonth(Integer.parseInt(invoiceDateFormat.format(cal.getTime())));
			invoiceDateFormat = new SimpleDateFormat("yyyy");
			invoiceForm.getInvoiceBean().setPeriodYear(Integer.parseInt(invoiceDateFormat.format(cal.getTime())));
			
			invoiceForm.getInvoiceBean().setStatusInvoiceId(1);
			float ppn = Float.parseFloat(generalInformationManager.getByKey("tax").getValue());
			invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
						
			//add settlement ke trainingbean
			double trainingFee = invoiceForm.getTrainingFee()/2;
			TrainingBean trainingBean = invoiceForm.getTrainingBean();
			
			TrainingDetailBean trainingDetailBean = new TrainingDetailBean();
			trainingDetailBean.setCreatedBy((String)session.getAttribute("username"));
			trainingDetailBean.setIsSettlement(1);
			trainingDetailBean.setDescription(trainingBean.getDescription()+" - Settlement");
			trainingDetailBean.setFee(trainingFee);
			List<TrainingDetailBean> trainingDetailBeanList = new ArrayList<TrainingDetailBean>();
			trainingDetailBeanList.add(trainingDetailBean);
			
			trainingBean.setIsGross(invoiceForm.getInvoiceBean().getIsGross());
			trainingBean.setSettlementInvoiceId(0);
			trainingBean.setClientId(invoiceForm.getInvoiceBean().getClientId());
			trainingBean.setDpInvoiceId(invoiceManager.getMaxInvoiceHeaderId()+1);
			cal.setTime(showDateFormat.parse(trainingBean.getTrainingStartDate()));
			trainingBean.setTrainingStartDate(dateFormat.format(cal.getTime()));
			cal.setTime(showDateFormat.parse(trainingBean.getTrainingEndDate()));
			trainingBean.setTrainingEndDate(dateFormat.format(cal.getTime()));
			
			trainingBean.setCreatedBy((String)session.getAttribute("username"));
			trainingBean.setDetailList(trainingDetailBeanList);
						
			//insert invoice
			InvoiceDetailBean invoiceDetailBean = new InvoiceDetailBean();
			invoiceDetailBean.setCreatedBy((String)session.getAttribute("username"));
			invoiceDetailBean.setDescription("Training \""+invoiceForm.getTrainingBean().getDescription() + "\" - DP");
			invoiceDetailBean.setNotes(invoiceForm.getInvoiceDetailNotes());
						
			if (invoiceForm.getInvoiceBean().getIsGross() == 0){
				//exclude ppn
				double netTotal = 0;
				netTotal = trainingFee;
				invoiceDetailBean.setFee(trainingFee);
				invoiceDetailBean.setUnitPrice(trainingFee);
				invoiceDetailBean.setTotalFee(trainingFee);
				
				double formula = netTotal+(netTotal*ppn/100);				
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(formula);
				invoiceForm.getInvoiceBean().setTotalPpn(formula-netTotal);
				invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			} else if (invoiceForm.getInvoiceBean().getIsGross() == 1){
				//include ppn
				double divider = 100+ppn;
				double netTotal;
				double grossTotal = 0;
				invoiceDetailBean.setFee(trainingFee);
				netTotal = trainingFee * 100 / divider;
				grossTotal = trainingFee;
				invoiceDetailBean.setUnitPrice(netTotal);
				invoiceDetailBean.setTotalFee(netTotal);
				
				double ppnValue = grossTotal - netTotal;
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(grossTotal);
				invoiceForm.getInvoiceBean().setTotalPpn(ppnValue);
			}

			invoiceForm.getInvoiceBean().getDetailList().add(invoiceDetailBean);
			invoiceForm.getInvoiceBean().setCreatedBy((String)session.getAttribute("username"));
			
			invoiceManager.insert(invoiceForm.getInvoiceBean());
			
			//insert training
			trainingManager.insert(trainingBean);
			
			//setting field untuk view invoice
			invoiceForm.setClientBean(clientManager.getById(invoiceForm.getInvoiceBean().getClientId()));
			invoiceForm.setInvoiceBean(invoiceManager.getHeaderById(invoiceManager.getMaxInvoiceHeaderId()));
			invoiceForm.setInvoiceDetailList(invoiceManager.getDetailById(invoiceManager.getMaxInvoiceHeaderId()));
			invoiceForm.setNote(generalInformationManager.getByKey("rek_no"));
			invoiceForm.setSign(generalInformationManager.getByKey("sign"));
			
			return mapping.findForward("detailInvoice");
		} else if ("createInvoiceTRST".equals(invoiceForm.getTask())) {
			return mapping.findForward("createInvoiceTRST");
		}else if("deleteDetailHH".equals(invoiceForm.getTask())){
			invoiceForm.getHeadHunterList().remove(invoiceForm.getDeleteIndex());
			return mapping.findForward("createInvoiceHH");
		}else if ("insertHH".equals(invoiceForm.getTask())) {
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
