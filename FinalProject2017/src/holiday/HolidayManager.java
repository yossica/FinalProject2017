package holiday;

import utils.IbatisHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

public class HolidayManager {
	public void insert(HolidayBean input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		
		try {
			ibatis.startTransaction();
			//if exists = update
			Integer holidayId = (Integer) ibatis.queryForObject("holiday.getIdByDate", input.getHolidayDate());
			if(holidayId == null){
				//no = insert
				holidayId = (Integer) ibatis.queryForObject("holiday.getMaxId", null);
				if(holidayId == null){
					holidayId = 1;
				}
				else {
					holidayId++;
				}
				input.setHolidayId(holidayId);
				
				ibatis.insert("holiday.insert", input);
			}
			else {
				input.setHolidayId(holidayId);
				
				ibatis.update("holiday.update", input);
			}
			
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
	public void delete(int input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		
		try {
			ibatis.startTransaction();
			
			ibatis.delete("holiday.delete", input);
			
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
	public int getWorkingDays(String input){
		//asumsi: input dari handler sudah berupa tanggal MM/dd/yyyy
		int result = 0;		
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		
		try {			
			result = (Integer) ibatis.queryForObject("holiday.getWorkingDays", input);			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return result;
	}
	public List getAll(){
		List result = null;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		
		try {			
			result = ibatis.queryForList("holiday.getAll", null);			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return result;
	}
}
