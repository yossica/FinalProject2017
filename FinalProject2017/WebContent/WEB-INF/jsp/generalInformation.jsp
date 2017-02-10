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
			document.forms[1].task.value = task;
			document.forms[1].submit();
		}
</script>
<title>General Information</title>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	<html:form action="/generalInformation" method="post">
	<html:hidden property="task" name="generalInformationForm"/>
	</html:form>
	<div id="page-wrapper">
	    <div class="row">
	        <div class="col-lg-12">
	            <h1 class="page-header">General Information List</h1>
	            <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-hover">
		                     <tr>
			            		<th>Key</th>
			            		<th>Name</th>
			            		<th>Action</th>
			            	</tr>
                            <tbody>
                                	<tr>
				            			<td></td>
				            			<td></td>
				            			<td><input type="button" value="Edit" class="btn btn-primary" onclick="javascript:flyToPage('editGeneralInformation')"></td>
				            		</tr>
                            </tbody>
                        </table>
                    </div>
                    <!-- /.table-responsive -->
                </div>
	            <table border="1">
	            	
	            </table>
	        </div>
	    </div>       
    </div>
</body>
</html>