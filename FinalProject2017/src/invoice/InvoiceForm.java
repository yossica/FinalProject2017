package invoice;

import generalInformation.GeneralInformationBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;

import training.TrainingBean;
import training.TrainingDetailBean;
import client.ClientBean;

public class InvoiceForm extends ActionForm{
	
	private InvoiceBean invoiceBean = new InvoiceBean();

	private TrainingBean trainingBean = new TrainingBean();
	private TrainingDetailBean trainingDetailBean = new TrainingDetailBean();
	
	private double trainingFee;
	private String invoiceDetailNotes;
	
	private ClientBean clientBean = new ClientBean();
	
	private GeneralInformationBean note = new GeneralInformationBean();
	private GeneralInformationBean sign = new GeneralInformationBean();
	
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
	private List<InvoiceDetailBean> professionalServiceList = new ArrayList<InvoiceDetailBean>();
	private List<InvoiceDetailBean> headHunterList = new ArrayList<InvoiceDetailBean>();
	private List<InvoiceDetailBean> settlementList = new ArrayList<InvoiceDetailBean>();
	private List<TrainingDetailBean> detailTrainingList = new ArrayList<TrainingDetailBean>();
	private List outsourceList = new ArrayList();
	private List invoiceDetailList = new ArrayList();
	private List ongoingTrainingList;
	private int transactionTrainingDetailId;
	
	private List invoiceList;
	
	private List statusInvoiceList;
	private List messageList;
	
	private int deleteIndex;
	
	public InvoiceForm(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		invoiceBean.setInvoiceDate(dateFormat.format(date));
		InvoiceDetailBean bean = new InvoiceDetailBean();
		headHunterList.add(bean);
	}
	public InvoiceBean getInvoiceBean() {
		return invoiceBean;
	}
	public void setInvoiceBean(InvoiceBean invoiceBean) {
		this.invoiceBean = invoiceBean;
	}	
	public TrainingBean getTrainingBean() {
		return trainingBean;
	}
	public void setTrainingBean(TrainingBean trainingBean) {
		this.trainingBean = trainingBean;
	}
	public double getTrainingFee() {
		return trainingFee;
	}
	public void setTrainingFee(double trainingFee) {
		this.trainingFee = trainingFee;
	}
	public String getInvoiceDetailNotes() {
		return invoiceDetailNotes;
	}
	public void setInvoiceDetailNotes(String invoiceDetailNotes) {
		this.invoiceDetailNotes = invoiceDetailNotes;
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
	public List<InvoiceDetailBean> getHeadHunterList() {
		return headHunterList;
	}
	public void setHeadHunterList(List<InvoiceDetailBean> headHunterList) {
		this.headHunterList = headHunterList;
	}
	public InvoiceDetailBean getInvoiceDetailHH(int index){
		while(this.headHunterList.size() <= index){
			this.headHunterList.add(new InvoiceDetailBean());
		}
		return (InvoiceDetailBean) this.headHunterList.get(index);
	}
	public TrainingDetailBean getTrainingDetail(int index){
		while (this.detailTrainingList.size() <= index){
			this.detailTrainingList.add(new TrainingDetailBean());
		}
		return (TrainingDetailBean) this.detailTrainingList.get(index);
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

	public void print(){
		System.out.println(invoiceBean.getTransactionInvoiceHeaderId());
		//System.out.println(invoiceBean.getInvoiceNumber());
		System.out.println(invoiceBean.getInvoiceDate());
		//System.out.println(invoiceBean.getPeriodMonth());
		//System.out.println(invoiceBean.getPeriodYear());
		//System.out.println(invoiceBean.getStatusInvoiceId());
		//System.out.println(invoiceBean.getInvoiceTypeId());
		//System.out.println(invoiceBean.getClientId());
		System.out.println(invoiceBean.getTotalNet());
		//System.out.println(invoiceBean.getPpnPercentage());
		System.out.println(invoiceBean.getIsGross());
		System.out.println(invoiceBean.getTotalGross());
		System.out.println(invoiceBean.getNotes());
		//System.out.println(invoiceBean.getCreatedBy());
		//System.out.println(invoiceBean.getCreatedDate());
		System.out.println(invoiceBean.getChangedBy());
		System.out.println(invoiceBean.getChangedDate());
		System.out.println(invoiceBean.getTotalPpn());
	}
  
	public List getMessageList() {
		if(messageList == null){
			messageList = new ArrayList();
		}
		return messageList;
	}
	public void setMessageList(List messageList) {
		this.messageList = messageList;
	}

	public GeneralInformationBean getNote() {
		return note;
	}
	public void setNote(GeneralInformationBean note) {
		this.note = note;
	}
	public GeneralInformationBean getSign() {
		return sign;
	}
	public void setSign(GeneralInformationBean sign) {
		this.sign = sign;
	}
	public List<InvoiceDetailBean> getProfessionalServiceList() {
		return professionalServiceList;
	}
	public void setProfessionalServiceList(List<InvoiceDetailBean> professionalServiceList) {
		this.professionalServiceList = professionalServiceList;
	}
	public InvoiceDetailBean getInvoiceDetailPS(int index){
		while(this.professionalServiceList.size() <= index){
			this.professionalServiceList.add(new InvoiceDetailBean());
		}
		return (InvoiceDetailBean) this.professionalServiceList.get(index);
	}
	public int getDeleteIndex() {
		return deleteIndex;
	}
	public void setDeleteIndex(int deleteIndex) {
		this.deleteIndex = deleteIndex;
	}
	public List getOngoingTrainingList() {
		return ongoingTrainingList;
	}
	public void setOngoingTrainingList(List ongoingTrainingList) {
		this.ongoingTrainingList = ongoingTrainingList;
	}
	public List<TrainingDetailBean> getDetailTrainingList() {
		if(detailTrainingList == null)
			detailTrainingList=new ArrayList();
		return detailTrainingList;
	}
	public void setDetailTrainingList(List<TrainingDetailBean> detailTrainingList) {
		this.detailTrainingList = detailTrainingList;
	}
	public TrainingDetailBean getTrainingDetailBean() {
		return trainingDetailBean;
	}
	public void setTrainingDetailBean(TrainingDetailBean trainingDetailBean) {
		this.trainingDetailBean = trainingDetailBean;
	}
	public int getTransactionTrainingDetailId() {
		return transactionTrainingDetailId;
	}
	public void setTransactionTrainingDetailId(int transactionTrainingDetailId) {
		this.transactionTrainingDetailId = transactionTrainingDetailId;
	}
	public List<InvoiceDetailBean> getSettlementList() {
		return settlementList;
	}
	public void setSettlementList(List<InvoiceDetailBean> settlementList) {
		this.settlementList = settlementList;
	}
	
}

