package training;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import utils.Filter;
import client.ClientBean;
import client.ClientManager;

public class TrainingHandler extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();

		if (session.getAttribute("username") == null) {
			return mapping.findForward("login");
		}
		TrainingForm trainingForm = (TrainingForm) form;
		TrainingManager trainingManager = new TrainingManager();
		
		if("loadTrainingHeader".equals(trainingForm.getTask())){
			trainingForm.setClientList(trainingManager.getOngoingTrainingClient());
			List<TrainingBean> trainingList = trainingManager.getOngoingTrainingByClient(trainingForm.getClientId());
			trainingForm.getTrainingDetailBean().setTransactionTrainingHeaderId(trainingList.get(0).getTransactionTrainingHeaderId());
			trainingForm.setTrainingHeaderList(trainingList);
			trainingForm.setTrainingDetailList(trainingManager.getDetailByIdHeader(trainingList.get(0).getTransactionTrainingHeaderId()));
			return mapping.findForward("additionalTraining");
		}
		else if("loadTrainingDetail".equals(trainingForm.getTask())){
			trainingForm.setClientList(trainingManager.getOngoingTrainingClient());
			trainingForm.setTrainingHeaderList(trainingManager.getOngoingTrainingByClient(trainingForm.getClientId()));
			trainingForm.setTrainingDetailList(trainingManager.getDetailByIdHeader(trainingForm.getTrainingDetailBean().getTransactionTrainingHeaderId()));
			return mapping.findForward("additionalTraining");
		}
		else if("insertDetail".equals(trainingForm.getTask())){
			TrainingDetailBean trainingDetailBean = trainingForm.getTrainingDetailBean();
			trainingDetailBean.setIsSettlement(0);
			trainingDetailBean.setCreatedBy((String)session.getAttribute("username"));
			trainingManager.insertDetail(trainingDetailBean);
			
			trainingForm.setClientList(trainingManager.getOngoingTrainingClient());
			trainingForm.setTrainingHeaderList(trainingManager.getOngoingTrainingByClient(trainingForm.getClientId()));
			trainingForm.setTrainingDetailList(trainingManager.getDetailByIdHeader(trainingForm.getTrainingDetailBean().getTransactionTrainingHeaderId()));
			
			trainingForm.getTrainingDetailBean().setFee(0);
			trainingForm.getTrainingDetailBean().setDescription("");
			return mapping.findForward("additionalTraining");
		}
		else if("deleteDetail".equals(trainingForm.getTask())){
			trainingManager.deleteDetail(trainingForm.getTransactionTrainingDetailId());
			
			trainingForm.setClientList(trainingManager.getOngoingTrainingClient());
			trainingForm.setTrainingHeaderList(trainingManager.getOngoingTrainingByClient(trainingForm.getClientId()));
			trainingForm.setTrainingDetailList(trainingManager.getDetailByIdHeader(trainingForm.getTrainingDetailBean().getTransactionTrainingHeaderId()));
			
			trainingForm.getTrainingDetailBean().setFee(0);
			trainingForm.getTrainingDetailBean().setDescription("");
			return mapping.findForward("additionalTraining");
		}
		else {
			//task = additionalTraining
			List<ClientBean> clientList = trainingManager.getOngoingTrainingClient();			
			if(clientList.size()==0){
				trainingForm.getMessageList().add("There is no ongoing training!");
			}
			else {
				trainingForm.setClientList(clientList);
				List<TrainingBean> trainingList = trainingManager.getOngoingTrainingByClient(clientList.get(0).getClientId());
				trainingForm.setTrainingHeaderList(trainingList);
				trainingForm.setTrainingDetailList(trainingManager.getDetailByIdHeader(trainingList.get(0).getTransactionTrainingHeaderId()));
			}
			return mapping.findForward("additionalTraining");
		}
	}

}
