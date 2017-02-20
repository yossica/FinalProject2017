<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Master Client</title>
<script>
	function insert() {

		var name = document.getElementsByName("name")[0].value;
		var address = document.getElementsByName("address")[0].value;
		var city = document.getElementsByName("city")[0].value;
		var phoneNumber = document.getElementsByName("phoneNumber")[0].value;
		var faxNumber = document.getElementsByName("faxNumber")[0].value;
		var postalCode = document.getElementsByName("postalCode")[0].value;

		var letters = /([A-Za-z ])+\w/;
		var numbers = /^([0-9])*$/;
		var alphanumAndSpecial = /^[ A-Za-z0-9_@.,]*$/;
		var phoneRegex = /^([+0])([0-9- ])*$/;
		var faxRegex = /^([+0])([0-9. ])*$/;
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
		if (city == "") {
			errorMessage = errorMessage + "City must be filled!<br/>";
		} else if (!letters.test(city)) {
			errorMessage = errorMessage
					+ "City must be in alphabets only!<br/>";
		}
		if (phoneNumber == "") {
			errorMessage = errorMessage + "Phone Number must be filled!<br/>";
		} else if (!phoneRegex.test(phoneNumber)) {
			errorMessage = errorMessage
					+ "Invalid format of phone number!<br/>";
		}
		if (faxNumber == "") {
			errorMessage = errorMessage + "Fax Number must be filled!<br/>";
		} else if (!faxRegex.test(faxNumber)) {
			errorMessage = errorMessage + "Invalid format of fax number!<br/>";
		}
		if (postalCode == "") {
			errorMessage = errorMessage + "Postal Code must be filled!<br/>";
		} else if (!numbers.test(postalCode)) {
			errorMessage = errorMessage
					+ "Postal Code should contain numbers only!<br/>";
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
				confirmButtonColor : "#ef2300",
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
<title>Financial Solution</title>
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
										<td><label>Client Name</label></td>
										<td><html:text name="clientForm" property="name" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Address</label></td>
										<td><html:text name="clientForm" property="address" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>City</label></td>
										<td><html:text name="clientForm" property="city" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Phone</label></td>
										<td><html:text name="clientForm" property="phoneNumber" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Fax</label></td>
										<td><html:text name="clientForm" property="faxNumber" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Postal Code</label></td>
										<td><html:text name="clientForm" property="postalCode" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Client Status</label></td>
										<td>
											<div class="col-lg-13" >
											<html:select name="clientForm" property="isEnabled" styleClass="form-control-client">
												<html:option value="1">Enabled</html:option>
												<html:option value="0">Disabled</html:option>
											</html:select>
											</div>
									</tr>
								</tbody>
							</table>
						</div> <!-- /.table-responsive -->
					</div>	<!-- /.panel-body -->
          
          <div class="col-md-12" style="padding-right: 1%;" >
						<button type="button" class="btn btn-primary"
										onclick="javascript:cancel()">Cancel</button>
            <button type="button" class="btn btn-primary"
              onclick="javascript:insert()">Save</button>
					</div>
			    
					<div class="col-md-15" style="color: red;" id="message">
						<logic:notEmpty name="clientForm" property="messageList">
							<logic:iterate id="message" name="clientForm"
								property="messageList">
								<bean:write name="message" />
							</logic:iterate>
						</logic:notEmpty>
					</div>


				</div><!-- /.col-lg-12 -->
			</div><!-- /.row -->
		</div><!-- /.page-wrapper -->

	</html:form>
</body>
</html>