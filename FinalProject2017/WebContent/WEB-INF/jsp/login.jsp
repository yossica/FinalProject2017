<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
	<link rel="stylesheet" href="asset/bootstrap/css/styleLogin.css">
<script>
	function flyToPage(task){
		document.forms[0].task.value = task;
		document.forms[0].submit();
	}
</script>
</head>
<body>
<html:form action="/user" method="post">
	<html:hidden property="task" name="userForm"/>
	<div class="login-form">
     <h1>Finance Solution</h1>
     <div class="form-group ">
       <input type="text" class="form-control" placeholder="Username " id="UserName" name="userName">
       <i class="fa fa-user"></i>
     </div>
     <div class="form-group log-status">
       <input type="password" class="form-control" placeholder="Password" id="Password" name="password">
       <i class="fa fa-lock"></i>
     </div>
      <span class="alert">Invalid Credentials</span>
     <button type="button" class="log-btn" onclick="javascript:flyToPage('login')">Log in</button>
   </div>
  <script src="asset/jquery/jquery.min.js"></script>
  <script src="asset/js/index.js"></script>
</html:form>
</body>
</html>