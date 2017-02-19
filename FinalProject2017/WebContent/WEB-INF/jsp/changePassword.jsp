<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
	function flyToPage(task)
	{
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
</script>
<title>Finance Solution</title>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	
	<html:form action="/user" method="post">
	<html:hidden  name="userForm" property="task"/> 
	
	<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Change Password</h1>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-hover">
								<tbody>
									<tr>
										<td><label>Username</label></td>
										<td><html:text name="userForm" property="userName" readonly="true" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Password</label></td>
										<td><html:password name="userForm" property="password" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>New Password</label></td>
										<td> <html:password name="userForm" property="password" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Confirm Password</label></td>
										<td><html:password name="userForm" property="confirmPassword" styleClass="form-control-client"/></td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- /.table-responsive -->
						<div class="col-md-12" style="padding-left:0;" style="margin-top: 1px; margin-bottom: 10px;">
							<div class="pull-left">
				            	<button type="button" class="btn btn-primary " onclick="javascript:flyToPage('saveChangePassword')">Save</button>
					         	<button type="button" class="btn btn-primary ">Cancel</button>
					        </div>	
					         	<div class="col-md-4" style="color:red;overflow: auto;" id="message">
									<logic:notEmpty name="userForm" property="messageList" >
										<logic:iterate id="message" name="userForm" property="messageList">
											<bean:write name="message" /> 
										</logic:iterate>
									</logic:notEmpty>
								</div>
				            
				        </div>
					</div>
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>