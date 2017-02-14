package generalInformation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.*;
import client.ClientBean;

import com.ibatis.sqlmap.client.SqlMapClient;

public class GeneralInformationManager {
	public void update(GeneralInformationBean input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try{
			ibatis.startTransaction();
            ibatis.update("generalInformation.update", input);
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
	
	public GeneralInformationBean getByKey(String input){
		GeneralInformationBean result = new GeneralInformationBean();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		
		try {
			result = (GeneralInformationBean) ibatis.queryForObject("generalInformation.getByKey", input);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	public List getAll (){
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("generalInformation.getAll", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
	
	
}
