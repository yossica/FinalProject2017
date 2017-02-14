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
	function flyToPage(task){
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
	
	function flyToUpdate(id){
		document.forms[1].employeeId.value = id;
		flyToPage("update");
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
		            <h1 class="page-header">Employee List</h1>
		            <div class="panel-body">
		            <input type="button" value="Create" class="btn btn-primary" onclick="javascript:flyToPage('create')">
	                    <div class="table-responsive">
	                        <table class="table table-hover">
			                     <tr>
				            		<th>Employee Name</th>
				            		<th>Email</th>
				            		<th>Action</th>
				            	</tr>
	                            <tbody>
	                                <logic:iterate id="emp" name="employeeForm" property="employeeList">
					            		<tr>
					            			<td><bean:write name="emp" property="name"/></td>
					            			<td><bean:write name="emp" property="email"/></td>
					            			<td><input type="button" value="Edit" class="btn btn-primary" onclick="javascript:flyToUpdate('<bean:write name="emp" property="employeeId" format="#"/>')"></td>
					            		</tr>
					            	</logic:iterate>
	                            </tbody>
	                        </table>
	                    </div>
	                    <!-- /.table-responsive -->
	                </div>
		        </div>
		    </div>
		</div>
	</html:form>
</body>
</html>