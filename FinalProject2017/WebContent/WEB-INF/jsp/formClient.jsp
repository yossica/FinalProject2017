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
		var address = document.getElementsByName("address")[0].value;
		var city = document.getElementsByName("city")[0].value;
		var phoneNumber = document.getElementsByName("phoneNumber")[0].value;
		var faxNumber = document.getElementsByName("faxNumber")[0].value;
		var postalCode = document.getElementsByName("postalCode")[0].value;

		var letters = /([A-Za-z ])+\w/;
		var alphanumAndSpecial = /^[ A-Za-z0-9_@.,]*$/;
		var errorMessage = "";

		if (name == "") {
			errorMessage = errorMessage + "Name must be filled!<br/>";
		} else if (!letters.test(name)) {
			errorMessage = errorMessage
					+ "Name must be in alphabets only!<br/>";
		}
		if (address == "") {
			errorMessage = errorMessage + "Address must be filled!<br/>";
		} else if (!alphanumAndSpecial.test(address)) {
			errorMessage = errorMessage
					+ "Address should contain letters, numbers, comma, and period only!<br/>";
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
				confirmButtonColor : "#DD6B55",
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
		document.forms[1].task.value = "client";
		flyToPage();
	}

	function flyToPage() {
		document.forms[1].submit();
	}
</script>
<title>Client</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/client" method="post">
		<html:hidden property="task" name="clientForm" />
		<html:hidden property="clientId" name="clientForm" />
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Form Client</h1>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-hover">
								<tbody>
									<tr>
										<td>Client Name</td>
										<td><html:text name="clientForm" property="name" /></td>
									</tr>
									<tr>
										<td>Address</td>
										<td><html:text name="clientForm" property="address" /></td>
									</tr>
									<tr>
										<td>City</td>
										<td><html:text name="clientForm" property="city" /></td>
									</tr>
									<tr>
										<td>Phone Number</td>
										<td><html:text name="clientForm" property="phoneNumber" /></td>
									</tr>
									<tr>
										<td>Fax Number</td>
										<td><html:text name="clientForm" property="faxNumber" /></td>
									</tr>
									<tr>
										<td>Postal Code</td>
										<td><html:text name="clientForm" property="postalCode" /></td>
									</tr>
									<tr>
										<td>Client Status</td>
										<td><html:select name="clientForm" property="isEnabled">
												<html:option value="1">Enabled</html:option>
												<html:option value="0">Disabled</html:option>
											</html:select></td>
									</tr>
									<tr>
										<td><button type="button" class="btn btn-primary"
												onclick="javascript:cancel()">Cancel</button></td>
										<td><button type="button"
												class="btn btn-primary pull-right"
												onclick="javascript:insert()">Save</button></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="col-md-15" style="color: red;" id="message">
						<logic:notEmpty name="clientForm" property="messageList">
							<logic:iterate id="message" name="clientForm"
								property="messageList">
								<bean:write name="message" />
							</logic:iterate>
						</logic:notEmpty>
					</div>

				</div>
			</div>
		</div>
	</html:form>
</body>
</html>