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
		            <div class="panel-body">
	                    <table>
	                    	<tr>
	                    		<td class="col-md-5">Employee Name</td>
	                    		<td class="col-md-12"><html:text property="name" name="employeeForm"></html:text></td>
	                    	</tr>
	                    	<tr>
	                    		<td class="col-md-5">Email</td>
	                    		<td class="col-md-12"><html:text property="email" name="employeeForm"></html:text></td>
	                    	</tr>
	                    	<tr>
	                    		<td class="col-md-5">Employee Status</td>
	                    		<td class="col-md-12">
									<html:select property="isEnabled" name="employeeForm">
										<html:option value="">Pilih Satu</html:option>
										<html:option value="1">Enabled</html:option>
										<html:option value="0">Disabled</html:option>
									</html:select>
								</td>
	                    	</tr>
	                    	<tr>
	                    		<td class="col-md-5"><input type="button" value="Cancel" class="btn btn-primary" onclick="javascript:flyToPage('employee')"></td>
	                    		<td class="col-md-5"><input type="button" value="Save" class="btn btn-primary" onclick="javascript:flyToSave()"></td>
	                    	</tr>
	                    </table>
	                </div>
		        </div>
		    </div>
	    </div>
    </html:form>
</body>
</html>