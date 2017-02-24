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

	function flyToUpdate(clientId) {
		document.forms[1].clientId.value = clientId;
		flyToPage("update");
	}
</script>
<title>Finance Solution</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/client" method="post">
		<html:hidden property="task" name="clientForm" />
		<html:hidden property="clientId" name="clientForm" />
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Client List</h1>
					<div class="panel-body" style="margin-right: 10px;">
					<div class="pull-right">
					<button type="button" class="btn btn-primary pull-right"
								onclick="javascript:flyToPage('create')">Create</button>
					</div>
					</div>
					<div class="panel-body">
						<div class="table-responsive" style="height:370px;overflow:auto;">
							
							<table class="table table-hover">
							
								<tr>
									<th>Client Name</th>
									<th>Address</th>
									<th>City</th>
									<th>Phone</th>
									<th>Fax</th>
									<th>Postal Code</th>
									<th>Contact Person</th>
									<th>Action</th>
								</tr>
							
								<tbody>
									<logic:iterate id="client" name="clientForm"
										property="listClient">
										<tr>
											<td><bean:write name="client" property="name" /></td>
											<td><bean:write name="client" property="address" /></td>
											<td><bean:write name="client" property="city" /></td>
											<td><bean:write name="client" property="phoneNumber" /></td>
											<td><bean:write name="client" property="faxNumber" /></td>
											<td><bean:write name="client" property="postalCode" /></td>
											<td><bean:write name="client" property="contactPerson" /></td>
											<td><input type="button" value="Edit"
												class="btn btn-primary"
												onclick="javascript:flyToUpdate('<bean:write name="client" 
												property="clientId" format="#"/>')"></td>
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