package pettyCash;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import utils.Filter;
import utils.IbatisHelper;


public class PettyCashManager {
	public void insert(PettyCashBean input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		
		try {
			ibatis.startTransaction();
			
			ibatis.insert("pettyCash.insert", input);
			
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
		List result = null;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		Map paramMap = new HashMap();
		
		try {		
			paramMap.put("startDate", input.getStartDate());
			paramMap.put("endDate", input.getEndDate());
			if(input.getBean() instanceof PettyCashBean){
				PettyCashBean bean = (PettyCashBean) input.getBean();
				paramMap.put("category", bean.getCashFlowCategoryId());
			}
			result = ibatis.queryForList("pettyCash.getAllWithFilter", paramMap);			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return result;
	}
	public Double getCurrentBalance(){
		Double result = 0d;
		
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		
		try {			
			result = (Double) ibatis.queryForObject("pettyCash.getCurrentBalance", null);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		if(result == null)
			return 0d;
		else
			return result;
	}
}
