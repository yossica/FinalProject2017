package outsource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import utils.IbatisHelper;

import com.ibatis.sqlmap.client.SqlMapClient;

public class OutsourceManager {
	public void insert(OutsourceBean input) {
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			ibatis.startTransaction();
			Integer maxId = (Integer) ibatis.queryForObject(
					"outsource.getMaxId", null);
			if (maxId == null) {
				maxId = 1;
			} else {
				maxId++;
			}
			input.setTransactionOutsourceId(maxId);
			ibatis.insert("outsource.insert", input);
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

	public void update(OutsourceBean input) {
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			ibatis.startTransaction();
			ibatis.update("outsource.update", input);
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

	public OutsourceBean getById(int input) {
		OutsourceBean result = new OutsourceBean();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (OutsourceBean) ibatis.queryForObject("outsource.getById",
					input);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public List getAllWithFilter(Map input) {
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (List) ibatis.queryForList("outsource.getAllWithFilter",
					input);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public List getListedRemainderList() {
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("outsource.getListedRemainderList",
					null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public List getMinStartDate() {
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("outsource.getMinStartDate", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public String getPeriod() {
		String result = null;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (String) ibatis
					.queryForObject("outsource.getPeriod", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public Integer checkContract(Map input) {
		Integer result = null;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (Integer) ibatis.queryForObject("outsource.checkContract",
					input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result == null) {
			return 0;
		}
		return result;
	}

	public List getOutsourceContract(Map input) {
		List<OutsourceBean> result = new ArrayList<OutsourceBean>();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (List<OutsourceBean>) ibatis.queryForList(
					"outsource.getOutsourceContract", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
