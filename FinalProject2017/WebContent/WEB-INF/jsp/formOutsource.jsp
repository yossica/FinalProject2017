<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
	function flyToPage(task) {
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
	function flyToSave() {
		document.forms[1].submit();
	}
</script>
<title>Client</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/outsource" method="post">
		<html:hidden property="task" name="outsourceForm" />
		<html:hidden property="transactionOutsourceId" name="outsourceForm"/>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<logic:equal name="outsourceForm" property="task" 
								value = "savecreate" >
						<h1 class="page-header">Create Profesional Service Contract</h1>
					</logic:equal>
					<logic:equal name="outsourceForm" property="task" 
								value = "saveupdate" >
						<h1 class="page-header">Update Profesional Service Contract</h1>
					</logic:equal>
					<logic:equal name="outsourceForm" property="task" 
								value = "savemutation" >
						<h1 class="page-header">Mutation Profesional Service Contract</h1>
					</logic:equal>
					
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-hover">
								<tbody>
									<tr>
										<td>Client</td>
										<td>
											<logic:equal name="outsourceForm" property="task" 
														value = "savecreate" >
												<html:select name="outsourceForm" property="clientId"
													styleClass="form-control-client" size="1" >
													<html:optionsCollection name="outsourceForm"
														property="optClientList" value="clientId" label="name" />
												</html:select>
											</logic:equal>
											<logic:equal name="outsourceForm" property="task" 
														value = "saveupdate" >
												<html:text name="outsourceForm" property="clientId"/>
												<html:text name="outsourceForm" property="clientName"/>
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td>Employee</td>
										<td>
											<html:select name="outsourceForm" property="employeeId"
												styleClass="form-control-client" size="1">
												<html:optionsCollection name="outsourceForm"
													property="optEmployeeList" value="employeeId" label="name" />
											</html:select>
										</td>
									</tr>
									<tr>
										<td>Start Date</td>
										<td><html:text name="outsourceForm" property="startDate" /></td>
									</tr>
									<tr>
										<td>End Date</td>
										<td><html:text name="outsourceForm" property="endDate" /></td>
									</tr>
									<tr>
										<td>Tax</td>
										<td>
											<html:radio name="outsourceForm"  property="isGross" value="1"/>Gross
											<html:radio name="outsourceForm"   property="isGross" value="0" />Nett
										</td>
									</tr>
									<tr>
										<td>Fee</td>
										<td><html:text name="outsourceForm" property="fee" /></td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- /.table-responsive -->
					</div>
					<table border="1">

					</table>
					<button type="button" class="btn btn-primary pull-right"
						onclick="javascript:flyToSave()">Save</button>
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>