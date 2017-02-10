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
import client.ClientManager;

public class TrainingHandler extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TrainingForm trainingForm = (TrainingForm) form;
		TrainingManager trainingManager = new TrainingManager();
		ClientManager clientManager = new ClientManager();
		HttpSession session = request.getSession();
		
		if("loadHeaderTraining".equals(trainingForm.getTask())){
			trainingForm.setClientList(clientManager.getAllEnabled());
			Filter filter = new Filter();
			TrainingBean trainingBean = new TrainingBean();
			//belum selesai
			trainingForm.setTrainingHeaderList(trainingManager.getAllWithFilter(filter));
			return mapping.findForward("additionalTraining");
		}
		else if("loadDetailTraining".equals(trainingForm.getTask())){
			trainingForm.setClientList(clientManager.getAllEnabled());
			return mapping.findForward("additionalTraining");
		}
		else if("insertDetail".equals(trainingForm.getTask())){
			TrainingDetailBean trainingDetailBean = new TrainingDetailBean();
			trainingDetailBean.setIsSettlement(0);
			trainingDetailBean.setCreatedBy((String)session.getAttribute("username"));
			trainingDetailBean.setTransactionTrainingHeaderId(trainingForm.getTransactionTrainingHeaderId());
			trainingDetailBean.setFee(trainingForm.getFee());
			trainingDetailBean.setDescription(trainingForm.getDescription());
			trainingManager.insertDetail(trainingDetailBean);
			
			trainingForm.setClientList(clientManager.getAllEnabled());
			return mapping.findForward("additionalTraining");
		}
		else if("deleteDetail".equals(trainingForm.getTask())){
			//trainingManager.deleteDetail(trainingForm.getTransactionTrainingDetailId());
			trainingForm.setClientList(clientManager.getAllEnabled());
			return mapping.findForward("additionalTraining");
		}
		else {
			//task = additionalTraining
			trainingForm.setClientList(clientManager.getAllEnabled());
			
			return mapping.findForward("additionalTraining");
		}
	}

}
