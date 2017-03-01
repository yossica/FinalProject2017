<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!-- Bootstrap Core CSS -->
<link href="asset/bootstrap/css/bootstrap.css" type="text/css"
	rel="stylesheet" media="all">
<link href="asset/metisMenu/metisMenu.css" rel="stylesheet">
<link href="dist/css/sb-admin-2.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="dist/sweetalert.css">
<!-- Custom Fonts -->
<link href="asset/font-awesome/css/font-awesome.css" rel="stylesheet"
	type="text/css">
<script src="dist/sweetalert.min.js"></script>
<script>
	function flyToPageIndex(taskIndex) {
		document.forms[0].taskIndex.value = taskIndex;
		document.forms[0].submit();
	}
</script>
<title>Finance Solution</title>
<script type="text/javascript">
	function insertUser() {
		var regex = /^[A-Za-z0-9]*$/;
		var newUser = document.forms[1].newUser.value;
		var errorMessage = "";
		if (newUser == "") {
			errorMessage = errorMessage + "Username must be filled! \n";
		}
		if (!regex.test(newUser)) {
			errorMessage = errorMessage + "New Username must consist of only alphanumeric!";
			//alert("New Username must consist of only alphanumeric!");
			return;
		}
		if (errorMessage.length != 0) {
			sweetAlert("Oops...", errorMessage, "error");
			/* document.getElementById("message").innerHTML = errorMessage; */
			return;
		} else {
			swal({
				title : "Are you sure?",
				text : "System will insert these data new User",
				type : "warning",
				showCancelButton : true,
				confirmButtonColor : "#ef2300",
				confirmButtonText : "Yes, Update",
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
							flyToPage("insertUser");
						}, 10);
					});

				} else {
					swal("Cancelled", "Cancel Insert New User",
							"error");
				}
			});
			/* flyToPage("insertUser"); */
		}
	}

	function resetPassword(username) {
		swal({
			title : "Are you sure?",
			text : "System will reset password",
			type : "warning",
			showCancelButton : true,
			confirmButtonColor : "#ef2300",
			confirmButtonText : "Yes, Update",
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
						//flyToPage("insertUser");
						document.forms[1].userName.value = username;
						flyToPage("resetPassword");
					}, 10);
				});

			} else {
				swal("Cancelled", "Cancel Reset Password",
						"error");
			}
		});
		/* if (confirm("Are you sure to reset this user password?")) {
			document.forms[1].userName.value = username;
			flyToPage("resetPassword");
		} */
	}

	function flyToPage(task) {
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
	
	function alertError() {
		var message = document.getElementById("err");
		if (message != null) {
			var messageValue = message.value;

			var strValue = messageValue.substring(0, 7);
			if (strValue == "Success") {
				//Success
				swal("Good job!", messageValue, "success");
			} else if (strValue == "Ooooops"){
				//Ooooops
				sweetAlert("Oops...", messageValue, "error");
			}
		}
	}
	window.onload = alertError; 
</script>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/user" method="post">
		<html:hidden property="task" name="userForm" />
		<html:hidden property="userName" name="userForm" />
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Manage User</h1>
					<div class="col-lg-11">
						<div class="col-md-8">
							<html:text property="newUser" name="userForm"
								styleClass="form-control" style="width:50%" />
							<button type="button" class="btn btn-primary"
								onclick="javascript:insertUser()">Insert New User</button>
						</div>
						<div class="col-md-4"
							style="color: red; padding: 20px; overflow: auto; background: #f7eded; border: 2px outset #f70000; display: none;"
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
					<div class="col-lg-8">
						<div class="panel-body">
							<div class="table-responsive"
								style="height: 420px; overflow: auto;">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>Username</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody>
										<logic:notEmpty property="userList" name="userForm">
											<logic:iterate id="user" property="userList" name="userForm">
												<tr>
													<td><bean:write name="user" property="userName" /></td>
													<td>
														<button type="button" class="btn btn-primary"
															onclick="javascript:resetPassword('<bean:write property="userName" name="user"/>')">Reset
															Password</button>
													</td>
												</tr>
											</logic:iterate>
										</logic:notEmpty>

									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<!-- /.col-lg-12 -->
			</div>
		</div>
		
	</html:form>
</body>
</html>
