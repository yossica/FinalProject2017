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
		var password = document.forms[1].password.value;
		var newPassword = document.forms[1].newPassword.value;
		var confirmPassword = document.forms[1].confirmPassword.value;
		var error = "";
		if (password == "") {
			error += "Password must be filled\n";
		}
		if (newPassword == "") {
			error += "New Password must be filled\n";
		}
		if (confirmPassword == "") {
			error += "Confirm Password must be filled\n";
		}

		if (error.length != 0) {
			sweetAlert("Oops...", error, "error");
			return;
		} else {
			swal({
				title : "Are you sure?",
				text : "old password will be replaced to new password",
				type : "warning",
				showCancelButton : true,
				confirmButtonColor : "#ef2300",
				confirmButtonText : "Yes, Change",
				cancelButtonText : "No, Cancel Please!",
				closeOnConfirm : false,
				closeOnCancel : false
			}, function(isConfirm) {
				if (isConfirm) {
					document.forms[1].task.value = task;
					document.forms[1].submit();
				} else {
					swal("Cancelled", "Cancel change password", "error");
				}
			});
		}
	}
	function alertError() {
		var message = document.getElementById("err");
		if (message != null) {
			var messageValue = message.value;

			var strValue = messageValue.substring(0, 7);
			if (strValue == "Success") {
				//Success
				swal("Good job!", messageValue, "success");
			} else {
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

	<html:form action="/user" method="post">
		<html:hidden name="userForm" property="task" />

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Change Password</h1>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-hover">
								<tbody>
									<tr>
										<td><label>Username</label></td>
										<td><html:text name="userForm" property="userName"
												readonly="true" styleClass="form-control-client" /></td>
									</tr>
									<tr>
										<td><label>Password</label></td>
										<td><html:password name="userForm" property="password"
												styleClass="form-control-client" /></td>
									</tr>
									<tr>
										<td><label>New Password</label></td>
										<td><html:password name="userForm" property="newPassword"
												styleClass="form-control-client" /></td>
									</tr>
									<tr>
										<td><label>Confirm Password</label></td>
										<td><html:password name="userForm"
												property="confirmPassword" styleClass="form-control-client" /></td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- /.table-responsive -->
						<div class="col-md-12" style="padding-left: 0;"
							style="margin-top: 1px; margin-bottom: 10px;">
							<div class="pull-left">
								<button type="button" class="btn btn-primary ">Cancel</button>
								<button type="button" class="btn btn-primary "
									onclick="javascript:flyToPage('saveChangePassword')">Save</button>
							</div>
							<div class="col-md-4" style="color: red; overflow: auto;"
								id="message">
								<logic:notEmpty name="userForm" property="messageList">
									<logic:iterate id="message" name="userForm"
										property="messageList">
										<input type="hidden" id="err"
											value="<bean:write name="message" />">
									</logic:iterate>
								</logic:notEmpty>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>