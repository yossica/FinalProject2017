package outsource;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import utils.Filter;
import client.ClientManager;

public class OutsourceHandler extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		OutsourceForm outsourceForm = (OutsourceForm) form;
		OutsourceManager outsourceManager = new OutsourceManager();
		ClientManager clientManager = new ClientManager();
		
		
		if("create".equals(outsourceForm.getTask())){
			return mapping.findForward("outsource");
			
		}else if("update".equals(outsourceForm.getTask())){
			return mapping.findForward("outsource");
		}else if("mutation".equals(outsourceForm.getTask())){
			return mapping.findForward("outsource");
		}else if("end".equals(outsourceForm.getTask())){
			return mapping.findForward("outsource");
		}else{
			outsourceForm.setOptClientList(clientManager.getAllEnabled());
			
			Map filter = new HashMap();
			filter.put("client", outsourceForm.getFilterClient());
			filter.put("month", outsourceForm.getFilterMonth());
			filter.put("year", outsourceForm.getFilterYear());
			
			outsourceForm.setOutsourceList(outsourceManager.getAllWithFilter(filter));
	
			return mapping.findForward("outsource");
		}
	}

}
