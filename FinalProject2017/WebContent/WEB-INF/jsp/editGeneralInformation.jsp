<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
	function flyToPage(task)
		{
			document.forms[0].task.value = task;
			document.forms[0].submit();
		}
</script>
<title>General Information</title>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	<html:form action="/generalInformation" method="post"></html:form>
	<html:hidden property="task" name="generalInformationForm"/>
	
</body>
</html>