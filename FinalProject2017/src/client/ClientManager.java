package client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.IbatisHelper;

import com.ibatis.sqlmap.client.SqlMapClient;

public class ClientManager {

	public void insert(ClientBean input) {
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			ibatis.startTransaction();
			Integer maxId = (Integer) ibatis.queryForObject("client.getMaxId",
					null);
			if (maxId == null) {
				maxId = 1;
			} else {
				maxId++;
			}
			input.setClientId(maxId);
			ibatis.insert("client.insert", input);
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

	public void update(ClientBean input) {
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			ibatis.startTransaction();
			ibatis.update("client.update", input);
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

	public ClientBean getById(int input) {
		ClientBean result = new ClientBean();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (ClientBean) ibatis
					.queryForObject("client.getById", input);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List getAll() {
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("client.getAll", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List getAllEnabled() {
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("client.getAllEnabled", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
