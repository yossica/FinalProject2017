package holiday;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import utils.IbatisHelper;

import com.ibatis.sqlmap.client.SqlMapClient;

public class HolidayManager {
	public void insert(HolidayBean input) {
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();

		try {
			ibatis.startTransaction();
			// if exists = update
			Integer holidayId = (Integer) ibatis.queryForObject(
					"holiday.getIdByDate", input.getHolidayDate());
			if (holidayId == null) {
				// no = insert
				holidayId = (Integer) ibatis.queryForObject("holiday.getMaxId",
						null);
				if (holidayId == null) {
					holidayId = 1;
				} else {
					holidayId++;
				}
				input.setHolidayId(holidayId);

				ibatis.insert("holiday.insert", input);
			} else {
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

	public void delete(int input) {
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

	public int getWorkingDays(String input) {
		// asumsi: input dari handler sudah berupa tanggal MM/dd/yyyy
		int result = 0;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();

		try {
			result = (Integer) ibatis.queryForObject("holiday.getWorkingDays",
					input);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public List getAllWithFilter(Map input) {
		List result = null;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();

		try {
			result = ibatis.queryForList("holiday.getAllWithFilter", input);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public List getDistinctYear() {
		List result = null;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();

		try {
			result = ibatis.queryForList("holiday.getDistinctYear", null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
