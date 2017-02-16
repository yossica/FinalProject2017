package invoice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import utils.Filter;
import utils.IbatisHelper;


public class InvoiceManager {
	public void insert(InvoiceBean input){
		
	}
	
	public void update(InvoiceBean input){
		
	}
	
	public InvoiceBean getById(int input){
		InvoiceBean result = new InvoiceBean();
		return result;
	}
	
	public List getDetailByIdHeader(int input){
		List result = new ArrayList();
		return result;
	}
	
	public List getAllWithFilter(){
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("invoice.getAllWithFilter", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
