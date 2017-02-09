package outsource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import utils.Filter;
import utils.IbatisHelper;

public class OutsourceManager {
	public void insert(OutsourceBean input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try{
			ibatis.startTransaction();
            ibatis.insert("outsource.insert", input);
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
	
	public void update(OutsourceBean input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try{
			ibatis.startTransaction();
            ibatis.update("outsource.update", input);
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
	
	public OutsourceBean getById(int input){
		OutsourceBean result = new OutsourceBean();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (OutsourceBean) ibatis.queryForObject("outsource.getById", input);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public List getAllWithFilter(Filter input){
		List result = new ArrayList();
		return result;
	}
}
