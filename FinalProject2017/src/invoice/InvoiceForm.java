package invoice;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class InvoiceForm extends ActionForm{
	
	private InvoiceBean invoiceBean = new InvoiceBean();
	
	private String task;

	private String clientId;
	private String monthFrom;
	private String yearFrom;
	private String monthTo;
	private String yearTo;
	private String statusInvoiceId;

	private List clientList = new ArrayList();
	private List invoiceTypeList = new ArrayList();
	private List headHunterList = new ArrayList();
	
	private List invoiceList;
	private List statusInvoiceList;
	
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
	
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
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
	public String getStatusInvoiceId() {
		return statusInvoiceId;
	}
	public void setStatusInvoiceId(String statusInvoiceId) {
		this.statusInvoiceId = statusInvoiceId;
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
	
	public List getInvoiceList() {
		return invoiceList;
	}
	public void setInvoiceList(List invoiceList) {
		this.invoiceList = invoiceList;
	}
	public List getStatusInvoiceList() {
		return statusInvoiceList;
	}
	public void setStatusInvoiceList(List statusInvoiceList) {
		this.statusInvoiceList = statusInvoiceList;
	}
}
