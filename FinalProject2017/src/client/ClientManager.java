package client;

import java.util.ArrayList;
import java.util.List;

import utils.Paging;

public class ClientManager {
	public void insert(ClientBean input){
		
	}
	public void update(ClientBean input){
		
	}
	public void toggleEnable(ClientBean input){
		//inputnya bean karena nnti lempar ke ibatis pake bean
		
	}
	public ClientBean getById(int input){
		ClientBean result = new ClientBean();
		return result;
	}
	
	//query belum dibuat
	public int getCount(){
		int result=0;
		return result;
	}
	
	//query belum dibuat
	public List getAllWithLimit(Paging input){
		List result = new ArrayList();
		return result;
	}
	public List getAll (){
		List result = new ArrayList();
		return result;
	}
}
