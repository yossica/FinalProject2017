package training;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pettyCash.PettyCashBean;

import com.ibatis.sqlmap.client.SqlMapClient;

import employee.EmployeeBean;
import utils.Filter;
import utils.IbatisHelper;

public class TrainingManager {
	
	public void insert(TrainingBean input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try{
			//masukin data header
			ibatis.startTransaction();
			Integer maxIdHeader = (Integer) ibatis.queryForObject("training.getMaxHeaderId", null);
            if(maxIdHeader==null){
            	maxIdHeader = 1;
            }else{
            	maxIdHeader++;
            }
            input.setTransactionTrainingHeaderId(maxIdHeader);
            ibatis.insert("training.insertHeader", input);
            
            //masukin data detail
            for(int i = 0; i<input.getDetailList().size();i++){
		        Integer maxIdDetail = (Integer) ibatis.queryForObject("training.getMaxDetailId", null);
	            if(maxIdDetail==null){
	            	maxIdDetail = 1;
	            }else{
	            	maxIdDetail++;
	            }
		        input.getDetailList().get(i).setTransactionTrainingDetailId(maxIdDetail);
		        input.getDetailList().get(i).setTransactionTrainingHeaderId(maxIdHeader);
	            ibatis.insert("training.insertDetail", input.getDetailList().get(i));
            }
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
	
	public void insertDetail(TrainingDetailBean input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try{
	        ibatis.startTransaction();
	        Integer maxIdDetail = (Integer) ibatis.queryForObject("training.getMaxDetailId", null);
            if(maxIdDetail==null){
            	maxIdDetail = 1;
            }else{
            	maxIdDetail++;
            }
            input.setTransactionTrainingDetailId(maxIdDetail);
	        
            ibatis.insert("training.insertDetail", input);
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
	
	public void update(TrainingBean input){
		//kalo invoice DP berubah, maka ada update di settlement 
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try{
			//update data header
			ibatis.startTransaction();
            ibatis.update("training.updateHeader", input);
            ibatis.commitTransaction();
            
            //update data detail
            for(int i = 0; i<input.getDetailList().size();i++){
		        ibatis.startTransaction();
	            ibatis.insert("training.updateDetail", input.getDetailList().get(i));
	            ibatis.commitTransaction();
            }

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
	
	//dibuat oleh aldhi
	public void updateSettlementId(int input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try{			
			ibatis.startTransaction();
			ibatis.update("training.updateSettlementId", input);
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
	
	public void deleteDetailByHeader(int input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			ibatis.startTransaction();
			ibatis.delete("training.deleteDetailByHeader", input);
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
	//sampai sini
	
	public void resetSettlementInvoiceIdByHeaderId(Map input){
		//kalau invoice settlement di cancel, maka perlu direset settlement invoice id nya jadi 0
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try{			
			ibatis.startTransaction();
            ibatis.update("training.resetSettlementInvoiceIdByHeaderId", input);
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
	
	public void delete (int input){
		//kalo DP batal, header dan detail di delete
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {

            //delete detail by header id
			ibatis.startTransaction();
			ibatis.delete("training.deleteDetailByIdHeader", input);
            ibatis.commitTransaction();
            
			//delete header 	
			ibatis.startTransaction();
			ibatis.delete("training.deleteHeader", input);
            ibatis.commitTransaction();
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
            try {
				ibatis.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deleteDetail(int input){
		//kalo additionalnya di hapus
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
            //delete detail
			ibatis.startTransaction();
			ibatis.delete("training.deleteDetail", input);
            ibatis.commitTransaction();
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
            try {
				ibatis.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public TrainingBean getById(int input){
		TrainingBean result = new TrainingBean();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			//get header
			result = (TrainingBean) ibatis.queryForObject("training.getById", input);
			
			//get detail
			List detailList = new ArrayList();
			detailList = ibatis.queryForList("training.getDetailByIdHeader", result.getTransactionTrainingHeaderId());
			result.setDetailList(detailList);;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public Integer getHeaderIdByDpId(int input) {
		Integer result = null;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (Integer)ibatis.queryForObject("training.getHeaderIdByDpId", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public TrainingDetailBean getDetailById(int input){
		TrainingDetailBean result = new TrainingDetailBean();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (TrainingDetailBean) ibatis.queryForObject("training.getDetailById", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public List getDetailByIdHeader(int input){
		//saat tambah additional training, biar datanya ke udpate
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("training.getDetailByIdHeader", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public List getAllWithFilter(Filter input){
		List<TrainingBean> result  = new ArrayList<TrainingBean>();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		Map paramMap = new HashMap();
		try {		
			//ambil data header berdasarkan filter
			if(input.getBean() instanceof TrainingBean){
				TrainingBean bean = (TrainingBean) input.getBean();
				paramMap.put("clientId", bean.getClientId());
			}
			result = ibatis.queryForList("training.getAllWithFilter", paramMap);
			
			//get detail
			for(int i = 0; i<result.size();i++){
				List detailList = new ArrayList();
				detailList = ibatis.queryForList("training.getDetailByIdHeader", result.get(i).getTransactionTrainingHeaderId());
				result.get(i).setDetailList(detailList);;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}

	public List getListedTrainingRemainderList() {
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("training.getListedTrainingRemainderList", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public List getOngoingTrainingClient(){
		List result = null;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("training.getOngoingTrainingClient", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public List getOngoingTrainingByClient(int input){
		List result = null;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("training.getOngoingTrainingByClient", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public TrainingBean getTrainingByInvoiceDpId(int input){
		TrainingBean result = new TrainingBean();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (TrainingBean) ibatis.queryForObject("training.getTrainingByInvoiceDpId", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
}
