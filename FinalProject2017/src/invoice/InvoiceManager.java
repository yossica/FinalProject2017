package invoice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import utils.Filter;
import utils.IbatisHelper;


public class InvoiceManager {
	public void insert(InvoiceBean input){
		
	}
	public void update(InvoiceBean input){
		
	}
	public List getDetailByIdHeader(int input){
		List result = new ArrayList();
		return result;
	}
	public List getAllWithFilter(Filter input){
		List result = new ArrayList();
		return result;
	}
	public List getCreatedRemainderList() {
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("invoice.getCreatedRemainderList", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public List getSentOutsourceRemainderList() {
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("invoice.getSentOutsourceRemainderList", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public Integer checkInvoice(Map input){
		Integer result = null;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (Integer) ibatis.queryForObject("invoice.checkInvoice", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result == null){
			return 0;
		}
		return result;
	}
	public List getListedTrainingRemainderList() {
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("invoice.getListedTrainingRemainderList", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
}
