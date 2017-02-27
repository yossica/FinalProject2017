package user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import utils.IbatisHelper;

import com.ibatis.sqlmap.client.SqlMapClient;

public class UserManager {
	public void changePassword(UserBean input) {
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			ibatis.startTransaction();
			input.setPassword(encrypt(input.getPassword()));
			ibatis.update("user.changePassword", input);
			ibatis.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ibatis.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int checkLogin(UserBean input) {
		int result = 0;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			input.setPassword(encrypt(input.getPassword()));
			result = (Integer) ibatis.queryForObject("user.checkLogin", input);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public List getAll() {
		List result = null;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("user.getAll", null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public int checkUsername(String input) {
		int result = 0;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (Integer) ibatis.queryForObject("user.checkUsername",
					input);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public void resetPassword(String input) {
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			ibatis.startTransaction();
			UserBean bean = new UserBean();
			bean.setUserName(input);
			bean.setPassword(encrypt(input));
			ibatis.update("user.changePassword", bean);
			ibatis.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ibatis.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void insert(String input) {
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			ibatis.startTransaction();
			UserBean bean = new UserBean();
			bean.setUserName(input);
			bean.setPassword(encrypt(input));
			ibatis.insert("user.insert", bean);
			ibatis.commitTransaction();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ibatis.endTransaction();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private String encrypt(String input) {
		// https://www.mkyong.com/java/java-sha-hashing-example/
		MessageDigest md;
		StringBuffer sb = null;
		try {
			md = MessageDigest.getInstance("SHA-256");

			md.update(input.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
