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
			
			Integer pettyCashId = (Integer) ibatis.queryForObject("pettyCash.getMaxId", null);
			if(pettyCashId == null){
				pettyCashId = 1;
			}
			else {
				pettyCashId++;
			}
			input.setTransactionPettyCashId(pettyCashId);
			
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
	public List getAllWithFilter(Map input){
		List result = null;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
	
		try {					
			result = ibatis.queryForList("pettyCash.getAllWithFilter", input);			
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
