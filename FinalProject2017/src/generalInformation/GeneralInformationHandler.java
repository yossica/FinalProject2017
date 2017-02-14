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
		if("editGeneralInformation".equals(generalInformationForm.getTask()))
		{
			return mapping.findForward("editGeneralInformation");
		}
		else
		{
		generalInformationForm.setListGeneralInformation(generalInformationManager.getAll());
		return mapping.findForward("generalInformation");
		//sampe sini tinggal di jspnya aja 
		}
	}
}
