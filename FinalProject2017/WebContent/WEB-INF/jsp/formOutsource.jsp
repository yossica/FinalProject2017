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
				</div>
				<div class="col-lg-12" style="padding-left: 0px;">	
					<div class="panel-body" style="padding-left: 0px;padding-top:0px;">
						<div class="row"">
							<div class="col-md-10" style="padding-right: 1%">
								<div class="col-md-2"><label>Client</label></div>
								<div class="col-md-5">
									<logic:equal name="outsourceForm" property="task" value = "savecreate" >
												<html:select name="outsourceForm" property="clientId" styleClass="form-control-client" size="1" >
													<html:optionsCollection name="outsourceForm" property="optClientList" value="clientId" label="name" />
												</html:select>
											</logic:equal>
											<logic:equal name="outsourceForm" property="task" value = "saveupdate" >
												<html:text name="outsourceForm" property="clientName" readonly="true" styleClass="form-control-client"/>
												<html:hidden name="outsourceForm" property="clientId"/>
											</logic:equal>
											<logic:equal name="outsourceForm" property="task" value = "savemutation" >
												<html:select name="outsourceForm" property="clientId" styleClass="form-control-client" size="1" >
													<html:optionsCollection name="outsourceForm" property="optClientList" value="clientId" label="name" />
												</html:select>
											</logic:equal>
											<logic:equal name="outsourceForm" property="task" value = "saveend" >
												<html:text name="outsourceForm" property="clientName" readonly="true" styleClass="form-control-client"/>
												<html:hidden name="outsourceForm" property="clientId"/>
											</logic:equal>
								</div>
							</div>
						</div>
						<div class="col-md-10" style="margin-top: 10px;">
							<div class="row">
								<div class="col-md-2"><label>Employee</label></div>
								<div class="col-md-5">
									<logic:equal name="outsourceForm" property="task" 
														value = "savecreate" >
										<html:select name="outsourceForm" property="employeeId"
											styleClass="form-control-client" size="1">
											<html:optionsCollection name="outsourceForm"
												property="optEmployeeList" value="employeeId" label="name" />
										</html:select>
									</logic:equal>
									<logic:equal name="outsourceForm" property="task" 
												value = "saveupdate" >
										<html:text name="outsourceForm" property="employeeName" readonly="true" styleClass="form-control-client"/>
										<html:hidden name="outsourceForm" property="employeeId"/>
									</logic:equal>
									<logic:equal name="outsourceForm" property="task" 
												value = "savemutation" >
										<html:select name="outsourceForm" property="employeeId"
											styleClass="form-control-client" size="1">
											<html:optionsCollection name="outsourceForm"
												property="optEmployeeList" value="employeeId" label="name" />
										</html:select>
									</logic:equal>
									<logic:equal name="outsourceForm" property="task" 
												value = "saveend" >
										<html:text name="outsourceForm" property="employeeName" readonly="true" styleClass="form-control-client"/>
										<html:hidden name="outsourceForm" property="employeeId"/>
									</logic:equal>
								</div>
							</div>
						</div>
						<div class="col-md-10" style="margin-top: 10px;">
							<div class="row">
								<div class="col-md-2"><label>Start Date</label></div>
								<div class="col-md-5">
									<logic:equal name="outsourceForm" property="task" 
														value = "savecreate" >
										<html:text name="outsourceForm" property="startDate" styleClass="form-control-client"/>
									</logic:equal>
									<logic:equal name="outsourceForm" property="task" 
												value = "saveupdate" >
										<html:text name="outsourceForm" property="startDate" readonly="true" styleClass="form-control-client"/>
									</logic:equal>
									<logic:equal name="outsourceForm" property="task" 
												value = "savemutation" >
										<html:text name="outsourceForm" property="startDate" styleClass="form-control-client"/>
									</logic:equal>
									<logic:equal name="outsourceForm" property="task" 
												value = "saveend" >
										<html:text name="outsourceForm" property="startDate" readonly="true" styleClass="form-control-client"/>
									</logic:equal>
								</div>
							</div>
						</div>
						<div class="col-md-10" style="margin-top: 10px;">
							<div class="row">
								<div class="col-md-2"><label>End Date</label></div>
								<div class="col-md-5">
									<logic:notEqual name="outsourceForm" property="task" 
												value = "saveupdate" >
										<html:text name="outsourceForm" property="endDate" styleClass="form-control-client"/>
									</logic:notEqual>
									<logic:equal name="outsourceForm" property="task" 
												value = "saveupdate" >
										<html:text name="outsourceForm" property="endDate" readonly="true" styleClass="form-control-client"/>
									</logic:equal>
								</div>
							</div>
						</div>
						<div class="col-md-10" style="margin-top: 10px;">
							<div class="row">
								<div class="col-md-2"><label>Tax</label></div>
								<div class="col-md-5">
									<logic:notEqual name="outsourceForm" property="task" 
												value = "saveend" >
										<html:radio name="outsourceForm"  property="isGross" value="1"/>Gross
										<html:radio name="outsourceForm"   property="isGross" value="0"/>Nett
									</logic:notEqual>
									<logic:equal name="outsourceForm" property="task" 
												value = "saveend" >
										<html:radio name="outsourceForm"  property="isGross" value="1" disabled="true"/>Gross
										<html:radio name="outsourceForm"   property="isGross" value="0" disabled="true"/>Nett
									</logic:equal>
								</div>
							</div>
						</div>			
						<div class="col-md-10" style="margin-top: 10px;">
							<div class="row">
								<div class="col-md-2"><label>Fee</label></div>
								<div class="col-md-5">
									<html:text name="outsourceForm" property="fee" styleClass="form-control-client"/>
								</div>
							</div>
						</div>				
					</div>
				</div>
				<div class="col-lg-12">
					<button type="button" class="btn btn-primary" onclick="javascript:flyToSave()">Save</button>
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>