package master;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Filter;
import utils.IbatisHelper;

import com.ibatis.sqlmap.client.SqlMapClient;

public class MasterManager {
	/*public List getAllCashFlowCategory() {
		// hanya mengambil yang enabled
		List result = new ArrayList();

		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("cashFlowCategory.getAll", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}*/
	public List getAllCashFlowCategory(Filter input) {
		// hanya mengambil yang enabled + cash type sesuai + debit/kredit
		//filter nanti akan diisi di handler
		List result = null;

		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		Map paramMap = new HashMap();
		try {
			if(input.getBean() instanceof CashFlowCategoryBean){
				CashFlowCategoryBean bean = (CashFlowCategoryBean) input.getBean();
				paramMap.put("cashFlowType", bean.getCashFlowType());
				paramMap.put("isDebit", bean.getIsDebit());
			}
			result = ibatis.queryForList("cashFlowCategory.getAll", paramMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public CashFlowCategoryBean getCashFlowCategoryById(String input) {
		CashFlowCategoryBean result = new CashFlowCategoryBean();

		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (CashFlowCategoryBean) ibatis.queryForObject("cashFlowCategory.getById", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public List getAllInvoiceType() {
		// hanya mengambil yang enabled
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("invoiceType.getAll", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public InvoiceTypeBean getInvoiceTypeById(int input) {
		InvoiceTypeBean result = new InvoiceTypeBean();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (InvoiceTypeBean) ibatis.queryForObject("invoiceType.getById", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public List getAllStatusInvoice() {
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("statusInvoice.getAll", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public StatusInvoiceBean getStatusInvoiceById(int input) {
		StatusInvoiceBean result = new StatusInvoiceBean();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (StatusInvoiceBean) ibatis.queryForObject("statusInvoice.getById", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public List getAllFinanceSummary(){
		List result = null;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("financeSummary.getAll", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
