package training;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

import utils.Filter;
import utils.IbatisHelper;

public class TrainingManager {
	public void insert(TrainingBean input){
//		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
//		try{
//			ibatis.startTransaction();
//			Integer maxId = (Integer) ibatis.queryForObject("employee.getMaxId", null);
//            if(maxId==null){
//            	maxId = 1;
//            }else{
//            	maxId++;
//            }
//            input.setEmployeeId(maxId);
//            ibatis.insert("employee.insert", input);
//            ibatis.commitTransaction();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally{
//            try {
//				ibatis.endTransaction();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
		
	}
	public void update(TrainingBean input){
		//kalo invoice DP berubah, maka ada update di settlement 
	}
	public void delete (int input){
		//kalo DP batal, header dan detail di delete
	}
	public void deleteDetail(int input){
		//kalo additionalnya di hapus
	}
	public TrainingBean getById(int input){
		TrainingBean result = new TrainingBean();
		return result;
	}
	public List getDetailByIdHeader(int input){
		//saat tambah additional training, biar datanya ke udpate
		List result = new ArrayList();
		return result;
	}
	public List getAllWithFilter(Filter input){
		List result = new ArrayList();
		return result;
	}
}
