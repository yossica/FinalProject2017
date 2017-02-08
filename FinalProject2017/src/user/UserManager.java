package user;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

public class UserManager {
	public void changePassword(String input) throws SQLException{
		SqlMapClient smc = utils.IbatisHelper.getSqlMapInstance();
		smc.update("user.changePassword", input);
	}
	public int checkLogin(UserBean input){
		int result = 0;
		return result;
	}
}
