package generalInformation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GeneralInformationHandler extends Action {
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("username") == null) {
			return mapping.findForward("login");
		}
		GeneralInformationForm generalInformationForm = (GeneralInformationForm) form;
		GeneralInformationManager generalInformationManager = new GeneralInformationManager();

		if ("update".equals(generalInformationForm.getTask())) {
			generalInformationForm.setTask("save"
					+ generalInformationForm.getTask());
			GeneralInformationBean generalInformationBean = generalInformationManager
					.getByKey(generalInformationForm.getGeneralInformationBean().getKey());
			generalInformationForm.setGeneralInformationBean(generalInformationBean);
			return mapping.findForward("editGeneralInformation");
		} else if ("saveupdate".equals(generalInformationForm.getTask())) {
			generalInformationForm.getGeneralInformationBean().setChangedBy((String)session.getAttribute("username"));
			generalInformationManager.update(generalInformationForm.getGeneralInformationBean());
			generalInformationForm.setListGeneralInformation(generalInformationManager.getAll());
			return mapping.findForward("generalInformation");
		}

		else {
			generalInformationForm
					.setListGeneralInformation(generalInformationManager
							.getAll());
			return mapping.findForward("generalInformation");
		}
	}
}
