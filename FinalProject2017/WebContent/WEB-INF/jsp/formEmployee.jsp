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
	function flyToSave()
	{
		document.forms[1].submit();
	}
</script>
<title>Finance Solution</title>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	<html:form action="/employee" method="post">
		<html:hidden property="task" name="employeeForm"/>
		<html:hidden property="employeeId" name="employeeForm"/>
		<div id="page-wrapper">
	    	<div class="row">
		        <div class="col-lg-12">
		            <h1 class="page-header">Form Employee</h1>
		        </div>
		        <div class="row" style="margin-top: 10px;">
					<div class="col-md-10" style="padding-right: 1%">
						<div class="form-group">
							<div class="col-md-2">Employee Name</div>
							<div class="col-md-5">
								<html:text property="name" name="employeeForm" styleClass="form-control"/>
							</div>
						</div>
					</div>
				</div>
				<div class="row" style="margin-top: 10px;">
					<div class="col-md-10" style="padding-right: 1%">
						<div class="form-group">
							<div class="col-md-2">Email</div>
							<div class="col-md-5">
								<html:text property="email" name="employeeForm" styleClass="form-control"/>
							</div>
						</div>
					</div>
				</div>
				<div class="row" style="margin-top: 10px;">
					<div class="col-md-10" style="padding-right: 1%">
						<div class="col-md-2">Employee Status</div>
						<div class="col-md-5">
							<html:select property="isEnabled" name="employeeForm" styleClass="form-control">
								<html:option value="1">Enabled</html:option>
								<html:option value="0">Disabled</html:option>
							</html:select>
						</div>
					</div>
				</div>
				<div class="col-md-10" style="padding-right: 1%">
                   	</div>
                   	<input type="button" value="Cancel" class="btn btn-primary" onclick="javascript:flyToPage('employee')">
                    <input type="button" value="Save" class="btn btn-primary" onclick="javascript:flyToSave()">
                </div>
		  </div>
    </html:form>
</body>
</html>