<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change Password</title>
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
	<span>
		<logic:notEmpty name="userForm" property="messageList">
			<logic:iterate id="message" name="userForm" property="messageList">
				<bean:write name="message" /> 
			</logic:iterate>
		</logic:notEmpty>
	</span>
	<div id="page-wrapper">
	    <div class="row">
	        <div class="col-lg-12">
	            <h1 class="page-header">Change Password</h1>
	        </div>
	        <div class="row" style="margin-top: 10px;">
				<div class="col-md-10" style="padding-right: 1%">
					<div class="col-md-2"><label>Username</label></div>
					<div class="col-md-5">
						<html:text name="userForm" property="userName" readonly="true" styleClass="form-control-client"/>
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-10" style="padding-right: 1%">
					<div class="col-md-2"><label>Password</label></div>
					<div class="col-md-5">
						 <html:password name="userForm" property="password" styleClass="form-control-client"/>
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-10" style="padding-right: 1%">
					<div class="col-md-2"><label>New Password</label></div>
					<div class="col-md-5">
						 <html:password name="userForm" property="password" styleClass="form-control-client"/>
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-10" style="padding-right: 1%">
					<div class="col-md-2"><label>New Password</label></div>
					<div class="col-md-5">
						 <html:password name="userForm" property="confirmPassword" styleClass="form-control-client"/>
					</div>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 10px; margin-bottom: 10px;">
	         	<button type="button" class="btn btn-primary ">Cancel</button>
            	<button type="button" class="btn btn-primary " onclick="javascript:flyToPage('saveChangePassword')">Save</button>
            </div>
		</div>                      
	</div>
    </html:form>
</body>
</html>