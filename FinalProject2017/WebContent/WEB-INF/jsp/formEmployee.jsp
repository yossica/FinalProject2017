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
	function insert() {

		var name = document.getElementsByName("employeeBean.name")[0].value;
		var email = document.getElementsByName("employeeBean.email")[0].value;

		var letters = /^([A-Za-z ]{3,})+$/;
		var emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[\d]{1,3}\.[\d]{1,3}\.[\d]{1,3}\.[\d]{1,3}])|(([A-z\-\d]+\.)+[A-z]{2,}))$/;
		var errorMessage = "";

		if (name == "") {
			errorMessage = errorMessage + "Name must be filled! \n";
		} else if (!letters.test(name)) {
			errorMessage = errorMessage
					+ "Invalid format of Name (min. 3 chars, in alphabets only) ! \n";
		}

		if (email == "") {
			errorMessage = errorMessage + "Email must be filled! \n";
		} else if (!emailRegex.test(email)) {
			errorMessage = errorMessage + "Email should be in valid format! \n";
			+"Invalid format of Email!<br/>";
		}

		if (errorMessage.length != 0) {
			sweetAlert("Oops...", errorMessage, "error");
			//document.getElementById("message").innerHTML = errorMessage;
			return;
		} else {
			swal({
				title : "Are you sure?",
				text : "System will insert these data to employee database",
				type : "warning",
				showCancelButton : true,
				confirmButtonColor : "#ef2300",
				confirmButtonText : "Yes, Insert",
				cancelButtonText : "No, Cancel Please!",
				closeOnConfirm : false,
				closeOnCancel : false
			}, function(isConfirm) {
				if (isConfirm) {
					swal({
						title : "Good job!",
						text : "Save Success!",
						type : "success"
					}, function() {
						setTimeout(function() {
							flyToPage();
						}, 10);
					});
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
		<html:hidden property="employeeBean.employeeId" name="employeeForm" />

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Form Employee</h1>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-hover">
								<tbody>
									<tr>
										<td><label>Employee Name <b
												style="color: red; font-size: 18px">*</b></label></td>
										<td><html:text property="employeeBean.name"
												name="employeeForm" styleClass="form-control-client" /></td>
									</tr>
									<tr>
										<td><label>Email <b
												style="color: red; font-size: 18px">*</b></label></td>
										<td><input type="text" name="employeeBean.email"
											placeholder="e.g.: adam@test.com"
											value="<bean:write name="employeeForm" property="employeeBean.email"/>"></td>
									</tr>
									<tr>
										<td><label>Employee Status</label></td>
										<td>
											<div class="col-lg-13">
												<html:select property="employeeBean.isEnabled"
													name="employeeForm" styleClass="form-control-client">
													<html:option value="1">Enabled</html:option>
													<html:option value="0">Disabled</html:option>
												</html:select>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
							<b style="color: red; font-size: 12px"><i>*Field is
									required</i></b>
						</div>
						<!-- /.table-responsive -->
					</div>
					<!-- /.panel-body -->

					<div class="col-md-12" style="padding-left: 0;">
						<div class="pull-left">
							<button type="button" class="btn btn-primary"
								onclick="javascript:cancel()">Cancel</button>
							<button type="button" class="btn btn-primary"
								onclick="javascript:insert()">Save</button>
						</div>
					</div>

					<div class="col-md-12" style="color: red;" id="message">

						<logic:notEmpty name="employeeForm" property="messageList">
							<logic:iterate id="message" name="employeeForm"
								property="messageList">
								<input type="hidden" id="err"
									value="<bean:write name="message" />">
							</logic:iterate>
						</logic:notEmpty>

					</div>

				</div>
				<!-- /.col lg 12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page wrapper -->

	</html:form>


</body>
</html>