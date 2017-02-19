package invoice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import com.ibatis.sqlmap.client.SqlMapClient;

import utils.Filter;
import utils.IbatisHelper;


public class InvoiceManager {
	
	public String getInvoiceNumber(String input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		String invoiceNumber = "";
		try {
			String invoiceDate = (String)ibatis.queryForObject("invoice.getLatestInvoiceDate", null);
			if (invoiceDate == null){
				String numberStringTemp = (String)ibatis.queryForObject("invoice.getMaxInvoiceNumber", null);
				if (numberStringTemp == null){
					numberStringTemp = "001";
				}
				Integer numberIntegerTemp = Integer.parseInt(numberStringTemp.substring(0,3));
				invoiceNumber = String.format("%03d", numberIntegerTemp);
				invoiceNumber += "/ACE/INV/";
				invoiceNumber += input;
			}else if (Integer.parseInt(input.substring(3)) > Integer.parseInt(invoiceDate.substring(0,4))){
				invoiceNumber += "001";
				invoiceNumber += "/ACE/INV/";
				invoiceNumber += input;
			}else {
				String numberStringTemp = (String)ibatis.queryForObject("invoice.getMaxInvoiceNumber", null);
				Integer numberIntegerTemp = Integer.parseInt(numberStringTemp.substring(0,3))+1;
				invoiceNumber = String.format("%03d", numberIntegerTemp);
				invoiceNumber += "/ACE/INV/";
				invoiceNumber += input;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return invoiceNumber;
	}
	
	public Integer getMaxInvoiceHeaderId(){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		Integer id = null;
		try {
			id = (Integer) ibatis.queryForObject("invoice.getMaxIdHeader", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (id == null){
			id = 1;
		}
		return id;
		
	}
	
	public Integer getMaxInvoiceDetailId(){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		Integer id = null;
		try {
			id = (Integer) ibatis.queryForObject("invoice.getMaxIdDetail", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (id == null){
			id = 1;
		}
		return id;	
	}
	
	public void insert(InvoiceBean input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try{
			ibatis.startTransaction();
			Integer idHeader = getMaxInvoiceHeaderId();
			input.setTransactionInvoiceHeaderId(idHeader);
			ibatis.insert("invoice.insertHeader", input);
			for (InvoiceDetailBean bean : input.getDetailList()){
				bean.setTransactionInvoiceHeaderId(idHeader);
				bean.setTransactionInvoiceDetailId(getMaxInvoiceDetailId());
				ibatis.insert("invoice.insertDetail", bean);
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
	
	public void insertDetail(InvoiceDetailBean input){
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try{
			ibatis.startTransaction();
			ibatis.insert("invoice.insertDetail", input);
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
	
	public void update(InvoiceBean input){
		
	}
	
//	public InvoiceBean getById(int input){
//		InvoiceBean result = new InvoiceBean();
//		return result;
//	}

//	public List getDetailByIdHeader(int input){
//		List result = new ArrayList();
//		return result;
//	}
	
	public List getAllWithFilter(Map input){
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("invoice.getAllWithFilter", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public InvoiceBean getHeaderById(int input){
		InvoiceBean result = new InvoiceBean();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (InvoiceBean) ibatis.queryForObject("invoice.getHeaderById", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public List getDetailById(int input){
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("invoice.getDetailById", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public List getCreatedRemainderList() {
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("invoice.getCreatedRemainderList", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public List getSentOutsourceRemainderList() {
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("invoice.getSentOutsourceRemainderList", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public Integer checkInvoice(Map input){
		Integer result = null;
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = (Integer) ibatis.queryForObject("invoice.checkInvoice", input);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result == null){
			return 0;
		}
		return result;
	}
	public List getListedTrainingRemainderList() {
		List result = new ArrayList();
		SqlMapClient ibatis = IbatisHelper.getSqlMapInstance();
		try {
			result = ibatis.queryForList("invoice.getListedTrainingRemainderList", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
}
