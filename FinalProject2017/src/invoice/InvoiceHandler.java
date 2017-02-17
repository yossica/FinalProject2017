package invoice;

import holiday.HolidayManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class InvoiceHandler extends Action{
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

		if ("createInvoice".equals(invoiceForm.getTask())){
			return mapping.findForward("createInvoice");
		}else if ("createInvoicePS".equals(invoiceForm.getTask())) {
			String exampleDate = invoiceForm.getInvoiceBean().getPeriodMonth()+"/01/"+invoiceForm.getInvoiceBean().getPeriodYear();
			Map paramMap = new HashMap();
			paramMap.put("clientId", invoiceForm.getInvoiceBean().getClientId());
			paramMap.put("exampleDate", exampleDate);
			System.out.println(paramMap);
			if(outsourceManager.checkContract(paramMap) != 0){
				//true ada isi
				invoiceForm.getInvoiceBean().setClientName(clientManager.getById(invoiceForm.getInvoiceBean().getClientId()).getName());
				invoiceForm.getInvoiceBean().setInvoiceTypeName(masterManager.getInvoiceTypeById(invoiceForm.getInvoiceBean().getInvoiceTypeId()).getName());
				invoiceForm.getInvoiceBean().setNotes(invoiceForm.getInvoiceBean().getNotes());
				System.out.println("ada kontrak");
				
				/*System.out.println(holidayManager.getWorkingDays(dateFormat.format(date)));*/
				//holidayManager.getWorkingDays(dateFormat.format(date));
				//invoiceForm.setOutsourceList(invoiceManager.getOutsourceContract(paramMap));
				invoiceForm.setOutsourceList(outsourceManager.getOutsourceContract(paramMap));
				
				List<OutsourceBean> bean = new ArrayList<OutsourceBean>();
				bean = outsourceManager.getOutsourceContract(paramMap);
				InvoiceDetailBean invoiceDetailBean;
				for(OutsourceBean temp : bean){
					invoiceDetailBean = new InvoiceDetailBean();
					invoiceDetailBean.setEmployeeName(temp.getEmployeeName());
					invoiceDetailBean.setFee(temp.getFee());
					invoiceDetailBean.setWorkDays(holidayManager.getWorkingDays(exampleDate));
					invoiceForm.getInvoiceDetailList().add(invoiceDetailBean);
				}
				
				/*for(EmployeeBean bean: list){
					bean.getEmpId();
					bean.getFirstName();
					bean.getLastName();
					bean.getEmail();
					bean.getHireDate();
					bean.getJobId();				
				}*/
				
				System.out.println(invoiceForm.getOutsourceList());
				
				
				
				return mapping.findForward("createInvoicePS");
			}
			else{
				//gada
				System.out.println("ga ada kontrak");
				return mapping.findForward("createInvoice");
			}
			
		}else if ("createInvoiceHH".equals(invoiceForm.getTask())) {

			invoiceForm.getInvoiceBean().setClientName(clientManager.getById(invoiceForm.getInvoiceBean().getClientId()).getName());
			invoiceForm.getInvoiceBean().setInvoiceTypeName(masterManager.getInvoiceTypeById(invoiceForm.getInvoiceBean().getInvoiceTypeId()).getName());
			
			return mapping.findForward("createInvoiceHH");
		}else if ("createInvoiceTR".equals(invoiceForm.getTask())) {
			return mapping.findForward("createInvoiceTR");
		}else {
			return mapping.findForward("invoice");
		}
	}
}
