package invoice;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class InvoiceForm extends ActionForm{
	
	private InvoiceBean invoiceBean = new InvoiceBean();
	
	private String task;
	private String message;
	private String clientId;
	private String monthFrom;
	private String yearFrom;
	private String monthTo;
	private String yearTo;

	private List clientList = new ArrayList();
	private List invoiceTypeList = new ArrayList();
	private List headHunterList = new ArrayList();
	private List outsourceList = new ArrayList();
	
	private String month;
	private String year;
	
	private String clientName;
	
	
	
	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public InvoiceForm(){
		InvoiceDetailBean bean = new InvoiceDetailBean();
		headHunterList.add(bean);
	}

	
	public InvoiceBean getInvoiceBean() {
		return invoiceBean;
	}
	public void setInvoiceBean(InvoiceBean invoiceBean) {
		this.invoiceBean = invoiceBean;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public List getClientList() {
		return clientList;
	}
	public void setClientList(List clientList) {
		this.clientList = clientList;
	}
	public List getInvoiceTypeList() {
		return invoiceTypeList;
	}
	public void setInvoiceTypeList(List invoiceTypeList) {
		this.invoiceTypeList = invoiceTypeList;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getMessage() {
		return message;
	}
	public List getHeadHunterList() {
		return headHunterList;
	}
	public void setHeadHunterList(List headHunterList) {
		this.headHunterList = headHunterList;
	}
	public InvoiceDetailBean getInvoiceDetail(int index){
		while(this.headHunterList.size() <= index){
			this.headHunterList.add(new InvoiceDetailBean());
		}
		return (InvoiceDetailBean) this.headHunterList.get(index);
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMonthFrom() {
		return monthFrom;
	}
	public void setMonthFrom(String monthFrom) {
		this.monthFrom = monthFrom;
	}
	public String getYearFrom() {
		return yearFrom;
	}
	public void setYearFrom(String yearFrom) {
		this.yearFrom = yearFrom;
	}
	public String getMonthTo() {
		return monthTo;
	}
	public void setMonthTo(String monthTo) {
		this.monthTo = monthTo;
	}
	public String getYearTo() {
		return yearTo;
	}
	public void setYearTo(String yearTo) {
		this.yearTo = yearTo;
	}


	public List getOutsourceList() {
		return outsourceList;
	}


	public void setOutsourceList(List outsourceList) {
		this.outsourceList = outsourceList;
	}


	public String getClientName() {
		return clientName;
	}


	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
}
