package invoice;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

import client.ClientBean;

public class InvoiceForm extends ActionForm{
	
	private InvoiceBean invoiceBean = new InvoiceBean();
	
	private ClientBean clientBean = new ClientBean();
	
	private String task;
	
	private String clientId;
	private String monthFrom;
	private String yearFrom;
	private String monthTo;
	private String yearTo;
	private String statusInvoiceId;
	
	private String client;
	private String invoiceNumber;
	private String statusId;
	
	private int transactionInvoiceHeaderId;
	private String invoiceTypeId;
	
	private List clientList = new ArrayList();
	private List invoiceTypeList = new ArrayList();
	private List headHunterList = new ArrayList();
	private List outsourceList = new ArrayList();
	private List invoiceDetailList = new ArrayList();
	
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
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
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
	public List getOutsourceList() {
		return outsourceList;
	}
	public void setOutsourceList(List outsourceList) {
		this.outsourceList = outsourceList;
	}
	public List getInvoiceDetailList() {
		return invoiceDetailList;
	}
	public void setInvoiceDetailList(List invoiceDetailList) {
		this.invoiceDetailList = invoiceDetailList;
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
	public String getInvoiceTypeId() {
		return invoiceTypeId;
	}
	public void setInvoiceTypeId(String invoiceTypeId) {
		this.invoiceTypeId = invoiceTypeId;
	}
	public int getTransactionInvoiceHeaderId() {
		return transactionInvoiceHeaderId;
	}
	public void setTransactionInvoiceHeaderId(int transactionInvoiceHeaderId) {
		this.transactionInvoiceHeaderId = transactionInvoiceHeaderId;
	}
	public ClientBean getClientBean() {
		return clientBean;
	}
	public void setClientBean(ClientBean clientBean) {
		this.clientBean = clientBean;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
}
