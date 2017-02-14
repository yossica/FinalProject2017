package employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import utils.Filter;
import utils.IbatisHelper;


public class EmployeeManager {
	public void insert(EmployeeBean input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try{
			ibatis.startTransaction();
			Integer maxId = (Integer) ibatis.queryForObject("employee.getMaxId", null);
            if(maxId==null){
            	maxId = 1;
            }else{
            	maxId++;
            }
            input.setEmployeeId(maxId);
            ibatis.insert("employee.insert", input);
            ibatis.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
            try {
				ibatis.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void update(EmployeeBean input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try{
			ibatis.startTransaction();
            ibatis.update("employee.update", input);
            ibatis.commitTransaction();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
            try {
				ibatis.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public EmployeeBean getById(int input){
		EmployeeBean result = new EmployeeBean();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (EmployeeBean) ibatis.queryForObject("employee.getById", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public List getAllWithFilter(Filter input){
		List result = new ArrayList();
		return result;
	}
	
	public List getAll (){
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("employee.getAll", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public List getAllEnabled (){
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("employee.getAllEnabled", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
