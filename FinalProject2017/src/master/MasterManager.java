package master;

import java.util.ArrayList;
import java.util.List;

public class MasterManager {
	public List getAllCashFlowCategory(){
		List result = new ArrayList();
		return result;
	}
	public CashFlowCategoryBean getCashFlowCategoryById (String input){
		CashFlowCategoryBean result = new CashFlowCategoryBean();
		return result;
	}
	public List getAllInvoiceType(){
		List result = new ArrayList();
		return result;
	}
	public InvoiceTypeBean getInvoiceTypeById(int input){
		InvoiceTypeBean result = new InvoiceTypeBean();
		return result;
	}
	public List getAllStatusInvoice(){
		List result = new ArrayList();
		return result;
	}
	public StatusInvoiceBean getStatusInvoiceById(int input){
		StatusInvoiceBean result = new StatusInvoiceBean();
		return result;
	}
}
