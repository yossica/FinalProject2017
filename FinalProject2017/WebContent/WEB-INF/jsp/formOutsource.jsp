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
	function flyToSave() {
		document.forms[1].submit();
	}
	function validate() {
		var errorMessage = "";
		var startDate = document.getElementsByName("outsourceBean.startDate")[0].value;
		var endDate = document.getElementsByName("outsourceBean.endDate")[0].value;
		var fee = document.getElementsByName("outsourceBean.fee")[0].value;
		var doubleReg = /^([\d]+)(|.[\d]+)$/;
		if (startDate == "") {
			errorMessage = errorMessage + "Start date must be filled!<br/>";
		}
		if (endDate == "") {
			errorMessage = errorMessage + "End date must be filled!<br/>";
		}
		if (startDate != "" && endDate != "") {
			var s = startDate.split("-");
			var sDate = new Date(parseInt(s[0]), parseInt(s[1]) - 1,
					parseInt(s[2]));
			var e = endDate.split("-");
			var eDate = new Date(parseInt(e[0]), parseInt(e[1]) - 1,
					parseInt(e[2]));
			if (sDate > eDate) {
				errorMessage = errorMessage
						+ "Start Date must be later than End Date! <br/>";
			}
		}
		if (fee == "") {
			errorMessage = errorMessage + "Fee  must be filled!<br/>";
		} else if (!doubleReg.test(fee)) {
			errorMessage = errorMessage + "Fee must be number!<br/>";
		} else if (parseFloat(fee) < 0) {
			errorMessage = errorMessage + "Fee cannot be negative!<br/>";
		}
		if (errorMessage.length != 0) {
			document.getElementById("message").innerHTML = errorMessage;
			return;
		} else {
			swal({
				title : "Are you sure?",
				text : "System will save these data to master outsource",
				type : "warning",
				showCancelButton : true,
				confirmButtonColor : "#ef2300",
				confirmButtonText : "Yes, Save",
				cancelButtonText : "No, Cancel Please!",
				closeOnConfirm : false,
				closeOnCancel : false
			}, function(isConfirm) {
				if (isConfirm) {
					flyToSave();
				} else {
					swal("Cancelled", "Cancel Save Master Outsource", "error");
				}
			});
			/* if (confirm("Are you sure to save data ?")) {
				flyToSave();
			} */
		}
	}
</script>
<title>Client</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/outsource" method="post">
		<html:hidden property="task" name="outsourceForm" />
		<html:hidden property="transactionOutsourceId" name="outsourceForm" />
		<html:hidden property="outsourceBean.transactionOutsourceId"
			name="outsourceForm" />
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<logic:equal name="outsourceForm" property="task"
						value="savecreate">
						<h1 class="page-header">Create Profesional Service Contract</h1>
					</logic:equal>
					<logic:equal name="outsourceForm" property="task"
						value="saveupdate">
						<h1 class="page-header">Update Profesional Service Contract</h1>
					</logic:equal>
					<logic:equal name="outsourceForm" property="task"
						value="savemutation">
						<h1 class="page-header">Mutation Profesional Service Contract</h1>
					</logic:equal>
					<logic:equal name="outsourceForm" property="task" value="saveend">
						<h1 class="page-header">End Profesional Service Contract</h1>
					</logic:equal>
				</div>
				<div class="col-lg-12">
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-10" style="padding-right: 1%">
							<div class="col-md-7">
								<span id="message"> <logic:notEmpty name="outsourceForm"
										property="messageList">
										<logic:iterate id="message" name="outsourceForm"
											property="messageList">
											<div>
												<bean:write name="message" />
											</div>
										</logic:iterate>
									</logic:notEmpty>
								</span>
							</div>
						</div>
					</div>
					<div class="row" style="margin-top: 10px;">
						<div class="col-md-10" style="padding-right: 1%">
							<div class="col-md-3">
								<label>Client</label>
							</div>
							<div class="col-md-9">
								<logic:equal name="outsourceForm" property="task"
									value="savecreate">
									<html:select name="outsourceForm"
										property="outsourceBean.clientId"
										styleClass="form-control-client" size="1">
										<html:optionsCollection name="outsourceForm"
											property="optClientList" value="clientId" label="name" />
									</html:select>
								</logic:equal>
								<logic:equal name="outsourceForm" property="task"
									value="saveupdate">
									<html:text name="outsourceForm"
										property="outsourceBean.clientName" readonly="true"
										styleClass="form-control-client" />
									<html:hidden name="outsourceForm"
										property="outsourceBean.clientId" />
								</logic:equal>
								<logic:equal name="outsourceForm" property="task"
									value="savemutation">
									<html:select name="outsourceForm"
										property="outsourceBean.clientId"
										styleClass="form-control-client" size="1">
										<html:optionsCollection name="outsourceForm"
											property="optClientList" value="clientId" label="name" />
									</html:select>
								</logic:equal>
								<logic:equal name="outsourceForm" property="task"
									value="saveend">
									<html:text name="outsourceForm"
										property="outsourceBean.clientName" readonly="true"
										styleClass="form-control-client" />
									<html:hidden name="outsourceForm"
										property="outsourceBean.clientId" />
								</logic:equal>
							</div>
						</div>
					</div>
					<div class="col-md-10" style="margin-top: 10px;">
						<div class="row">
							<div class="col-md-3">
								<label>Employee</label>
							</div>
							<div class="col-md-9">
								<logic:equal name="outsourceForm" property="task"
									value="savecreate">
									<html:select name="outsourceForm"
										property="outsourceBean.employeeId"
										styleClass="form-control-client" size="1">
										<html:optionsCollection name="outsourceForm"
											property="optEmployeeList" value="employeeId" label="name" />
									</html:select>
								</logic:equal>
								<logic:notEqual name="outsourceForm" property="task"
									value="savecreate">
									<html:text name="outsourceForm"
										property="outsourceBean.employeeName" readonly="true"
										styleClass="form-control-client" />
									<html:hidden name="outsourceForm"
										property="outsourceBean.employeeId" />
								</logic:notEqual>
							</div>
						</div>
					</div>
					<div class="col-md-10" style="margin-top: 10px;">
						<div class="row">
							<div class="col-md-3">
								<label>Start Date</label>
							</div>
							<div class="col-md-9">
								<logic:equal name="outsourceForm" property="task"
									value="savecreate">
									<input type="date" class="form-control" style="width: 100%;"
										name="outsourceBean.startDate"
										value="<bean:write property="outsourceBean.startDate" name="outsourceForm" />" />
								</logic:equal>
								<logic:equal name="outsourceForm" property="task"
									value="saveupdate">
									<html:text name="outsourceForm"
										property="outsourceBean.startDate" readonly="true"
										styleClass="form-control-client" />
								</logic:equal>
								<logic:equal name="outsourceForm" property="task"
									value="savemutation">
									<input type="date" class="form-control" style="width: 100%;"
										name="outsourceBean.startDate"
										value="<bean:write property="outsourceBean.startDate" name="outsourceForm" />" />
								</logic:equal>
								<logic:equal name="outsourceForm" property="task"
									value="saveend">
									<html:text name="outsourceForm"
										property="outsourceBean.startDate" readonly="true"
										styleClass="form-control-client" />
								</logic:equal>
							</div>
						</div>
					</div>
					<div class="col-md-10" style="margin-top: 10px;">
						<div class="row">
							<div class="col-md-3">
								<label>End Date</label>
							</div>
							<div class="col-md-9">
								<logic:notEqual name="outsourceForm" property="task"
									value="saveupdate">
									<input type="date" class="form-control" style="width: 100%;"
										name="outsourceBean.endDate"
										value="<bean:write property="outsourceBean.endDate" name="outsourceForm" />" />
								</logic:notEqual>
								<logic:equal name="outsourceForm" property="task"
									value="saveupdate">
									<html:text name="outsourceForm"
										property="outsourceBean.endDate" readonly="true"
										styleClass="form-control-client" />
								</logic:equal>
							</div>
						</div>
					</div>
					<div class="col-md-10" style="margin-top: 10px;">
						<div class="row">
							<div class="col-md-3">
								<label>Tax</label>
							</div>
							<div class="col-md-9">
								<logic:notEqual name="outsourceForm" property="task"
									value="saveend">
									<div class="col-md-4">
										<html:radio name="outsourceForm"
											property="outsourceBean.isGross" value="0" />
										&nbsp;Exclude &nbsp;
										<html:radio name="outsourceForm"
											property="outsourceBean.isGross" value="1" />
										&nbsp;Include
									</div>
								</logic:notEqual>
								<logic:equal name="outsourceForm" property="task"
									value="saveend">
									<html:radio name="outsourceForm"
										property="outsourceBean.isGross" value="1" disabled="true" />Gross
									<html:radio name="outsourceForm"
										property="outsourceBean.isGross" value="0" disabled="true" />Nett
								</logic:equal>
							</div>
						</div>
					</div>
					<div class="col-md-10" style="margin-top: 10px;">
						<div class="row">
							<div class="col-md-3">
								<label>Fee</label>
							</div>
							<div class="col-md-9">
								<logic:notEqual name="outsourceForm" property="task"
									value="saveend">
									<html:text name="outsourceForm" property="outsourceBean.fee"
										styleClass="form-control-client" />
								</logic:notEqual>
								<logic:equal name="outsourceForm" property="task"
									value="saveend">
									<html:text name="outsourceForm" property="outsourceBean.fee"
										styleClass="form-control-client" readonly="true" />
								</logic:equal>

							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-12" style>
					<button type="button" class="btn btn-primary"
						onclick="javascript:flyToPage('back')">Back</button>
					<button type="button" class="btn btn-primary"
						onclick="javascript:validate()">Save</button>

				</div>
			</div>
		</div>
	</html:form>
</body>
</html>