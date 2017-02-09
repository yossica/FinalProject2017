package user;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import utils.IbatisHelper;

import com.ibatis.sqlmap.client.SqlMapClient;

public class UserManager {
	public void changePassword(UserBean input) {
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try{
			ibatis.startTransaction();
            ibatis.update("user.changePassword", input);
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
	
	public int checkLogin(UserBean input){
		int result = 0;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (Integer) ibatis.queryForObject("user.checkLogin", input);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
}
