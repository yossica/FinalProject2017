<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="java.util.Calendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Professional Services</title>
<script>
	function flyToPage(task) {
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
	function flyToUpdate(id) {
		document.forms[1].transactionOutsourceId.value = id;
		flyToPage('update');
	}
	function flyToMutation(id) {
		document.forms[1].transactionOutsourceId.value = id;
		flyToPage('mutation');
	}
	function flyToEnd(id) {
		document.forms[1].transactionOutsourceId.value = id;
		flyToPage('end');
	}
	function alertError() {
		var message=document.getElementById("err");
		if(message!=null){
			var messageValue=message.value;
			
			var strValue = messageValue.substring(0, 7);
			if(strValue=="Success"){
				//Success
				swal("Good job!", messageValue, "success");
			}
			else if(strValue=="Ooooops"){
				//Ooooops
				sweetAlert("Oops...", messageValue, "error");
			}
		}
	}
	window.onload = alertError;
</script>
<title>Finance Solution</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/outsource" method="post">
		<html:hidden name="outsourceForm" property="task" />
		<html:hidden name="outsourceForm" property="transactionOutsourceId" />
		<div id="page-wrapper">
			<div class="row">
				<span> <logic:notEmpty name="outsourceForm"
						property="messageList">
						<logic:iterate id="message" name="outsourceForm"
							property="messageList">
							<input type="hidden" id="err" value="<bean:write name="message" />">
						</logic:iterate>
					</logic:notEmpty>
				</span>
				<div class="col-lg-12">
					<h1 class="page-header">Profesional Service Contract List</h1>
					<div class="panel-body" style="padding-right: 0;">
						<div class="pull-right">
							<button type="button" class="btn btn-primary"
								onclick="javascript:flyToPage('create')">Create</button>
						</div>
					</div>
					<div class="col-lg-12"
						style="border: solid 2px gray; border-radius: 10px; background-color: #EFEFEF;">
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12" style="padding-right: 1%">
								<div class="col-md-2">Client</div>
								<div class="col-md-10">
									<html:select name="outsourceForm" property="filterClient"
										styleClass="form-control-client" size="1">
										<html:option value="">Select All</html:option>
										<html:optionsCollection name="outsourceForm"
											property="optClientList" value="clientId" label="name" />
									</html:select>
								</div>
							</div>
						</div>
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12" style="padding-right: 1%">
								<div class="col-md-2">Employee</div>
								<div class="col-md-10">
									<html:select name="outsourceForm" property="filterEmployee"
										styleClass="form-control-client" size="1">
										<html:option value="">Select All</html:option>
										<html:optionsCollection name="outsourceForm"
											property="optEmployeeList" value="employeeId" label="name" />
									</html:select>
								</div>
							</div>
						</div>
						<div class="col-md-12" style="margin-top: 10px;">
							<div class="row">
								<div class="col-md-2">Period</div>
								<div class="col-md-4">
									<html:select name="outsourceForm" property="filterMonth"
										styleClass="form-control" size="1">
										<html:option value="">Select All</html:option>
										<html:option value="01">January</html:option>
										<html:option value="02">February</html:option>
										<html:option value="03">March</html:option>
										<html:option value="04">April</html:option>
										<html:option value="05">May</html:option>
										<html:option value="06">June</html:option>
										<html:option value="07">July</html:option>
										<html:option value="08">August</html:option>
										<html:option value="09">September</html:option>
										<html:option value="10">October</html:option>
										<html:option value="11">November</html:option>
										<html:option value="12">December</html:option>
									</html:select>
								</div>
								<div class="col-md-2">
									<html:select name="outsourceForm" property="filterYear"
										styleClass="form-control-client" size="1">
										<html:option value="">Select All</html:option>
										<html:optionsCollection name="outsourceForm"
											property="optYear" value="value" label="label" />
									</html:select>
								</div>
							</div>
						</div>
						<div class="col-md-12"
							style="margin-top: 10px; margin-bottom: 10px;">
							<button type="button" class="btn btn-primary"
								onclick="javascript:flyToPage('filter')">Filter</button>
						</div>
					</div>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<div class="panel-body">
				<div class="table-responsive" style="height:400px;overflow:auto;">
					<table class="table table-hover">
						<tr>
							<th>Client</th>
							<th>Employee</th>
							<th>Start Date</th>
							<th>End Date</th>
							<th>Fee</th>
							<th>Action</th>
						</tr>
						<tbody>
							<logic:notEmpty property="outsourceList" name="outsourceForm">
								<logic:iterate id="outsource" property="outsourceList"
									name="outsourceForm">
									<tr>
										<td><bean:write property="clientName" name="outsource" /></td>
										<td><bean:write property="employeeName" name="outsource" /></td>
										<td><bean:write property="startDate" name="outsource" /></td>
										<td><bean:write property="endDate" name="outsource" /></td>
										<td><bean:write property="fee" name="outsource"
												format="#,###.##" /></td>
										<td><button type="button" class="btn btn-primary"
												onclick="javascript:flyToUpdate('<bean:write property="transactionOutsourceId" name="outsource" format="#"/>')">Edit</button>
											<button type="button" class="btn btn-primary"
												onclick="javascript:flyToMutation('<bean:write property="transactionOutsourceId" name="outsource" format="#"/>')">Mutation</button>
											<button type="button" class="btn btn-primary"
												onclick="javascript:flyToEnd('<bean:write property="transactionOutsourceId" name="outsource" format="#"/>')">End</button></td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</tbody>
					</table>
				</div>
				<!-- /.table-responsive -->
			</div>
		</div>
	</html:form>
</body>
</html>