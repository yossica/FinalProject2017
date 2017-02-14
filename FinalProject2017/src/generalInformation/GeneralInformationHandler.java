package generalInformation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GeneralInformationHandler  extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GeneralInformationForm generalInformationForm = (GeneralInformationForm) form;
		GeneralInformationManager generalInformationManager = new GeneralInformationManager();
		if("update".equals(generalInformationForm.getTask()))
		{
			generalInformationForm.setTask("save"+generalInformationForm.getTask());
			GeneralInformationBean generalInformationBean = generalInformationManager.getByKey(generalInformationForm.getKey());
			generalInformationForm.setValue(generalInformationBean.getValue());
			generalInformationForm.setDataType(generalInformationBean.getDataType());
			generalInformationForm.setLength(generalInformationBean.getLength());
			
			generalInformationManager.getByKey(generalInformationForm.getKey());
			return mapping.findForward("editGeneralInformation");
		}
		else if ("saveupdate".equals(generalInformationForm.getTask()))
		{
			GeneralInformationBean generalInformationBean = new GeneralInformationBean();
			generalInformationBean.setKey(generalInformationForm.getKey());
			generalInformationBean.setValue(generalInformationForm.getValue());
			generalInformationBean.setDataType(generalInformationForm.getDataType());
			generalInformationBean.setLength(generalInformationForm.getLength());
			
			
			generalInformationForm.setListGeneralInformation(generalInformationManager.getAll());
			return mapping.findForward("generalInformation");
		}
		
		else
		{
		generalInformationForm.setListGeneralInformation(generalInformationManager.getAll());
		return mapping.findForward("generalInformation");
		}
	}
}
