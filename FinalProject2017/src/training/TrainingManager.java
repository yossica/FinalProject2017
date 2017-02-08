package training;

import java.util.ArrayList;
import java.util.List;

import utils.Filter;

public class TrainingManager {
	public void insert(TrainingBean input){
		
	}
	public void update(TrainingBean input){
		//kalo invoice DP berubah, maka ada update di settlement 
	}
	public void delete (int input){
		//kalo DP batal, header dan detail di delete
	}
	public void deleteDetail(int input){
		//kalo additionalnya di hapus
	}
	public TrainingBean getById(int input){
		TrainingBean result = new TrainingBean();
		return result;
	}
	public List getDetailByIdHeader(int input){
		//saat tambah additional training, biar datanya ke udpate
		List result = new ArrayList();
		return result;
	}
	public List getAllWithFilter(Filter input){
		List result = new ArrayList();
		return result;
	}
}
