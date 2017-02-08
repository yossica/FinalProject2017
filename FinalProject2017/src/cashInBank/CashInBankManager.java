package cashInBank;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pettyCash.PettyCashBean;

import com.ibatis.sqlmap.client.SqlMapClient;

import utils.Filter;
import utils.IbatisHelper;

public class CashInBankManager {
	public void insert(CashInBankBean input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		
		try {
			ibatis.startTransaction();
			
			ibatis.insert("cashInBank.insert", input);
			
			ibatis.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ibatis.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public List getAllWithFilter(Filter input){
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		Map paramMap = new HashMap();
		
		try {		
			paramMap.put("startDate", input.getStartDate());
			paramMap.put("endDate", input.getEndDate());
			if(input.getBean() instanceof PettyCashBean){
				PettyCashBean bean = (PettyCashBean) input.getBean();
				paramMap.put("category", bean.getCashFlowCategoryId());
			}
			result = ibatis.queryForList("cashInBank.getAllWithFilter", paramMap);			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	public double getCurrentBalance(){
		double result = 0;
		
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		
		try {			
			result = (Double) ibatis.queryForObject("cashInBank.getCurrentBalance", null);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return result;
	}
}
