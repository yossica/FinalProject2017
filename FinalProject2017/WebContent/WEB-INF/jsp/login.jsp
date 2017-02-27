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
<link rel="stylesheet" href="asset/bootstrap/css/styleLogin.css">
<link rel="stylesheet" type="text/css" href="dist/sweetalert.css">
<script src="dist/sweetalert.min.js"></script>
<script>
	function flyToPage(task) {
		document.forms[0].task.value = task;
		document.forms[0].submit();
	}
	function alertError() {
		var message = document.getElementById("err");
		if (message != null) {
			var messageValue = message.value;
			var strValue = messageValue.substring(0, 7);
			if (strValue == "Ooooops") {
				//Ooooops
				sweetAlert("Error", messageValue, "error");
			}
		}
	}
	window.onload = alertError;
</script>
</head>
<body>
	<html:form action="/user" method="post">
		<html:hidden property="task" name="userForm" />
		<div class="login-form">
			<h1>Finance Solution</h1>
			<div class="form-group ">
				<input type="text" class="form-control" placeholder="Username "
					id="UserName" name="userName"> <i class="fa fa-user"></i>
			</div>
			<div class="form-group log-status">
				<input type="password" class="form-control" placeholder="Password"
					id="Password" name="password"
					onkeydown="if (event.keyCode == 13)
                        document.getElementById('btnSubmit').click()">
				<i class="fa fa-lock"></i>
			</div>
			<div class="col-md-4" id="message">
				<logic:notEmpty name="userForm" property="messageList">
					<logic:iterate id="message" name="userForm" property="messageList">
						<input type="hidden" id="err"
							value="<bean:write name="message" />">
					</logic:iterate>
				</logic:notEmpty>
			</div>
			<button type="button" id="btnSubmit" class="log-btn"
				onclick="javascript:flyToPage('login')">Log in</button>
		</div>
		<script src="asset/jquery/jquery.min.js"></script>
		<script src="asset/js/index.js"></script>
	</html:form>
</body>
</html>