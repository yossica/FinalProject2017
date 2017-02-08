package outsource;

import java.util.ArrayList;
import java.util.List;

import utils.Paging;

public class OutsourceManager {
	public void insert(OutsourceBean input){
		
	}
	public void update(OutsourceBean input){

		//update fee & is gross
	}
	public void mutation(OutsourceBean input){
		//end contract yang lama
		//insert contract yang baru
	}
	public void endContract(int id,int isEnable){
		//ganti end date contract jadi tanggal hari itu
	}	
  
	public OutsourceBean getById(int input){
		OutsourceBean result = new OutsourceBean();
		return result;
	}
  
	public int getCount(){
		int result=0;
		return result;
	}
  
	public List getAllWithLimit(Paging input){
		List result = new ArrayList();
		return result;
	}
}
