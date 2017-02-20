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
	function insert() {

		var name = document.getElementsByName("name")[0].value;
		var email = document.getElementsByName("email")[0].value;

		var letters = /([A-Za-z ])+\w/;
		var emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/ ;
		var errorMessage = "";

		if (name == "") {
			errorMessage = errorMessage + "Name must be filled!<br/>";
		} else if (!letters.test(name)) {
			errorMessage = errorMessage
					+ "Name must be in alphabets only!<br/>";
		}
	
		if (email == "") {
			errorMessage = errorMessage + "Email must be filled!<br/>";
		} else if (!emailRegex.test(email)) {
			errorMessage = errorMessage
					+ "Email should be in valid format!<br/>";
		}
		
		if (errorMessage.length != 0) {
			document.getElementById("message").innerHTML = errorMessage;
			return;
		} else {
			swal({
				title : "Are you sure?",
				text : "System will insert these data to client database",
				type : "warning",
				showCancelButton : true,
				confirmButtonColor : "#27AE60",
				confirmButtonText : "Yes, Insert",
				cancelButtonText : "No, Cancel Please!",
				closeOnConfirm : false,
				closeOnCancel : false
			}, function(isConfirm) {
				if (isConfirm) {
					flyToPage();
				} else {
					swal("Cancelled", "Cancel Insert New Data", "error");
				}
			});
		}

	}

	function cancel() {
		document.forms[1].task.value = "employee";
		flyToPage();
	}

	function flyToPage() {
		document.forms[1].submit();
	}
</script>
<title>Finance Solution</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/employee" method="post">
		<html:hidden property="task" name="employeeForm" />
		<html:hidden property="employeeId" name="employeeForm" />
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Form Employee</h1>
				</div>

				<div class="col-lg-12">
					<div class="col-lg-12" style="padding-left: 10px">
						<div class="col-lg-10">

							<div class="row" style="margin-top: 10px;">
								<div class="col-md-3">
									<label>Name</label>
								</div>
								<div class="col-md-6">
									<html:text styleClass="form-control" name="employeeForm"
										property="name" />
								</div>
							</div>

							<div class="row" style="margin-top: 10px;">
								<div class="col-md-3">
									<label>Email</label>
								</div>
								<div class="col-md-6">
									<input id="employeeForm" name="email" type="text"
										value='<bean:write name="employeeForm" property="email"/>'
										placeholder="e.g. : name@domain.com" />
								</div>
							</div>

							<div class="row">
								<div class="col-md-3">
									<label>Employee Status</label>
								</div>
								<div class="col-md-6">

									<html:select name="employeeForm" property="isEnabled"
										styleClass="form-control">
										<html:option value="1">Enabled</html:option>
										<html:option value="0">Disabled</html:option>
									</html:select>

								</div>
							</div>

							<div class="panel-body" style="padding-left: 0;">
								<div class="pull-left">
									<button type="button" class="btn btn-primary"
										onclick="javascript:cancel()">Cancel</button>
									<button type="button" class="btn btn-primary"
										onclick="javascript:insert()">Save</button>
								</div>
							</div>

						</div>
					</div>

					<div class="col-md-15" style="color: red;" id="message">
						<logic:notEmpty name="employeeForm" property="messageList">
							<logic:iterate id="message" name="employeeForm"
								property="messageList">
								<bean:write name="message" />
							</logic:iterate>
						</logic:notEmpty>
					</div>

				</div>
			</div>
		</div>

		<%--  <div class="row" style="margin-top: 10px;">
					<div class="col-md-10" style="padding-right: 1%">
						<div class="form-group">
							<div class="col-md-2"><label>Employee Name</label></div>
							<div class="col-md-5">
								<html:text property="name" name="employeeForm" styleClass="form-control-client"/>
							</div>
						</div>
					</div>
				</div>
				<div class="row" style="margin-top: 10px;">
					<div class="col-md-10" style="padding-right: 1%">
						<div class="form-group">
							<div class="col-md-2"><label>Email</label></div>
							<div class="col-md-5">
								<html:text property="email" name="employeeForm" styleClass="form-control-client"/>
							</div>
						</div>
					</div>
				</div>
				<div class="row" style="margin-top: 10px;">
					<div class="col-md-10" style="padding-right: 1%">
						<div class="col-md-2"><label>Employee Status</label></div>
						<div class="col-md-5">
							<html:select property="isEnabled" name="employeeForm" styleClass="form-control-client">
								<html:option value="1">Enabled</html:option>
								<html:option value="0">Disabled</html:option>
							</html:select>
						</div>
					</div>
				</div> --%>


	</html:form>
</body>
</html>