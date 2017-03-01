package invoice;

import generalInformation.GeneralInformationBean;
import generalInformation.GeneralInformationManager;
import holiday.HolidayManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
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
import client.ClientBean;
import client.ClientManager;

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
		GeneralInformationManager generalInformationManager = new GeneralInformationManager();
		invoiceForm.setClientList(clientManager.getAll());
		invoiceForm.setInvoiceTypeList(masterManager.getAllInvoiceType());
		if ("createInvoiceIndex".equals(invoiceForm.getTask())) {
			invoiceForm.getInvoiceBean().setClientId(
					Integer.parseInt(invoiceForm.getClientId()));
			invoiceForm.getInvoiceBean().setInvoiceTypeId(
					Integer.parseInt(invoiceForm.getInvoiceTypeId()));
			invoiceForm.getInvoiceBean().setPeriodMonth(
					Integer.parseInt(invoiceForm.getPeriodMonth()));
			invoiceForm.getInvoiceBean().setPeriodYear(
					Integer.parseInt(invoiceForm.getPeriodYear()));
			return mapping.findForward("createInvoice");
		} else if ("createInvoiceIndexTr".equals(invoiceForm.getTask())) {
			invoiceForm.getInvoiceBean().setClientId(
					Integer.parseInt(invoiceForm.getClientId()));
			invoiceForm.getInvoiceBean().setInvoiceTypeId(
					Integer.parseInt(invoiceForm.getInvoiceTypeId()));
			invoiceForm.getInvoiceBean().setSettlementInvoiceId(
					invoiceForm.getSettlementInvoiceId());
			return mapping.findForward("createInvoice");
		} else if ("createInvoice".equals(invoiceForm.getTask())) {
			return mapping.findForward("createInvoice");
		} else if ("formInvoicePS".equals(invoiceForm.getTask())) {
			// check apakah invoice yang akan dibuat >= bulan ini
			String currentPeriod = outsourceManager.getPeriod();
			if (Integer.parseInt(""
					+ invoiceForm.getInvoiceBean().getPeriodYear()
					+ String.format("%02d", invoiceForm.getInvoiceBean()
							.getPeriodMonth())) > Integer
					.parseInt(currentPeriod)) {
				// invoice yang akan dibuat >= bulan ini (masih berjalan dan ada
				// kemungkinan bertambah)
				invoiceForm.getMessageList().clear();
				invoiceForm
						.getMessageList()
						.add("Ooooops!!! You can only create previous months invoice");
				return mapping.findForward("createInvoice");
			}
			String exampleDate = String.format("%02d", invoiceForm
					.getInvoiceBean().getPeriodMonth())
					+ "/01/" + invoiceForm.getInvoiceBean().getPeriodYear();
			Map paramMap = new HashMap();
			paramMap.put("clientId", invoiceForm.getInvoiceBean().getClientId());
			paramMap.put("exampleDate", exampleDate);
			paramMap.put("periodMonth", invoiceForm.getInvoiceBean()
					.getPeriodMonth());
			paramMap.put("periodYear", invoiceForm.getInvoiceBean()
					.getPeriodYear());
			if (invoiceManager.checkInvoice(paramMap) != 0) {
				// client sudah punya invoice professional service di period tsb
				// balikin ke create invoice + message ilst dikasi
				invoiceForm.getMessageList().clear();
				invoiceForm.getMessageList().add(
						"Ooooops!!! Invoice already created");
				return mapping.findForward("createInvoice");
			} else {
				if (outsourceManager.checkContract(paramMap) != 0) {
					// cek ada kontrak apa ga
					invoiceForm.getInvoiceBean().setClientName(
							clientManager.getById(
									invoiceForm.getInvoiceBean().getClientId())
									.getName());
					invoiceForm.getInvoiceBean().setInvoiceTypeName(
							masterManager.getInvoiceTypeById(
									invoiceForm.getInvoiceBean()
											.getInvoiceTypeId()).getName());
					invoiceForm.getInvoiceBean().setNotes(
							invoiceForm.getInvoiceBean().getNotes());
					invoiceForm.setOutsourceList(outsourceManager
							.getOutsourceContract(paramMap));
					List<OutsourceBean> bean = new ArrayList<OutsourceBean>();
					bean = outsourceManager.getOutsourceContract(paramMap);
					invoiceForm.getInvoiceBean().setIsGross(
							bean.get(0).getIsGross());
					InvoiceDetailBean invoiceDetailBean;
					for (OutsourceBean temp : bean) {
						invoiceDetailBean = new InvoiceDetailBean();
						invoiceDetailBean.setEmployeeName(temp
								.getEmployeeName());
						invoiceDetailBean.setEmployeeId(temp.getEmployeeId());
						invoiceDetailBean.setFee(temp.getFee());
						invoiceDetailBean.setWorkDays(holidayManager
								.getWorkingDays(exampleDate));
						invoiceForm.getProfessionalServiceList().add(
								invoiceDetailBean);
					}
					invoiceForm.getInvoiceBean().setDetailSize(
							String.valueOf(invoiceForm
									.getProfessionalServiceList().size()));
					invoiceForm.setTask("createInvoice");
					return mapping.findForward("formInvoicePS");
				} else {
					invoiceForm.getMessageList().clear();
					invoiceForm.getMessageList().add(
							"Ooooops!!! There's no contract!");
					return mapping.findForward("createInvoice");
				}
			}
		} else if ("detailInvoice".equals(invoiceForm.getTask())) {
			invoiceForm.setInvoiceBean(invoiceManager.getHeaderById(invoiceForm
					.getTransactionInvoiceHeaderId()));
			invoiceForm.setClientBean(clientManager.getById(Integer
					.parseInt(invoiceForm.getClient())));
			invoiceForm
					.setInvoiceDetailList(invoiceManager
							.getDetailById(invoiceForm
									.getTransactionInvoiceHeaderId()));
			invoiceForm.setNote(generalInformationManager.getByKey("rek_no"));
			invoiceForm.setSign(generalInformationManager.getByKey("sign"));
			return mapping.findForward("detailInvoice");
		} else if ("insertPS".equals(invoiceForm.getTask())) {
			DateFormat dateFormat = new SimpleDateFormat("MM.yy");
			Date date = new Date();
			// generate workdays
			String exampleDate = invoiceForm.getInvoiceBean().getPeriodMonth()
					+ "/01/" + invoiceForm.getInvoiceBean().getPeriodYear();
			invoiceForm.getInvoiceBean().setInvoiceNumber(
					invoiceManager.getInvoiceNumber(dateFormat.format(date)));
			invoiceForm.getInvoiceBean().setStatusInvoiceId(1);
			float ppn = Float.parseFloat(generalInformationManager.getByKey(
					"tax").getValue());
			invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			double netTotal = 0;
			if (invoiceForm.getInvoiceBean().getIsGross() == 0) {
				// Ini kalau exclude PPN
				for (InvoiceDetailBean bean : invoiceForm
						.getProfessionalServiceList()) {
					bean.setCreatedBy((String) session.getAttribute("username"));
					String description = "Jasa Professional Service - "
							+ bean.getEmployeeName() + " " + bean.getManDays()
							+ " Work Days";
					Integer workDays = bean.getWorkDays();
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
				double formula = netTotal + (netTotal * ppn / 100);
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(formula);
				invoiceForm.getInvoiceBean().setTotalPpn(formula - netTotal);
				invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			} else if (invoiceForm.getInvoiceBean().getIsGross() == 1) {
				// Ini kalau include PPN
				double divider = 100 + ppn;
				double netFee;
				double grossTotal = 0;
				for (InvoiceDetailBean bean : invoiceForm
						.getProfessionalServiceList()) {
					bean.setCreatedBy((String) session.getAttribute("username"));
					Integer workDays = bean.getWorkDays();
					int manDays = bean.getManDays();
					double fee = bean.getFee() * 100 / divider;
					double totalFee = fee * manDays / workDays;
					double totalGross = bean.getFee() * manDays / workDays;
					String description = "Jasa Professional Service - "
							+ bean.getEmployeeName() + " " + bean.getManDays()
							+ " hari";
					netTotal += totalFee;
					grossTotal += totalGross;
					bean.setUnitPrice(fee);
					bean.setTotalFee(totalFee);
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
			invoiceForm.getInvoiceBean().setCreatedBy(
					(String) session.getAttribute("username"));
			Integer idHeader = invoiceManager.insert(invoiceForm
					.getInvoiceBean());

			// display invoice
			invoiceForm.setClient(String.valueOf(invoiceForm.getInvoiceBean()
					.getClientId()));
			invoiceForm.setTransactionInvoiceHeaderId(idHeader);
			invoiceForm.setStatusId(String.valueOf(invoiceForm.getInvoiceBean()
					.getStatusInvoiceId()));
			invoiceForm.setTask("detailInvoice");
			invoiceForm.setInvoiceBean(invoiceManager.getHeaderById(invoiceForm
					.getTransactionInvoiceHeaderId()));
			invoiceForm.setClientBean(clientManager.getById(Integer
					.parseInt(invoiceForm.getClient())));
			invoiceForm
					.setInvoiceDetailList(invoiceManager
							.getDetailById(invoiceForm
									.getTransactionInvoiceHeaderId()));
			invoiceForm.setNote(generalInformationManager.getByKey("rek_no"));
			invoiceForm.setSign(generalInformationManager.getByKey("sign"));
			invoiceForm.getMessageList().add(
					"Success!!! Invoice Outsource Has Been Created!");
			return mapping.findForward("detailInvoice");
		} else if ("formInvoiceHH".equals(invoiceForm.getTask())) {
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
			invoiceForm.setHeadHunterListSize(invoiceForm.getHeadHunterList()
					.size());
			return mapping.findForward("formInvoiceHH");
		} else if ("addDetailHH".equals(invoiceForm.getTask())) {
			invoiceForm.getHeadHunterList().add(new InvoiceDetailBean());
			invoiceForm.setTask("formInvoiceHH");
			invoiceForm.setHeadHunterListSize(invoiceForm.getHeadHunterList()
					.size());
			return mapping.findForward("formInvoiceHH");
		} else if ("editDetailHH".equals(invoiceForm.getTask())) {
			invoiceForm.getHeadHunterList().add(new InvoiceDetailBean());
			invoiceForm.setTask("editInvoice");
			invoiceForm.setHeadHunterListSize(invoiceForm.getHeadHunterList()
					.size());

			return mapping.findForward("formInvoiceHH");
		} else if ("createInvoiceTRDP".equals(invoiceForm.getTask())) {
			invoiceForm.getInvoiceBean().setClientName(
					clientManager.getById(
							invoiceForm.getInvoiceBean().getClientId())
							.getName());
			invoiceForm.getInvoiceBean().setInvoiceTypeName(
					masterManager.getInvoiceTypeById(
							invoiceForm.getInvoiceBean().getInvoiceTypeId())
							.getName());
			return mapping.findForward("formInvoiceTRDP");
		} else if ("insertTRDP".equals(invoiceForm.getTask())) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat invoiceDateFormat = new SimpleDateFormat("MM.yy");
			Calendar cal = Calendar.getInstance();
			invoiceForm.getInvoiceBean().setInvoiceNumber(
					invoiceManager.getInvoiceNumber(invoiceDateFormat
							.format(cal.getTime())));
			invoiceDateFormat = new SimpleDateFormat("MM");
			invoiceForm.getInvoiceBean().setPeriodMonth(
					Integer.parseInt(invoiceDateFormat.format(cal.getTime())));
			invoiceDateFormat = new SimpleDateFormat("yyyy");
			invoiceForm.getInvoiceBean().setPeriodYear(
					Integer.parseInt(invoiceDateFormat.format(cal.getTime())));

			invoiceForm.getInvoiceBean().setStatusInvoiceId(1);
			float ppn = Float.parseFloat(generalInformationManager.getByKey(
					"tax").getValue());
			invoiceForm.getInvoiceBean().setPpnPercentage(ppn);

			// add settlement ke trainingbean
			double trainingFee = invoiceForm.getTrainingFee() / 2;
			TrainingBean trainingBean = invoiceForm.getTrainingBean();

			TrainingDetailBean trainingDetailBean = new TrainingDetailBean();
			trainingDetailBean.setCreatedBy((String) session
					.getAttribute("username"));
			trainingDetailBean.setIsSettlement(1);
			trainingDetailBean.setDescription("Training \""
					+ trainingBean.getDescription() + "\" - Settlement");
			trainingDetailBean.setFee(trainingFee);
			List<TrainingDetailBean> trainingDetailBeanList = new ArrayList<TrainingDetailBean>();
			trainingDetailBeanList.add(trainingDetailBean);

			trainingBean.setIsGross(invoiceForm.getInvoiceBean().getIsGross());
			trainingBean.setSettlementInvoiceId(0);
			trainingBean
					.setClientId(invoiceForm.getInvoiceBean().getClientId());
			cal.setTime(showDateFormat.parse(trainingBean
					.getTrainingStartDate()));
			trainingBean.setTrainingStartDate(dateFormat.format(cal.getTime()));
			cal.setTime(showDateFormat.parse(trainingBean.getTrainingEndDate()));
			trainingBean.setTrainingEndDate(dateFormat.format(cal.getTime()));

			trainingBean
					.setCreatedBy((String) session.getAttribute("username"));
			trainingBean.setDetailList(trainingDetailBeanList);

			// insert invoice
			InvoiceDetailBean invoiceDetailBean = new InvoiceDetailBean();
			invoiceDetailBean.setCreatedBy((String) session
					.getAttribute("username"));
			invoiceDetailBean.setDescription("Training \""
					+ invoiceForm.getTrainingBean().getDescription()
					+ "\" - DP");
			invoiceDetailBean.setNotes(invoiceForm.getInvoiceDetailNotes());

			if (invoiceForm.getInvoiceBean().getIsGross() == 0) {
				// exclude ppn
				double netTotal = 0;
				netTotal = trainingFee;
				invoiceDetailBean.setFee(trainingFee);
				invoiceDetailBean.setUnitPrice(trainingFee);
				invoiceDetailBean.setTotalFee(trainingFee);

				double formula = netTotal + (netTotal * ppn / 100);
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(formula);
				invoiceForm.getInvoiceBean().setTotalPpn(formula - netTotal);
				invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			} else if (invoiceForm.getInvoiceBean().getIsGross() == 1) {
				// include ppn

				double divider = 100 + ppn;
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
			invoiceForm.getInvoiceBean().setCreatedBy(
					(String) session.getAttribute("username"));

			Integer idHeader = invoiceManager.insert(invoiceForm
					.getInvoiceBean());
			invoiceForm.setStatusId(String.valueOf(invoiceForm.getInvoiceBean()
					.getStatusInvoiceId()));
			// insert training
			trainingBean.setDpInvoiceId(idHeader);
			trainingManager.insert(trainingBean);

			// setting field untuk view invoice
			invoiceForm.setStatusId("1");
			invoiceForm.setClientBean(clientManager.getById(invoiceForm
					.getInvoiceBean().getClientId()));
			// invoiceForm.setInvoiceBean(invoiceManager.getHeaderById(invoiceForm.getInvoiceBean().getTransactionInvoiceHeaderId()));
			invoiceForm.setInvoiceDetailList(invoiceManager
					.getDetailById(idHeader));
			invoiceForm.setNote(generalInformationManager.getByKey("rek_no"));
			invoiceForm.setSign(generalInformationManager.getByKey("sign"));
			invoiceForm
					.getMessageList()
					.add("Success!!! Invoice Training Down Payment has been Created!");
			return mapping.findForward("detailInvoice");
		} else if ("createInvoiceTRST".equals(invoiceForm.getTask())) {
			invoiceForm.getInvoiceBean().setClientName(
					clientManager.getById(
							invoiceForm.getInvoiceBean().getClientId())
							.getName());
			invoiceForm.getInvoiceBean().setInvoiceTypeName(
					masterManager.getInvoiceTypeById(
							invoiceForm.getInvoiceBean().getInvoiceTypeId())
							.getName());
			invoiceForm.setOngoingTrainingList(trainingManager
					.getOngoingTrainingByClient(invoiceForm.getInvoiceBean()
							.getClientId()));
			if (invoiceForm.getOngoingTrainingList().size() == 0) {
				invoiceForm
						.getMessageList()
						.add("Ooooops!!! There is no ongoing training for this client!");
				return mapping.findForward("createInvoice");
			} else {
				List<TrainingBean> trainingList = invoiceForm
						.getOngoingTrainingList();
				invoiceForm.getInvoiceBean().setIsGross(
						trainingList.get(0).getIsGross());
				invoiceForm.setDetailTrainingList(trainingManager
						.getDetailByIdHeader(trainingList.get(0)
								.getTransactionTrainingHeaderId()));
			}
			return mapping.findForward("formInvoiceTRST");
		} else if ("editInvoiceTRST".equals(invoiceForm.getTask())) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(showDateFormat.parse(invoiceForm.getInvoiceBean()
					.getInvoiceDate()));
			invoiceForm.getInvoiceBean().setInvoiceDate(
					dateFormat.format(cal.getTime()));

			invoiceForm.getInvoiceBean().setStatusInvoiceId(1);
			float ppn = Float.parseFloat(generalInformationManager.getByKey(
					"tax").getValue());
			invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			double netTotal = 0;

			Integer idHeader = invoiceForm.getInvoiceBean()
					.getTransactionInvoiceHeaderId();
			if (invoiceForm.getInvoiceBean().getIsGross() == 0) {
				// Ini kalau exclude PPN
				for (TrainingDetailBean print : invoiceForm
						.getDetailTrainingList()) {
					netTotal += print.getFee();
					InvoiceDetailBean bean = new InvoiceDetailBean();
					// head,desc,fee,crby,unit,total
					bean.setTransactionInvoiceHeaderId(idHeader);
					bean.setDescription(print.getDescription());
					bean.setFee(print.getFee());
					bean.setNotes(print.getNote());
					bean.setCreatedBy((String) session.getAttribute("username"));
					bean.setUnitPrice(print.getFee());
					bean.setTotalFee(print.getFee());
					invoiceForm.getSettlementList().add(bean);
				}
				double formula = netTotal + (netTotal * ppn / 100);
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(formula);
				invoiceForm.getInvoiceBean().setTotalPpn(formula - netTotal);
				invoiceForm.getInvoiceBean().setDetailList(
						invoiceForm.getSettlementList());
			} else if (invoiceForm.getInvoiceBean().getIsGross() == 1) {
				// Ini kalau include PPN
				double devider = 100 + ppn;
				double netFee;
				double grossTotal = 0;
				for (TrainingDetailBean print : invoiceForm
						.getDetailTrainingList()) {
					netFee = print.getFee() * 100 / devider;
					netTotal += netFee;
					grossTotal += print.getFee();
					InvoiceDetailBean bean = new InvoiceDetailBean();
					// head,desc,fee,crby,unit,total
					bean.setTransactionInvoiceHeaderId(idHeader);
					bean.setDescription(print.getDescription());
					bean.setFee(print.getFee());
					bean.setNotes(print.getNote());
					bean.setCreatedBy((String) session.getAttribute("username"));
					bean.setUnitPrice(print.getFee() * 100 / devider);
					bean.setTotalFee(print.getFee() * 100 / devider);
					invoiceForm.getSettlementList().add(bean);
				}
				double ppnValue = grossTotal - netTotal;
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(grossTotal);
				invoiceForm.getInvoiceBean().setTotalPpn(ppnValue);
				invoiceForm.getInvoiceBean().setDetailList(
						invoiceForm.getSettlementList());
			}

			// update invoice header, delete invoice detail id yang lama, add
			// yang baru
			invoiceManager.update(invoiceForm.getInvoiceBean());

			// delete all training detail & insert
			trainingManager.deleteDetailByHeader(invoiceForm.getTrainingBean()
					.getTransactionTrainingHeaderId());
			for (TrainingDetailBean print : invoiceForm.getDetailTrainingList()) {
				print.setCreatedBy((String) session.getAttribute("username"));
				print.setTransactionTrainingHeaderId(invoiceForm
						.getTrainingBean().getTransactionTrainingHeaderId());
				trainingManager.insertDetail(print);
			}

			// display invoice list
			invoiceForm.setClient(String.valueOf(invoiceForm.getInvoiceBean()
					.getClientId()));
			invoiceForm.setTransactionInvoiceHeaderId(idHeader);
			invoiceForm.setStatusId(String.valueOf(invoiceForm.getInvoiceBean()
					.getStatusInvoiceId()));
			invoiceForm.setTask("detailInvoice");
			invoiceForm.setInvoiceBean(invoiceManager.getHeaderById(invoiceForm
					.getTransactionInvoiceHeaderId()));
			invoiceForm.setClientBean(clientManager.getById(Integer
					.parseInt(invoiceForm.getClient())));
			invoiceForm
					.setInvoiceDetailList(invoiceManager
							.getDetailById(invoiceForm
									.getTransactionInvoiceHeaderId()));
			invoiceForm.setNote(generalInformationManager.getByKey("rek_no"));
			invoiceForm.setSign(generalInformationManager.getByKey("sign"));
			invoiceForm.getMessageList().add(
					"Success!!! Edit Training Settlement!");
			return mapping.findForward("detailInvoice");
		} else if ("getTax".equals(invoiceForm.getTask())) {
			invoiceForm.getInvoiceBean().setClientName(
					clientManager.getById(
							invoiceForm.getInvoiceBean().getClientId())
							.getName());
			invoiceForm.getInvoiceBean().setInvoiceTypeName(
					masterManager.getInvoiceTypeById(
							invoiceForm.getInvoiceBean().getInvoiceTypeId())
							.getName());
			invoiceForm.setOngoingTrainingList(trainingManager
					.getOngoingTrainingByClient(invoiceForm.getInvoiceBean()
							.getClientId()));
			List<TrainingBean> trainingList = invoiceForm
					.getOngoingTrainingList();
			invoiceForm.getInvoiceBean().setIsGross(
					trainingManager.getById(
							invoiceForm.getTrainingBean()
									.getTransactionTrainingHeaderId())
							.getIsGross());
			invoiceForm.setDetailTrainingList(trainingManager
					.getDetailByIdHeader(invoiceForm.getTrainingBean()
							.getTransactionTrainingHeaderId()));
			return mapping.findForward("formInvoiceTRST");
		} else if ("editInvoice".equals(invoiceForm.getTask())) {
			invoiceForm.setInvoiceBean(invoiceManager.getHeaderById(invoiceForm
					.getInvoiceBean().getTransactionInvoiceHeaderId()));
			invoiceForm.getInvoiceBean().setDetailList(
					invoiceManager.getDetailById(invoiceForm.getInvoiceBean()
							.getTransactionInvoiceHeaderId()));
			int invoiceTypeId = invoiceForm.getInvoiceBean().getInvoiceTypeId();
			if (invoiceTypeId == 1) {
				// Outsource
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				SimpleDateFormat showDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateFormat.parse(invoiceForm.getInvoiceBean()
						.getInvoiceDate()));
				invoiceForm.getInvoiceBean().setInvoiceDate(
						showDateFormat.format(cal.getTime()));
				List<InvoiceDetailBean> bean = new ArrayList<InvoiceDetailBean>();
				bean = invoiceForm.getInvoiceBean().getDetailList();
				InvoiceDetailBean invoiceDetailBean;
				for (InvoiceDetailBean temp : bean) {
					invoiceDetailBean = new InvoiceDetailBean();
					invoiceDetailBean.setTransactionInvoiceDetailId(temp
							.getTransactionInvoiceDetailId());
					invoiceDetailBean.setEmployeeName(temp.getEmployeeName());
					invoiceDetailBean.setEmployeeId(temp.getEmployeeId());
					invoiceDetailBean.setFee(temp.getFee());
					invoiceDetailBean.setNotes(temp.getNotes());
					invoiceDetailBean.setManDays(temp.getManDays());
					invoiceDetailBean.setWorkDays(temp.getWorkDays());
					invoiceForm.getProfessionalServiceList().add(
							invoiceDetailBean);
				}
				invoiceForm.setTransactionInvoiceHeaderId(invoiceForm
						.getInvoiceBean().getTransactionInvoiceHeaderId());
				invoiceForm.setClientId(String.valueOf(invoiceForm
						.getInvoiceBean().getClientId()));
				invoiceForm.setStatusInvoiceId(String.valueOf(invoiceForm
						.getInvoiceBean().getStatusInvoiceId()));
				invoiceForm.getInvoiceBean().setDetailSize(
						String.valueOf(invoiceForm.getProfessionalServiceList()
								.size()));
				invoiceForm.setTask("editInvoice");
				return mapping.findForward("formInvoicePS");
			} else if (invoiceTypeId == 2 || invoiceTypeId == 4) {
				// Head Hunter
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				SimpleDateFormat showDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				Date getDate = dateFormat.parse(invoiceForm.getInvoiceBean()
						.getInvoiceDate());
				invoiceForm.getInvoiceBean().setInvoiceDate(
						showDateFormat.format(getDate));
				List<InvoiceDetailBean> detailList = invoiceManager
						.getDetailById(invoiceForm.getInvoiceBean()
								.getTransactionInvoiceHeaderId());
				invoiceForm.setHeadHunterList(detailList);

				invoiceForm.setTransactionInvoiceHeaderId(invoiceForm
						.getInvoiceBean().getTransactionInvoiceHeaderId());
				invoiceForm.setClientId(String.valueOf(invoiceForm
						.getInvoiceBean().getClientId()));
				invoiceForm.setStatusInvoiceId(String.valueOf(invoiceForm
						.getInvoiceBean().getStatusInvoiceId()));
				invoiceForm.setHeadHunterListSize(invoiceForm
						.getHeadHunterList().size());
				return mapping.findForward("formInvoiceHH");
			} else if (invoiceTypeId == 3) {
				// Training
				String paymentDescription = invoiceManager
						.checkTrainingPaymentTypeByHeaderId(invoiceForm
								.getInvoiceBean()
								.getTransactionInvoiceHeaderId());

				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				SimpleDateFormat showDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();

				cal.setTime(dateFormat.parse(invoiceForm.getInvoiceBean()
						.getInvoiceDate()));
				invoiceForm.getInvoiceBean().setInvoiceDate(
						showDateFormat.format(cal.getTime()));
				// cek if DP/Settlement
				if (paymentDescription.toUpperCase().endsWith("DP")) {
					invoiceForm.setTrainingBean(trainingManager
							.getTrainingByInvoiceDpId(invoiceForm
									.getInvoiceBean()
									.getTransactionInvoiceHeaderId()));
					double trainingFee = (double) (2 * invoiceForm
							.getInvoiceBean().getDetailList().get(0).getFee());
					invoiceForm.setTrainingFee(trainingFee);
					invoiceForm
							.setInvoiceDetailNotes(invoiceForm.getInvoiceBean()
									.getDetailList().get(0).getNotes());
					invoiceForm.setTransactionTrainingDetailId(invoiceForm
							.getInvoiceBean().getDetailList().get(0)
							.getTransactionInvoiceDetailId());

					// set data untuk tombol back
					invoiceForm.setClient(String.valueOf(invoiceForm
							.getInvoiceBean().getClientId()));
					invoiceForm.setTransactionInvoiceHeaderId(invoiceForm
							.getInvoiceBean().getTransactionInvoiceHeaderId());
					invoiceForm.setStatusId(String.valueOf(invoiceForm
							.getInvoiceBean().getStatusInvoiceId()));

					// date format show
					cal.setTime(dateFormat.parse(invoiceForm.getTrainingBean()
							.getTrainingStartDate()));
					invoiceForm.getTrainingBean().setTrainingStartDate(
							showDateFormat.format(cal.getTime()));
					cal.setTime(dateFormat.parse(invoiceForm.getTrainingBean()
							.getTrainingEndDate()));
					invoiceForm.getTrainingBean().setTrainingEndDate(
							showDateFormat.format(cal.getTime()));
					invoiceForm.setTask("editInvoiceTRDP");
					return mapping.findForward("formInvoiceTRDP");
				} else if (paymentDescription.toUpperCase().endsWith(
						"SETTLEMENT")) {
					invoiceForm.setTask("editInvoiceTRST");
					invoiceForm.setSubTask("editInvoiceTRST");
					invoiceForm.getInvoiceBean().setInvoiceTypeName(
							masterManager.getInvoiceTypeById(
									invoiceForm.getInvoiceBean()
											.getInvoiceTypeId()).getName());

					TrainingBean trainingBean = trainingManager
							.getTrainingByInvoiceSettlementId(invoiceForm
									.getInvoiceBean()
									.getTransactionInvoiceHeaderId());
					invoiceForm.setTrainingBean(trainingBean);
					invoiceForm.setDetailTrainingList(trainingManager
							.getDetailByIdHeader(trainingBean
									.getTransactionTrainingHeaderId()));

					invoiceForm.setTransactionInvoiceHeaderId(invoiceForm
							.getInvoiceBean().getTransactionInvoiceHeaderId());
					invoiceForm.setClientId(String.valueOf(invoiceForm
							.getInvoiceBean().getClientId()));
					invoiceForm.setStatusInvoiceId(String.valueOf(invoiceForm
							.getInvoiceBean().getStatusInvoiceId()));
					invoiceForm.getInvoiceBean().setDetailSize(
							String.valueOf(invoiceForm
									.getProfessionalServiceList().size()));
					return mapping.findForward("formInvoiceTRST");
				}
			}
			return null;
		} else if ("editInvoicePS".equals(invoiceForm.getTask())) {
			// format date
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(showDateFormat.parse(invoiceForm.getInvoiceBean()
					.getInvoiceDate()));
			invoiceForm.getInvoiceBean().setInvoiceDate(
					dateFormat.format(cal.getTime()));

			double netTotal = 0;
			float ppn = (float) invoiceForm.getInvoiceBean().getPpnPercentage();
			invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			invoiceForm.getInvoiceBean().setInvoiceTypeId(1);
			if (invoiceForm.getInvoiceBean().getIsGross() == 0) {
				// Ini kalau exclude PPN
				for (InvoiceDetailBean bean : invoiceForm
						.getProfessionalServiceList()) {
					bean.setCreatedBy((String) session.getAttribute("username"));
					bean.setChangedBy((String) session.getAttribute("username"));
					bean.setTransactionInvoiceHeaderId(invoiceForm
							.getInvoiceBean().getTransactionInvoiceHeaderId());
					String description = "Jasa Professional Service - "
							+ bean.getEmployeeName() + " " + bean.getManDays()
							+ " Work Days";
					int manDays = bean.getManDays();
					double fee = bean.getFee();
					double totalFee = fee * manDays / bean.getWorkDays();
					netTotal += totalFee;
					bean.setUnitPrice(bean.getFee());
					bean.setTotalFee(totalFee);
					bean.setWorkDays(bean.getWorkDays());
					bean.setDescription(description);
					invoiceForm.getInvoiceBean().getDetailList().add(bean);
				}
				double formula = netTotal + (netTotal * ppn / 100);
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(formula);
				invoiceForm.getInvoiceBean().setTotalPpn(formula - netTotal);
				invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			} else if (invoiceForm.getInvoiceBean().getIsGross() == 1) {
				// Ini kalau include PPN
				double devider = 100 + ppn;
				double netFee;
				double grossTotal = 0;
				for (InvoiceDetailBean bean : invoiceForm
						.getProfessionalServiceList()) {
					bean.setChangedBy((String) session.getAttribute("username"));
					bean.setCreatedBy((String) session.getAttribute("username"));
					bean.setTransactionInvoiceHeaderId(invoiceForm
							.getInvoiceBean().getTransactionInvoiceHeaderId());
					int manDays = bean.getManDays();
					double fee = (bean.getFee() * 100) / devider;
					System.out.println(fee);
					double totalFee = fee * manDays / bean.getWorkDays();
					double totalGross = bean.getFee() * manDays
							/ bean.getWorkDays();
					String description = "Jasa Professional Service - "
							+ bean.getEmployeeName() + " " + bean.getManDays()
							+ " Work Days";
					netTotal += totalFee;
					grossTotal += totalGross;
					bean.setUnitPrice(fee);
					bean.setTotalFee(totalFee);
					bean.setWorkDays(bean.getWorkDays());
					bean.setDescription(description);
					invoiceForm.getInvoiceBean().getDetailList().add(bean);
				}
				double ppnValue = grossTotal - netTotal;
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(grossTotal);
				invoiceForm.getInvoiceBean().setTotalPpn(ppnValue);
				invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			}
			invoiceForm.getInvoiceBean().setChangedBy(
					(String) session.getAttribute("username"));
			invoiceManager.update(invoiceForm.getInvoiceBean());
			invoiceForm.getMessageList().clear();
			invoiceForm.getMessageList().add("Success!!! Edit Invoice");

			// setting field untuk view invoice
			invoiceForm.setTask("detailInvoice");
			invoiceForm.setStatusId(String.valueOf(invoiceForm.getInvoiceBean()
					.getStatusInvoiceId()));
			invoiceForm.setClientBean(clientManager.getById(invoiceForm
					.getInvoiceBean().getClientId()));
			invoiceForm.setInvoiceBean(invoiceForm.getInvoiceBean());
			invoiceForm.setInvoiceDetailList(invoiceManager
					.getDetailById(invoiceForm.getInvoiceBean()
							.getTransactionInvoiceHeaderId()));
			invoiceForm.setNote(generalInformationManager.getByKey("rek_no"));
			invoiceForm.setSign(generalInformationManager.getByKey("sign"));

			return mapping.findForward("detailInvoice");

		} else if ("editInvoiceTRDP".equals(invoiceForm.getTask())) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat invoiceDateFormat = new SimpleDateFormat("MM.yy");
			Calendar cal = Calendar.getInstance();
			// edit training bean
			double trainingFee = invoiceForm.getTrainingFee() / 2;
			TrainingBean trainingBean = trainingManager.getById(invoiceForm
					.getTrainingBean().getTransactionTrainingHeaderId());

			cal.setTime(showDateFormat.parse(invoiceForm.getTrainingBean()
					.getTrainingStartDate()));
			trainingBean.setTrainingStartDate(dateFormat.format(cal.getTime()));
			cal.setTime(showDateFormat.parse(invoiceForm.getTrainingBean()
					.getTrainingEndDate()));
			trainingBean.setTrainingEndDate(dateFormat.format(cal.getTime()));
			trainingBean.setDescription(invoiceForm.getTrainingBean()
					.getDescription());
			trainingBean.setIsGross(invoiceForm.getInvoiceBean().getIsGross());
			trainingBean
					.setChangedBy((String) session.getAttribute("username"));

			// edit training detail bean
			trainingBean
					.getDetailList()
					.get(0)
					.setDescription(
							"Training \"" + trainingBean.getDescription()
									+ "\" - Settlement");
			trainingBean.getDetailList().get(0).setFee(trainingFee);
			trainingBean.getDetailList().get(0)
					.setChangedBy((String) session.getAttribute("username"));

			trainingManager.update(trainingBean);

			// invoice
			InvoiceBean invoiceBean = invoiceManager.getHeaderById(invoiceForm
					.getTransactionInvoiceHeaderId());
			invoiceBean.setDetailList(invoiceManager.getDetailById(invoiceForm
					.getTransactionInvoiceHeaderId()));

			cal.setTime(showDateFormat.parse(invoiceForm.getInvoiceBean()
					.getInvoiceDate()));
			invoiceBean.setInvoiceDate(dateFormat.format(cal.getTime()));
			// header
			invoiceBean.setIsGross(invoiceForm.getInvoiceBean().getIsGross());
			invoiceBean.setNotes(invoiceForm.getInvoiceBean().getNotes());
			invoiceBean.setChangedBy((String) session.getAttribute("username"));
			// detail
			invoiceBean
					.getDetailList()
					.get(0)
					.setDescription(
							"Training \""
									+ invoiceForm.getTrainingBean()
											.getDescription() + "\" - DP");
			invoiceBean.getDetailList().get(0).setFee(trainingFee);
			invoiceBean.getDetailList().get(0)
					.setNotes(invoiceForm.getInvoiceDetailNotes());
			invoiceBean.getDetailList().get(0)
					.setChangedBy((String) session.getAttribute("username"));
			float ppn = (float) invoiceBean.getPpnPercentage();
			if (invoiceForm.getInvoiceBean().getIsGross() == 0) {
				// exclude ppn
				double netTotal = 0;
				netTotal = trainingFee;
				invoiceBean.getDetailList().get(0).setUnitPrice(trainingFee);
				invoiceBean.getDetailList().get(0).setTotalFee(trainingFee);

				double formula = netTotal + (netTotal * ppn / 100);
				invoiceBean.setTotalNet(netTotal);
				invoiceBean.setTotalGross(formula);
				invoiceBean.setTotalPpn(formula - netTotal);
			} else if (invoiceForm.getInvoiceBean().getIsGross() == 1) {
				// include ppn
				double divider = 100 + ppn;
				double netTotal;
				double grossTotal = 0;

				netTotal = trainingFee * 100 / divider;
				grossTotal = trainingFee;
				invoiceBean.getDetailList().get(0).setUnitPrice(netTotal);
				invoiceBean.getDetailList().get(0).setTotalFee(netTotal);

				double ppnValue = grossTotal - netTotal;
				invoiceBean.setTotalNet(netTotal);
				invoiceBean.setTotalGross(grossTotal);
				invoiceBean.setTotalPpn(ppnValue);
			}
			invoiceManager.update(invoiceBean);
			invoiceForm.getMessageList().add("Success!!! Edit Invoice");

			// setting field untuk view invoice
			invoiceForm.setClientBean(clientManager.getById(invoiceBean
					.getClientId()));
			invoiceForm.setInvoiceBean(invoiceBean);
			invoiceForm.setInvoiceDetailList(invoiceBean.getDetailList());
			invoiceForm.setNote(generalInformationManager.getByKey("rek_no"));
			invoiceForm.setSign(generalInformationManager.getByKey("sign"));

			return mapping.findForward("detailInvoice");

		} else if ("editInvoiceHH".equals(invoiceForm.getTask())) {
			double netTotal = 0;
			float ppn = Float.parseFloat(generalInformationManager.getByKey(
					"tax").getValue());
			if (invoiceForm.getInvoiceBean().getIsGross() == 0) {
				for (InvoiceDetailBean bean : invoiceForm.getHeadHunterList()) {
					bean.setCreatedBy((String) session.getAttribute("username"));
					netTotal += bean.getFee();
					bean.setUnitPrice(bean.getFee());
					bean.setTotalFee(bean.getFee());
					bean.setTransactionInvoiceHeaderId(invoiceForm
							.getTransactionInvoiceHeaderId());
					invoiceForm.getInvoiceBean().getDetailList().add(bean);
				}
				double formula = netTotal + (netTotal * ppn / 100);
				invoiceForm.getInvoiceBean().setTotalGross(formula);
			} else if (invoiceForm.getInvoiceBean().getIsGross() == 1) {
				double netFee;
				double grossTotal = 0;
				double divider = 100 + ppn;
				for (InvoiceDetailBean bean : invoiceForm.getHeadHunterList()) {
					bean.setCreatedBy((String) session.getAttribute("username"));
					netFee = bean.getFee() * 100 / divider;
					netTotal += netFee;
					grossTotal += bean.getFee();
					bean.setUnitPrice(netFee);
					bean.setTotalFee(netFee);
					bean.setTransactionInvoiceHeaderId(invoiceForm
							.getTransactionInvoiceHeaderId());
					invoiceForm.getInvoiceBean().getDetailList().add(bean);
				}
				invoiceForm.getInvoiceBean().setTotalGross(grossTotal);
			}
			invoiceForm.getInvoiceBean().setTotalNet(netTotal);
			invoiceForm.getInvoiceBean().setTotalPpn(
					invoiceForm.getInvoiceBean().getTotalGross() - netTotal);
			invoiceForm.getInvoiceBean().setChangedBy(
					(String) session.getAttribute("username"));
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date temp = (Date) showDateFormat.parseObject(invoiceForm
					.getInvoiceBean().getInvoiceDate());
			invoiceForm.getInvoiceBean()
					.setInvoiceDate(dateFormat.format(temp));
			invoiceManager.update(invoiceForm.getInvoiceBean());
			// display invoice
			invoiceForm.setClient(String.valueOf(invoiceForm.getInvoiceBean()
					.getClientId()));
			invoiceForm.setTransactionInvoiceHeaderId(invoiceForm
					.getInvoiceBean().getTransactionInvoiceHeaderId());
			invoiceForm.setStatusId(String.valueOf(invoiceForm.getInvoiceBean()
					.getStatusInvoiceId()));
			invoiceForm.setTask("detailInvoice");
			invoiceForm.setInvoiceBean(invoiceManager.getHeaderById(invoiceForm
					.getTransactionInvoiceHeaderId()));
			invoiceForm.setClientBean(clientManager.getById(Integer
					.parseInt(invoiceForm.getClient())));
			invoiceForm
					.setInvoiceDetailList(invoiceManager
							.getDetailById(invoiceForm
									.getTransactionInvoiceHeaderId()));
			invoiceForm.setNote(generalInformationManager.getByKey("rek_no"));
			invoiceForm.setSign(generalInformationManager.getByKey("sign"));
			invoiceForm.getMessageList().add("Success!!! Edit Invoice");
			return mapping.findForward("detailInvoice");
		} else if ("addAdditionalFee".equals(invoiceForm.getTask())) {
			invoiceForm.getInvoiceBean().setClientName(
					clientManager.getById(
							invoiceForm.getInvoiceBean().getClientId())
							.getName());
			invoiceForm.getInvoiceBean().setInvoiceTypeName(
					masterManager.getInvoiceTypeById(
							invoiceForm.getInvoiceBean().getInvoiceTypeId())
							.getName());
			invoiceForm.setOngoingTrainingList(trainingManager
					.getOngoingTrainingByClient(invoiceForm.getInvoiceBean()
							.getClientId()));
			List<TrainingBean> trainingList = invoiceForm
					.getOngoingTrainingList();
			invoiceForm.getDetailTrainingList().add(
					invoiceForm.getTrainingDetailBean());
			invoiceForm.setTrainingDetailBean(new TrainingDetailBean());
			if ("editInvoiceTRST".equals(invoiceForm.getSubTask())) {
				invoiceForm.setTask("editInvoiceTRST");
			}
			return mapping.findForward("formInvoiceTRST");
		} else if ("deleteAdditionalFee".equals(invoiceForm.getTask())) {
			invoiceForm.setOngoingTrainingList(trainingManager
					.getOngoingTrainingByClient(invoiceForm.getInvoiceBean()
							.getClientId()));
			List<TrainingBean> trainingList = invoiceForm
					.getOngoingTrainingList();
			invoiceForm.getDetailTrainingList().remove(
					invoiceForm.getDeleteIndex());
			if ("editInvoiceTRST".equals(invoiceForm.getSubTask())) {
				invoiceForm.setTask("editInvoiceTRST");
			}
			return mapping.findForward("formInvoiceTRST");
		} else if ("insertTRST".equals(invoiceForm.getTask())) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat showDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat invoiceDateFormat = new SimpleDateFormat("MM.yy");
			Calendar cal = Calendar.getInstance();
			invoiceForm.getInvoiceBean().setInvoiceNumber(
					invoiceManager.getInvoiceNumber(invoiceDateFormat
							.format(cal.getTime())));
			invoiceDateFormat = new SimpleDateFormat("MM");
			invoiceForm.getInvoiceBean().setPeriodMonth(
					Integer.parseInt(invoiceDateFormat.format(cal.getTime())));
			invoiceDateFormat = new SimpleDateFormat("yyyy");
			invoiceForm.getInvoiceBean().setPeriodYear(
					Integer.parseInt(invoiceDateFormat.format(cal.getTime())));
			invoiceForm.getInvoiceBean().setStatusInvoiceId(1);
			float ppn = Float.parseFloat(generalInformationManager.getByKey(
					"tax").getValue());
			invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			double netTotal = 0;
			if (invoiceForm.getInvoiceBean().getIsGross() == 0) {
				// Ini kalau exclude PPN
				for (TrainingDetailBean print : invoiceForm
						.getDetailTrainingList()) {
					netTotal += print.getFee();
					InvoiceDetailBean bean = new InvoiceDetailBean();
					// head,desc,fee,crby,unit,total
					bean.setTransactionInvoiceHeaderId(invoiceForm
							.getTrainingBean().getTransactionTrainingHeaderId());
					bean.setDescription(print.getDescription());
					bean.setFee(print.getFee());
					bean.setNotes(print.getNote());
					bean.setCreatedBy((String) session.getAttribute("username"));
					bean.setUnitPrice(print.getFee());
					bean.setTotalFee(print.getFee());
					invoiceForm.getSettlementList().add(bean);
				}
				double formula = netTotal + (netTotal * ppn / 100);
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(formula);
				invoiceForm.getInvoiceBean().setTotalPpn(formula - netTotal);
				invoiceForm.getInvoiceBean().setDetailList(
						invoiceForm.getSettlementList());
			} else if (invoiceForm.getInvoiceBean().getIsGross() == 1) {
				// Ini kalau include PPN
				double divider = 100 + ppn;
				double netFee;
				double grossTotal = 0;
				for (TrainingDetailBean print : invoiceForm
						.getDetailTrainingList()) {
					netFee = print.getFee() * 100 / divider;
					netTotal += netFee;
					grossTotal += print.getFee();
					InvoiceDetailBean bean = new InvoiceDetailBean();
					// head,desc,fee,crby,unit,total
					bean.setTransactionInvoiceHeaderId(invoiceForm
							.getTrainingBean().getTransactionTrainingHeaderId());
					bean.setDescription(print.getDescription());
					bean.setFee(print.getFee());
					bean.setNotes(print.getNote());
					bean.setCreatedBy((String) session.getAttribute("username"));
					bean.setUnitPrice(print.getFee() * 100 / divider);
					bean.setTotalFee(print.getFee() * 100 / divider);
					invoiceForm.getSettlementList().add(bean);
				}
				double ppnValue = grossTotal - netTotal;
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(grossTotal);
				invoiceForm.getInvoiceBean().setTotalPpn(ppnValue);
				invoiceForm.getInvoiceBean().setDetailList(
						invoiceForm.getSettlementList());
			}
			invoiceForm.getInvoiceBean().setCreatedBy(
					(String) session.getAttribute("username"));
			// insert to invoice header
			Integer idHeader = invoiceManager.insert(invoiceForm
					.getInvoiceBean());
			// update settlement invoice id training header
			trainingManager.updateSettlementId(invoiceForm.getTrainingBean()
					.getTransactionTrainingHeaderId());
			// insert to training detail
			trainingManager.deleteDetailByHeader(invoiceForm.getTrainingBean()
					.getTransactionTrainingHeaderId());
			for (TrainingDetailBean print : invoiceForm.getDetailTrainingList()) {
				print.setCreatedBy((String) session.getAttribute("username"));
				print.setTransactionTrainingHeaderId(invoiceForm
						.getTrainingBean().getTransactionTrainingHeaderId());
				trainingManager.insertDetail(print);
			}
			// display invoice
			invoiceForm.setClient(String.valueOf(invoiceForm.getInvoiceBean()
					.getClientId()));
			invoiceForm.setTransactionInvoiceHeaderId(idHeader);
			invoiceForm.setStatusId(String.valueOf(invoiceForm.getInvoiceBean()
					.getStatusInvoiceId()));
			invoiceForm.setTask("detailInvoice");
			invoiceForm.setInvoiceBean(invoiceManager.getHeaderById(invoiceForm
					.getTransactionInvoiceHeaderId()));
			invoiceForm.setClientBean(clientManager.getById(Integer
					.parseInt(invoiceForm.getClient())));
			invoiceForm
					.setInvoiceDetailList(invoiceManager
							.getDetailById(invoiceForm
									.getTransactionInvoiceHeaderId()));
			invoiceForm.setNote(generalInformationManager.getByKey("rek_no"));
			invoiceForm.setSign(generalInformationManager.getByKey("sign"));
			invoiceForm.getMessageList().add(
					"Success!!! Invoice Training Settlement has been Created!");
			return mapping.findForward("detailInvoice");
		} else if ("deleteDetailHH".equals(invoiceForm.getTask())) {
			invoiceForm.getHeadHunterList()
					.remove(invoiceForm.getDeleteIndex());
			invoiceForm.setTask("formInvoiceHH");
			invoiceForm.setHeadHunterListSize(invoiceForm.getHeadHunterList()
					.size());
			return mapping.findForward("formInvoiceHH");
		} else if ("deleteDetailHHonEditPage".equals(invoiceForm.getTask())) {
			invoiceForm.getHeadHunterList()
					.remove(invoiceForm.getDeleteIndex());
			invoiceForm.setTask("editInvoice");
			invoiceForm.setHeadHunterListSize(invoiceForm.getHeadHunterList()
					.size());
			return mapping.findForward("formInvoiceHH");
		} else if ("insertHH".equals(invoiceForm.getTask())) {
			DateFormat dateFormat = new SimpleDateFormat("MM.yy");
			Date date = new Date();
			invoiceForm.getInvoiceBean().setInvoiceNumber(
					invoiceManager.getInvoiceNumber(dateFormat.format(date)));
			invoiceForm.getInvoiceBean().setStatusInvoiceId(1);
			float ppn = Float.parseFloat(generalInformationManager.getByKey(
					"tax").getValue());
			invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			double netTotal = 0;
			if (invoiceForm.getInvoiceBean().getIsGross() == 0) {
				// Ini kalau exclude PPN
				for (InvoiceDetailBean bean : invoiceForm.getHeadHunterList()) {
					bean.setCreatedBy((String) session.getAttribute("username"));
					netTotal += bean.getFee();
					bean.setUnitPrice(bean.getFee());
					bean.setTotalFee(bean.getFee());
					invoiceForm.getInvoiceBean().getDetailList().add(bean);
				}
				double formula = netTotal + (netTotal * ppn / 100);
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(formula);
				invoiceForm.getInvoiceBean().setTotalPpn(formula - netTotal);
				invoiceForm.getInvoiceBean().setPpnPercentage(ppn);
			} else if (invoiceForm.getInvoiceBean().getIsGross() == 1) {
				// Ini kalau include PPN
				double divider = 100 + ppn;
				double netFee;
				double grossTotal = 0;
				for (InvoiceDetailBean bean : invoiceForm.getHeadHunterList()) {
					bean.setCreatedBy((String) session.getAttribute("username"));
					netFee = bean.getFee() * 100 / divider;
					netTotal += netFee;
					grossTotal += bean.getFee();
					bean.setUnitPrice(netFee);
					bean.setTotalFee(netFee);
					invoiceForm.getInvoiceBean().getDetailList().add(bean);
				}
				double ppnValue = grossTotal - netTotal;
				netTotal = netTotal;
				ppnValue = ppnValue;
				invoiceForm.getInvoiceBean().setTotalNet(netTotal);
				invoiceForm.getInvoiceBean().setTotalGross(grossTotal);
				invoiceForm.getInvoiceBean().setTotalPpn(ppnValue);
			}
			invoiceForm.getInvoiceBean().setCreatedBy(
					(String) session.getAttribute("username"));
			Integer idHeader = invoiceManager.insert(invoiceForm
					.getInvoiceBean());

			// display invoice
			invoiceForm.setClient(String.valueOf(invoiceForm.getInvoiceBean()
					.getClientId()));
			invoiceForm.setTransactionInvoiceHeaderId(idHeader);
			invoiceForm.setStatusId(String.valueOf(invoiceForm.getInvoiceBean()
					.getStatusInvoiceId()));
			invoiceForm.setTask("detailInvoice");
			invoiceForm.setInvoiceBean(invoiceManager.getHeaderById(invoiceForm
					.getTransactionInvoiceHeaderId()));
			invoiceForm.setClientBean(clientManager.getById(Integer
					.parseInt(invoiceForm.getClient())));
			invoiceForm
					.setInvoiceDetailList(invoiceManager
							.getDetailById(invoiceForm
									.getTransactionInvoiceHeaderId()));
			invoiceForm.setNote(generalInformationManager.getByKey("rek_no"));
			invoiceForm.setSign(generalInformationManager.getByKey("sign"));

			invoiceForm.getMessageList().add(
					"Success!!! Invoice Head Hunter has been Created!");

			return mapping.findForward("detailInvoice");
		} else if ("changeStatus".equals(invoiceForm.getTask())) {
			String invoiceNumber = invoiceForm.getInvoiceNumber();
			String currentStatus = invoiceForm.getStatusId();
			Map paramMap;
			if ("4".equals(currentStatus)) {
				// cancel
				paramMap = new HashMap();
				paramMap.put("invoiceNumber", invoiceNumber);
				paramMap.put("nextStatusId", 4);
				masterManager.setNextStatus(paramMap);

				// cek if training
				InvoiceBean invoiceBean = invoiceManager
						.getHeaderIdByNumber(invoiceNumber);
				if ("Training".equalsIgnoreCase(invoiceBean
						.getInvoiceTypeName())) {
					String paymentDescription = invoiceManager
							.checkTrainingPaymentTypeByHeaderId(invoiceBean
									.getTransactionInvoiceHeaderId());
					// cek if DP/Settlement
					if (paymentDescription.toUpperCase().endsWith("DP")) {
						Integer trainingHeaderId = trainingManager
								.getHeaderIdByDpId(invoiceBean
										.getTransactionInvoiceHeaderId());
						trainingManager.delete(trainingHeaderId);
					} else if (paymentDescription.toUpperCase().endsWith(
							"SETTLEMENT")) {
						paramMap = new HashMap();
						paramMap.put("settlementInvoiceId",
								invoiceBean.getTransactionInvoiceHeaderId());
						paramMap.put("changedBy",
								(String) session.getAttribute("username"));
						trainingManager
								.resetSettlementInvoiceIdByHeaderId(paramMap);
					}
				}
			} else {
				Integer nextStatusId = Integer.parseInt(masterManager
						.getNextStatus(currentStatus));
				paramMap = new HashMap();
				paramMap.put("invoiceNumber", invoiceNumber);
				paramMap.put("nextStatusId", nextStatusId);
				masterManager.setNextStatus(paramMap);
			}
			String client = invoiceForm.getClientId();
			String monthFrom = invoiceForm.getMonthFrom();
			String yearFrom = invoiceForm.getYearFrom();
			String monthTo = invoiceForm.getMonthTo();
			String yearTo = invoiceForm.getYearTo();
			String status = invoiceForm.getStatusInvoiceId();
			String tipe = invoiceForm.getInvoiceTipeId();
			if ("".equals(client)) {
				client = null;
			}
			if ("".equals(status)) {
				status = null;
			}
			if ("".equals(tipe)) {
				tipe = null;
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
			String tipe = invoiceForm.getInvoiceTipeId();
			if ("".equals(client)) {
				client = null;
			}
			if ("".equals(status)) {
				status = null;
			}
			if ("".equals(tipe)) {
				tipe = null;
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
			paramMap.put("tipe", tipe);
			invoiceForm.setStatusInvoiceList(masterManager
					.getAllStatusInvoice());
			invoiceForm.setInvoiceTypeList(masterManager.getAllInvoiceType());
			invoiceForm.setInvoiceList(invoiceManager
					.getAllWithFilter(paramMap));
			return mapping.findForward("invoice");
		} else if ("export".equals(invoiceForm.getTask())) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("name", generalInformationManager.getByKey("name")
					.getValue());
			parameters.put("address", generalInformationManager
					.getByKey("addr").getValue()
					+ "\nPhone:"
					+ generalInformationManager.getByKey("telp").getValue()
					+ "\nFax: "
					+ generalInformationManager.getByKey("fax").getValue());

			String client = invoiceForm.getClientId();
			String monthFrom = invoiceForm.getMonthFrom();
			String yearFrom = invoiceForm.getYearFrom();
			String monthTo = invoiceForm.getMonthTo();
			String yearTo = invoiceForm.getYearTo();
			String status = invoiceForm.getStatusInvoiceId();
			String tipe = invoiceForm.getInvoiceTipeId();
			if ("".equals(client)) {
				client = null;
				parameters.put("client", "All");
			} else {
				parameters.put("client",
						new ClientManager().getById(Integer.parseInt(client))
								.getName());
			}
			if ("".equals(status)) {
				status = null;
				parameters.put("invoiceStatus", "All");
			} else {
				parameters.put("invoiceStatus", new MasterManager()
						.getStatusInvoiceById(Integer.parseInt(status))
						.getName());
			}
			if ("".equals(tipe)) {
				status = null;
				parameters.put("invoiceType", "All");
			} else {
				parameters.put("invoiceType", new MasterManager()
						.getInvoiceTypeById(Integer.parseInt(tipe)).getName());
			}
			if ("".equals(yearTo)) {
				monthFrom = null;
				yearFrom = null;
				monthTo = null;
				yearTo = null;
				parameters.put("startDate", "All");
				parameters.put("endDate", "All");
			} else {
				parameters.put("startDate", getMonthName(monthFrom) + " "
						+ yearFrom);
				parameters.put("endDate", getMonthName(monthTo) + " " + yearTo);
			}
			Map paramMap = new HashMap();
			paramMap.put("monthFrom", monthFrom);
			paramMap.put("yearFrom", yearFrom);
			paramMap.put("monthTo", monthTo);
			paramMap.put("yearTo", yearTo);
			paramMap.put("client", client);
			paramMap.put("status", status);
			paramMap.put("tipe", tipe);
			invoiceForm.setStatusInvoiceList(masterManager
					.getAllStatusInvoice());
			invoiceForm.setInvoiceTypeList(masterManager.getAllInvoiceType());
			List<InvoiceBean> invoiceSummaryData = invoiceManager
					.getAllWithFilter(paramMap);
			// export to pdf
			String filePath = this.getServlet().getServletContext()
					.getRealPath("/asset/");
			parameters.put("filePath", filePath);
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat printDateFormat = new SimpleDateFormat(
					"yyyyMMddhhmmss");
			String fileName = "InvoiceSummaryReport_"
					+ printDateFormat.format(cal.getTime()) + ".pdf";
			String resultServerPath = ExportReportManager.exportToPdf(filePath
					+ "\\report\\InvoiceSummaryReport" + ".jrxml", fileName,
					parameters, invoiceSummaryData);
			
			ExportReportManager.downloadFile(response, resultServerPath, fileName);
			
			return null;

		} else if ("exportDetail".equals(invoiceForm.getTask())) {
			Integer invoiceHeaderId = invoiceForm.getInvoiceBean()
					.getTransactionInvoiceHeaderId();
			InvoiceBean invoiceBean = invoiceManager
					.getHeaderById(invoiceHeaderId);
			ClientBean clientBean = clientManager.getById(invoiceBean
					.getClientId());

			GeneralInformationBean rekNo = generalInformationManager
					.getByKey("rek_no");
			GeneralInformationBean rekNama = generalInformationManager
					.getByKey("rek_nama");
			GeneralInformationBean sign = generalInformationManager
					.getByKey("sign");

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("name", generalInformationManager.getByKey("name")
					.getValue());
			parameters.put("address", generalInformationManager
					.getByKey("addr").getValue()
					+ "\nPhone:"
					+ generalInformationManager.getByKey("telp").getValue()
					+ "\nFax: "
					+ generalInformationManager.getByKey("fax").getValue());
			parameters.put("invoiceNo", invoiceBean.getInvoiceNumber());
			parameters.put("invoiceDate", invoiceBean.getInvoiceDate());
			parameters.put("reference", "");
			parameters.put("clientName", clientBean.getName());
			parameters.put("clientAddress", clientBean.getAddress());
			parameters.put("clientCity", clientBean.getCity());
			parameters.put("clientPostalCode", clientBean.getPostalCode());
			parameters.put("clientPhoneNumber", clientBean.getPhoneNumber());
			parameters.put("clientFaxNumber", clientBean.getFaxNumber());
			parameters.put("totalNet", invoiceBean.getTotalNet());
			parameters.put("totalPPN", invoiceBean.getTotalPpn());
			parameters.put("totalGross", invoiceBean.getTotalGross());
			parameters.put("invoiceNote", invoiceBean.getNotes() == null ? ""
					: invoiceBean.getNotes());
			parameters.put("ppn", invoiceBean.getPpnPercentage()+"");
			parameters.put("accountNo", rekNo.getValue().toString());
			parameters.put("accountName", rekNama.getValue());
			parameters.put("manager", sign.getValue());

			List invoiceDetailData = invoiceManager
					.getDetailById(invoiceHeaderId);
			// export to pdf
			String filePath = this.getServlet().getServletContext()
					.getRealPath("/asset/");
			parameters.put("filePath", filePath);
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat printDateFormat = new SimpleDateFormat(
					"yyyyMMddhhmmss");
			String fileName = invoiceBean.getInvoiceNumber()
					.replaceAll("/", "")
					+ "_"
					+ printDateFormat.format(cal.getTime()) + ".pdf";
			String resultServerPath = ExportReportManager.exportToPdf(filePath
					+ "\\report\\InvoiceDetailReport" + ".jrxml", fileName,
					parameters, invoiceDetailData);
			
			ExportReportManager.downloadFile(response, resultServerPath, fileName);
			
			return null;
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
			paramMap.put("tipe", null);
			invoiceForm.setStatusInvoiceList(masterManager
					.getAllStatusInvoice());
			invoiceForm.setInvoiceTypeList(masterManager.getAllInvoiceType());
			invoiceForm.setInvoiceList(invoiceManager
					.getAllWithFilter(paramMap));
			return mapping.findForward("invoice");
		}
	}

	private String getMonthName(String input) {
		String result = "";
		Integer month = Integer.parseInt(input);
		switch (month) {
		case 1:
			result = "January";
			break;
		case 2:
			result = "February";
			break;
		case 3:
			result = "March";
			break;
		case 4:
			result = "April";
			break;
		case 5:
			result = "May";
			break;
		case 6:
			result = "June";
			break;
		case 7:
			result = "July";
			break;
		case 8:
			result = "August";
			break;
		case 9:
			result = "September";
			break;
		case 10:
			result = "October";
			break;
		case 11:
			result = "November";
			break;
		case 12:
			result = "December";
			break;
		}
		return result;
	}
}
