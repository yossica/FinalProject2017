<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Finance Solution</title>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	<html:form action="/client" method="post">
	<div id="page-wrapper">
    	<div class="row">
	        <div class="col-lg-12">
	            <h1 class="page-header">Create New Client Record</h1>
	            <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-hover">
		                     <tr>
			            		<td>Client Name</td>
			            		<td><html:text property="name"></html:text></td>
			       <!--      		<th>Address</th>
			            		<th>City</th>
			            		<th>Phone</th>
			            		<th>Fax</th>
			            		<th>Postal Code</th>
			            		<th>Action</th> -->
			            	</tr>
                            <tbody>
                                <logic:iterate id="client" name="clientForm" property="listClient">
				            		<tr>
				            			<td><bean:write name="client" property="name"/></td>
				            			<td><bean:write name="client" property="address"/></td>
				            			<td><bean:write name="client" property="city"/></td>
				            			<td><bean:write name="client" property="phoneNumber"/></td>
				            			<td><bean:write name="client" property="faxNumber"/></td>
				            			<td><bean:write name="client" property="postalCode"/></td>
				            			<td><input type="button" value="Edit" class="btn btn-primary"></td>
				            		</tr>
				            	</logic:iterate>
                            </tbody>
                        </table>
                    </div>
                    <!-- /.table-responsive -->
                </div>
	            <table border="1">
	            	
	            </table>
	            <button type="button" class="btn btn-primary pull-right">Create</button>
	        </div>
	    </div>
    </div>
</html:form>
</body>
</html>