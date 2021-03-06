<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Finance Solution</title>
<script>
	function flyToPage(task) {
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}

	function flyToUpdate(employeeId) {
		document.getElementsByName("employeeBean.employeeId")[0].value = employeeId;
		flyToPage("update");
	}
</script>
<title>Finance Solution</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/employee" method="post">
		<html:hidden property="task" name="employeeForm" />
		<html:hidden property="employeeBean.employeeId" name="employeeForm" />
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Employee List</h1>
					<div class="panel-body" style="margin-right: 10px;">
						<div class="pull-right">
							<input type="button" value="Create"
								class="btn btn-primary pull-right"
								onclick="javascript:flyToPage('create')">
						</div>
					</div>
					<div class="panel-body">
						<div class="table-responsive"
							style="height: 400px; overflow: auto;">
							<table class="table table-hover">
								<tr>
									<th>Employee Name</th>
									<th>Email</th>
									<th>Action</th>
								</tr>
								<tbody>
									<logic:iterate id="employee" name="employeeForm"
										property="employeeList">
										<tr>
											<td><bean:write name="employee" property="name" /></td>
											<td><bean:write name="employee" property="email" /></td>
											<td><input type="button" value="Edit"
												class="btn btn-primary"
												onclick="javascript:flyToUpdate('<bean:write name="employee" property="employeeId" format="#"/>')"></td>
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